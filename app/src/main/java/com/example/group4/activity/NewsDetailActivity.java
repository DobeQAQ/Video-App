package com.example.group4.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.group4.R;
import com.example.group4.entity.NewsEntity;
import com.example.group4.view.CircleTransform;
import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends BaseActivity {

    private ImageView ivback;
//    private ImageView ivCollect;
    private TextView tvDetail;
    private TextView tvTitle;
    private ImageView ivHeader;
    private TextView tvAuthor;
    private TextView tvTime;

    @Override
    protected int initLayout() {
        return R.layout.item_news_layout;
    }

    @Override
    protected void initView() {
        ivback=findViewById(R.id.back);
//        ivCollect=findViewById(R.id.collect);
        tvDetail=findViewById(R.id.detail);
        tvTitle=findViewById(R.id.detail_title);
        ivHeader=findViewById(R.id.detail_header);
        tvAuthor=findViewById(R.id.detail_author);
        tvTime=findViewById(R.id.detail_time);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        NewsEntity newsEntity= (NewsEntity) intent.getSerializableExtra("news");



        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        ivCollect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        tvDetail.setText(newsEntity.getNewsContent());
        tvTitle.setText(newsEntity.getNewsTitle());
        Picasso.with(mContext)
                .load(newsEntity.getHeaderUrl())
                .transform(new CircleTransform())
                .into(ivHeader);
        tvAuthor.setText(newsEntity.getAuthorName());
        tvTime.setText(newsEntity.getReleaseDate());

//        tvDetail.setMovementMethod(ScrollingMovementMethod.getInstance());
    }


}