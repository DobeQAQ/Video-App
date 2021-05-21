package com.example.group4.fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videocontroller.component.CompleteView;
import com.dueeeke.videocontroller.component.ErrorView;
import com.dueeeke.videocontroller.component.GestureView;
import com.dueeeke.videocontroller.component.TitleView;
import com.dueeeke.videocontroller.component.VodControlView;
import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.example.group4.R;
import com.example.group4.adapter.VideoAdapter;
import com.example.group4.entity.User;
import com.example.group4.entity.VideoEntity;
import com.example.group4.listener.OnItemChildClickListener;
import com.example.group4.service.Impl.VideoServiceImpl;
import com.example.group4.service.VideoService;
import com.example.group4.util.Tag;
import com.example.group4.util.Utils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


public class VideoFragment extends BaseFragment implements OnItemChildClickListener {

    private  int userId;
    private int categoryId;
    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private int pageNum = 1;//当前页数
    private int pageMaxNum=0;//总页数
    private static final int PAGESIZE=5;//每页条数
    private LinearLayoutManager linearLayoutManager;
    private List<VideoEntity> datas = new ArrayList<>();
    private List<VideoEntity> currentDatas = new ArrayList<>();//大小<=PAGESIZE
    private VideoAdapter videoAdapter;

    //视频播放功能
    protected VideoView mVideoView;
    protected StandardVideoController mController;
    protected ErrorView mErrorView;
    protected CompleteView mCompleteView;
    protected TitleView mTitleView;
    /**
     * 当前播放的位置
     */
    protected int mCurPos = -1;

    /**
     * 上次播放的位置，用于页面切回来  之后恢复播放
     */
    protected int mLastPos = mCurPos;

    public VideoFragment() {

    }

    public static VideoFragment newInstance(int categoryId) {
        VideoFragment fragment = new VideoFragment();
        fragment.categoryId=categoryId;
        return fragment;
    }


    @Override
    protected int initLayout() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initView() {
        initVideoView();
        recyclerView = mRootView.findViewById(R.id.recyclerView);
        refreshLayout = mRootView.findViewById(R.id.refreshLayout);
    }

    @Override
    protected void initData() {
        //设置当前用户ID
        Intent intent = getActivity().getIntent();
        User user= (User) intent.getSerializableExtra("user");
        userId=user.getUserId();

        //列表，线性布局效果，使用线性布局管理器，垂直排列
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //设置布局管理器
        recyclerView.setLayoutManager(linearLayoutManager);

        //适配器
        videoAdapter = new VideoAdapter(getActivity());//初始化适配器，
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
                FrameLayout playerContainer = view.findViewById(R.id.player_container);
                View v = playerContainer.getChildAt(0);
                if (v != null && v == mVideoView && !mVideoView.isFullScreen()) {
                    releaseVideoView();
                }
            }
        });

        //上拉刷新监听器
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getVideoList(true);
            }
        });
        //下拉加载监听器
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                getVideoList(false);//加载，不需刷新 isRefresh=false
            }
        });
    }




    //初始化播放器
    protected void initVideoView() {
        mVideoView = new VideoView(getActivity());
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
        mController = new StandardVideoController(getActivity());
        mErrorView = new ErrorView(getActivity());
        mController.addControlComponent(mErrorView);
        mCompleteView = new CompleteView(getActivity());
        mController.addControlComponent(mCompleteView);
        mTitleView = new TitleView(getActivity());
        mController.addControlComponent(mTitleView);
        mController.addControlComponent(new VodControlView(getActivity()));
        mController.addControlComponent(new GestureView(getActivity()));
        mController.setEnableOrientation(true);
        mVideoView.setVideoController(mController);
    }


    /**
     * 获取datas的数据
     */
    private void listVideo(){
        VideoService videoService=new VideoServiceImpl(this.getActivity());
        datas=videoService.listVideoByCategory(categoryId,userId);
        if(pageMaxNum==0){
            //第一页
            for(int i=0,j=1; j<=PAGESIZE&&i<datas.size(); i++,j++){
                currentDatas.add(datas.get(i));
            }
            pageMaxNum=(datas.size()+PAGESIZE-1)/PAGESIZE;//总页数
            videoAdapter.setDatas(currentDatas);
            videoAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 刷新和加载currentDatas数据
     * @param isRefresh
     */
    private void getVideoList(boolean isRefresh){
        if(isRefresh){
            //isRefresh=true，上拉刷新，刷新动画true
            refreshLayout.finishRefresh(true);
            Toast.makeText(getActivity(), "已经是最新数据了", Toast.LENGTH_SHORT).show();
        }else{
            //isRefresh=false，下拉加载，加载动画true
            refreshLayout.finishLoadMore(true);
            if(pageNum>=pageMaxNum){
                Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
            }else{
                int pos=PAGESIZE*pageNum;
                if(pos>=datas.size()){
                    pos=datas.size()-1;
                }
                pageNum++;

                //多加载一页数据
                for(int i=(pageNum-1)*PAGESIZE,j=1; j<=PAGESIZE&&i<datas.size(); i++,j++){
                    currentDatas.add(datas.get(i));
                }

                videoAdapter.setDatas(currentDatas);//设置数据
                videoAdapter.notifyDataSetChanged();//让适配器刷新数据
            }
        }
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
     * @param position 列表位置
     */
    protected void startPlay(int position) {
        if (mCurPos == position) return;
        if (mCurPos != -1) {
            releaseVideoView();
        }
        VideoEntity videoEntity = currentDatas.get(position);
        //边播边存
//        String proxyUrl = ProxyVideoCacheManager.getProxy(getActivity()).getProxyUrl(videoBean.getUrl());
//        mVideoView.setUrl(proxyUrl);

        mVideoView.setUrl(videoEntity.getPlayurl());
        mTitleView.setTitle(videoEntity.getVtitle());
        View itemView = linearLayoutManager.findViewByPosition(position);
        if (itemView == null) return;
        VideoAdapter.ViewHolder viewHolder = (VideoAdapter.ViewHolder) itemView.getTag();
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
        if (getActivity().getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mCurPos = -1;
    }

}