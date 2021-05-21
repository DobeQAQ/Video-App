package com.example.group4.activity;

import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.group4.R;
import com.example.group4.adapter.MyPagerAdapter;
import com.example.group4.entity.TabEntity;
import com.example.group4.entity.User;
import com.example.group4.fragment.CollectFragment;
import com.example.group4.fragment.HomeFragment;
import com.example.group4.fragment.MyFragment;
import com.example.group4.fragment.NewsFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity {

    private  int userId;
    private String[] mTitles = {"首页", "收藏", "我的"};

    //未选中图标
    private int[] mIconUnselectIds = {
            R.mipmap.home_unselect, R.mipmap.collect_unselect,
            R.mipmap.my_unselect};
    //选中图标
    private int[] mIconSelectIds = {
            R.mipmap.home_selected, R.mipmap.collect_selected,
            R.mipmap.my_selected};

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ViewPager viewPager;
    private CommonTabLayout commonTabLayout;

    @Override
    protected int initLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.viewpager);
        commonTabLayout = findViewById(R.id.commonTabLayout);
    }

    @Override
    protected void initData() {
        //设置当前用户ID
        Intent intent = getIntent();
        User user= (User) intent.getSerializableExtra("user");
        userId=user.getUserId();

        //添加3个fragment
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(NewsFragment.newInstance());
        mFragments.add(MyFragment.newInstance());
        //得到tab栏集合
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        //设置数据与点击事件-》切换页面
        commonTabLayout.setTabData(mTabEntities);
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        //预加载fragment个数
        viewPager.setOffscreenPageLimit(mFragments.size());
        //页面左右滑动切换->tab栏也切换
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                commonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //绑定适配器
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), mTitles, mFragments));
    }
}