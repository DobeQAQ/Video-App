package com.example.group4.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group4.R;
import com.example.group4.entity.NewsEntity;
import com.example.group4.view.CircleTransform;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<NewsEntity> datas = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setDatas(List<NewsEntity> datas) {
        this.datas = datas;
    }

    public NewsAdapter(Context context) {
        this.mContext = context;
    }

    public NewsAdapter(Context context, List<NewsEntity> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.news_item, parent, false);
        NewsAdapter.ViewHolder viewHolder = new NewsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NewsEntity newsEntity = datas.get(position);

        ViewHolder vh = (ViewHolder) holder;
        vh.title.setText(newsEntity.getNewsTitle());
        vh.author.setText(newsEntity.getAuthorName());
        vh.time.setText(newsEntity.getReleaseDate());
        vh.newsEntity = newsEntity;

        Picasso.with(mContext)
                .load(newsEntity.getHeaderUrl())
                .transform(new CircleTransform())
                .into(vh.header);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView author;
        private TextView time;
        private ImageView header;
        private NewsEntity newsEntity;

        public ViewHolder(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.title);
            author = view.findViewById(R.id.author);
            time = view.findViewById(R.id.time);
            header = view.findViewById(R.id.header);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(newsEntity);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (datas != null && datas.size() > 0) {
            return datas.size();
        } else {
            return 0;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Serializable obj);
    }
}
