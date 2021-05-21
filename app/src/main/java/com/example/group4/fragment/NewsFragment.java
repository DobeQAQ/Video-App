package com.example.group4.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.group4.R;
import com.example.group4.activity.HomeActivity;
import com.example.group4.activity.NewsDetailActivity;
import com.example.group4.adapter.NewsAdapter;
import com.example.group4.adapter.VideoAdapter;
import com.example.group4.entity.NewsEntity;
import com.example.group4.entity.User;
import com.example.group4.entity.VideoEntity;
import com.example.group4.service.Impl.NewsServiceImpl;
import com.example.group4.service.Impl.VideoServiceImpl;
import com.example.group4.service.NewsService;
import com.example.group4.service.VideoService;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends BaseFragment {

    private  int userId;
    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;

    private int pageNum = 1;//当前页数
    private int pageMaxNum=0;//总页数
    private static final int PAGESIZE=6;//每页条数
    private List<NewsEntity> datas = new ArrayList<>();
    private List<NewsEntity> currentDatas = new ArrayList<>();//大小<=PAGESIZE

    private NewsAdapter newsAdapter;
    private LinearLayoutManager linearLayoutManager;



    public NewsFragment() {
    }


    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }


    @Override
    protected int initLayout() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView() {
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
        newsAdapter = new NewsAdapter(getActivity());//初始化适配器，
        recyclerView.setAdapter(newsAdapter);//配置绑定适配器

        //点击弹出详情页面
        newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Serializable obj) {
                NewsEntity newsEntity = (NewsEntity) obj;
                Intent in = new Intent(getActivity(), NewsDetailActivity.class);
                in.putExtra("user",user);
                in.putExtra("news",newsEntity);
                startActivity(in);
            }
        });

        /**
         * 获取所有datas的数据
         */
        listNewsList();

        //上拉刷新监听器
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getNewsList(true);
            }
        });
        //下拉加载监听器
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                getNewsList(false);//加载，不需刷新 isRefresh=false
            }
        });
    }



    /**
     * 获取所有datas的数据
     */
    private void listNewsList() {
        NewsService newsService=new NewsServiceImpl(this.getActivity());
        datas=newsService.listNews();
        if(pageMaxNum==0){
            //第一页
            for(int i=0,j=1; j<=PAGESIZE&&i<datas.size(); i++,j++){
                currentDatas.add(datas.get(i));
            }
            pageMaxNum=(datas.size()+PAGESIZE-1)/PAGESIZE;//总页数
            newsAdapter.setDatas(currentDatas);
            newsAdapter.notifyDataSetChanged();
        }

    }

    /**
     * 刷新页面数据
     * @param isRefresh
     */
    private void getNewsList(boolean isRefresh) {
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

                newsAdapter.setDatas(currentDatas);//设置数据
                newsAdapter.notifyDataSetChanged();//让适配器刷新数据
            }
        }
    }
}