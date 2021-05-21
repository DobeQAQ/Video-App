package com.example.group4.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dueeeke.videocontroller.component.PrepareView;
import com.example.group4.R;
import com.example.group4.entity.VideoEntity;
import com.example.group4.listener.OnItemChildClickListener;
import com.example.group4.listener.OnItemClickListener;
import com.example.group4.service.Impl.VideoServiceImpl;
import com.example.group4.service.VideoService;
import com.example.group4.view.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<VideoEntity> datas;

    //点击整个item的事件
    private OnItemChildClickListener mOnItemChildClickListener;

    //点击播放按钮的事件
    private OnItemClickListener mOnItemClickListener;

    public void setDatas(List<VideoEntity> datas) {
        this.datas = datas;
    }

    public VideoAdapter(Context context) {
        this.mContext = context;
    }

    public VideoAdapter(Context context, List<VideoEntity> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_video_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //给ViewHolder绑定数据
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        VideoEntity videoEntity = datas.get(position);

        //控件对象赋值
        vh.tvTitle.setText(videoEntity.getVtitle());
        vh.tvAuthor.setText(videoEntity.getAuthor());
        int likenum=videoEntity.getLikenum();
        int collectnum=videoEntity.getCollectnum();
        boolean flagLike = videoEntity.isFlagLike();
        boolean flagCollect = videoEntity.isFlagCollect();
        if (flagLike) {
            vh.tvDz.setTextColor(Color.parseColor("#E21918"));
            vh.imgDizan.setImageResource(R.mipmap.dianzan_select);
        }
        if (flagCollect) {
            vh.tvCollect.setTextColor(Color.parseColor("#E21918"));
            vh.imgCollect.setImageResource(R.mipmap.collect_select);
        }
        vh.tvDz.setText(String.valueOf(likenum));
        vh.tvCollect.setText(String.valueOf(collectnum));
        vh.flagCollect = flagCollect;
        vh.flagLike = flagLike;


        //头像
        Picasso.with(mContext)
                .load(videoEntity.getHeadurl())
                .transform(new CircleTransform())
                .into(vh.imgHeader);


        //封面图
        Picasso.with(mContext)
                .load(videoEntity.getCoverurl())
                .into(vh.mThumb);

        vh.mPosition = position;
    }

    @Override
    public int getItemCount() {
        if (datas != null && datas.size() > 0) {
            return datas.size();
        } else {
            return 0;
        }
    }

    //前端数据包裹成ViewHolder实体
    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        private TextView tvTitle;   //标题
        private TextView tvAuthor;//作者
        private TextView tvCollect;//收藏数
        private TextView tvDz;//点赞数
        private ImageView imgHeader;//头像
        private ImageView imgCollect;//收藏图标
        private ImageView imgDizan;//点赞图标
//        private ImageView imgCover;

        public FrameLayout mPlayerContainer;
        public PrepareView mPrepareView;
        public ImageView mThumb;//缩略图

        public int mPosition;
        private boolean flagCollect;
        private boolean flagLike;


        public ViewHolder(@NonNull View view){
            super(view);
            tvTitle = view.findViewById(R.id.title);
            tvAuthor = view.findViewById(R.id.author);
            tvDz = view.findViewById(R.id.dz);
            tvCollect = view.findViewById(R.id.collect);

            imgHeader = view.findViewById(R.id.img_header);
            imgCollect = view.findViewById(R.id.img_collect);
            imgDizan = view.findViewById(R.id.img_like);
//            imgCover =view.findViewById(R.id.img_cover);
            mPlayerContainer = view.findViewById(R.id.player_container);
            mPrepareView = view.findViewById(R.id.prepare_view);
            mThumb = mPrepareView.findViewById(R.id.thumb);


            if (mOnItemChildClickListener != null) {
                mPlayerContainer.setOnClickListener(this);
            }
            if (mOnItemClickListener != null) {
                view.setOnClickListener(this);
            }
            imgCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int collectNum = Integer.parseInt(tvCollect.getText().toString());
                    if (flagCollect) { //已收藏
                        if (collectNum > 0) {
                            tvCollect.setText(String.valueOf(--collectNum));
                            tvCollect.setTextColor(Color.parseColor("#161616"));
                            imgCollect.setImageResource(R.mipmap.collect);

                            //收藏数更改，总收藏数减少，个人不收藏
                            VideoService videoService=new VideoServiceImpl(mContext);
                            videoService.updateCollectNum(datas.get(mPosition),-1);
                        }
                    } else {//未收藏
                        tvCollect.setText(String.valueOf(++collectNum));
                        tvCollect.setTextColor(Color.parseColor("#E21918"));
                        imgCollect.setImageResource(R.mipmap.collect_select);

                        //收藏数更改，总收藏数增加，个人收藏
                        VideoService videoService=new VideoServiceImpl(mContext);
                        videoService.updateCollectNum(datas.get(mPosition),1);
                    }
                    flagCollect = !flagCollect;
                }
            });
            imgDizan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int likeNum = Integer.parseInt(tvDz.getText().toString());
                    if (flagLike) { //已点赞
                        if (likeNum > 0) {
                            tvDz.setText(String.valueOf(--likeNum));
                            tvDz.setTextColor(Color.parseColor("#161616"));
                            imgDizan.setImageResource(R.mipmap.dianzan);

                            //喜爱数更改，总喜爱数减少，个人不喜爱
                            VideoService videoService=new VideoServiceImpl(mContext);
                            videoService.updateLikeNum(datas.get(mPosition),-1);
                        }
                    } else {//未点赞
                        tvDz.setText(String.valueOf(++likeNum));
                        tvDz.setTextColor(Color.parseColor("#E21918"));
                        imgDizan.setImageResource(R.mipmap.dianzan_select);

                        //喜爱数更改，总喜爱数增加，个人喜爱
                        VideoService videoService=new VideoServiceImpl(mContext);
                        videoService.updateLikeNum(datas.get(mPosition),1);
                    }
                    flagLike = !flagLike;
                }
            });

            //通过tag将ViewHolder和itemView绑定
            view.setTag(this);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.player_container) {
                if (mOnItemChildClickListener != null) {
                    mOnItemChildClickListener.onItemChildClick(mPosition);
                }
            } else {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(mPosition);
                }
            }

        }
    }
    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        mOnItemChildClickListener = onItemChildClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
