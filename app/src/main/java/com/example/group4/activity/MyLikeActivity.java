package com.example.group4.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videocontroller.component.CompleteView;
import com.dueeeke.videocontroller.component.ErrorView;
import com.dueeeke.videocontroller.component.GestureView;
import com.dueeeke.videocontroller.component.TitleView;
import com.dueeeke.videocontroller.component.VodControlView;
import com.dueeeke.videoplayer.player.VideoView;
import com.example.group4.R;
import com.example.group4.adapter.MyLikeAdapter;
import com.example.group4.entity.VideoEntity;
import com.example.group4.listener.OnItemChildClickListener;
import com.example.group4.service.Impl.VideoServiceImpl;
import com.example.group4.service.VideoService;
import com.example.group4.util.Tag;
import com.example.group4.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class MyLikeActivity extends BaseActivity implements OnItemChildClickListener {

    private int userId;
    private RecyclerView recyclerView;
    private MyLikeAdapter videoAdapter;
    private List<VideoEntity> datas = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;

    //视频播放功能
    protected VideoView mVideoView;
    protected StandardVideoController mController;
    protected ErrorView mErrorView;
    protected CompleteView mCompleteView;
    protected TitleView mTitleView;

    private ImageView ivback;
    /**
     * 当前播放的位置
     */
    protected int mCurPos = -1;
    /**
     * 上次播放的位置，用于页面切回来之后恢复播放
     */
    protected int mLastPos = mCurPos;

    @Override
    protected int initLayout() {
        return R.layout.activity_my_like;
    }

    @Override
    protected void initView() {
        initVideoView();//初始化视频播放器
        recyclerView = findViewById(R.id.recyclerView);
        ivback = findViewById(R.id.my_back);
    }

    /**
     * 初始化视频播放器
     */
    private void initVideoView() {
        mVideoView = new VideoView(this);
        mVideoView.setOnStateChangeListener(new com.dueeeke.videoplayer.player.VideoView.SimpleOnStateChangeListener() {
            @Override
            public void onPlayStateChanged(int playState) {
                //监听VideoViewManager释放，重置状态
                if (playState == com.dueeeke.videoplayer.player.VideoView.STATE_IDLE) {
                    Utils.removeViewFormParent(mVideoView);
                    mLastPos = mCurPos;
                    mCurPos = -1;
                }
            }
        });
        mController = new StandardVideoController(this);
        mErrorView = new ErrorView(this);
        mController.addControlComponent(mErrorView);
        mCompleteView = new CompleteView(this);
        mController.addControlComponent(mCompleteView);
        mTitleView = new TitleView(this);
        mController.addControlComponent(mTitleView);
        mController.addControlComponent(new VodControlView(this));
        mController.addControlComponent(new GestureView(this));
        mController.setEnableOrientation(true);
        mVideoView.setVideoController(mController);
    }

    @Override
    protected void initData() {
        //设置当前用户ID
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);

        //列表，线性布局效果，使用线性布局管理器，垂直排列
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //设置布局管理器
        recyclerView.setLayoutManager(linearLayoutManager);

        //适配器
        videoAdapter = new MyLikeAdapter(this);//初始化适配器，
        videoAdapter.setOnItemChildClickListener(this);
        recyclerView.setAdapter(videoAdapter);//配置绑定适配器

        //获取所有datas的数据
        listVideo();

        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                FrameLayout playerContainer = view.findViewById(R.id.like_player_container);
                View v = playerContainer.getChildAt(0);
                if (v != null && v == mVideoView && !mVideoView.isFullScreen()) {
                    releaseVideoView();
                }
            }
        });

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 获取收藏的datas数据
     */
    private void listVideo() {
        VideoService videoService = new VideoServiceImpl(this);
        datas = videoService.listVideoByMyLike(userId);
        videoAdapter.setDatas(datas);
        videoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        pause();
    }

    /**
     * 由于onPause必须调用super。故增加此方法，
     * 子类将会重写此方法，改变onPause的逻辑
     */
    protected void pause() {
        releaseVideoView();
    }

    @Override
    public void onResume() {
        super.onResume();
        resume();
    }

    /**
     * 由于onResume必须调用super。故增加此方法，
     * 子类将会重写此方法，改变onResume的逻辑
     */
    protected void resume() {
        if (mLastPos == -1)
            return;
        //恢复上次播放的位置
        startPlay(mLastPos);
    }

    /**
     * PrepareView被点击
     */
    @Override
    public void onItemChildClick(int position) {
        startPlay(position);
    }

    /**
     * 开始播放
     *
     * @param position 列表位置
     */
    protected void startPlay(int position) {
        if (mCurPos == position) return;
        if (mCurPos != -1) {
            releaseVideoView();
        }
        VideoEntity videoEntity = datas.get(position);

        mVideoView.setUrl(videoEntity.getPlayurl());
        mTitleView.setTitle(videoEntity.getVtitle());
        View itemView = linearLayoutManager.findViewByPosition(position);
        if (itemView == null) return;
        MyLikeAdapter.ViewHolder viewHolder = (MyLikeAdapter.ViewHolder) itemView.getTag();
        //把列表中预置的PrepareView添加到控制器中，注意isPrivate此处只能为true。
        mController.addControlComponent(viewHolder.mPrepareView, true);
        Utils.removeViewFormParent(mVideoView);
        viewHolder.mPlayerContainer.addView(mVideoView, 0);
        //播放之前将VideoView添加到VideoViewManager以便在别的页面也能操作它
        getVideoViewManager().add(mVideoView, Tag.LIST);
        mVideoView.start();
        mCurPos = position;
    }

    private void releaseVideoView() {
        mVideoView.release();
        if (mVideoView.isFullScreen()) {
            mVideoView.stopFullScreen();
        }
        if (this.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mCurPos = -1;
    }
}