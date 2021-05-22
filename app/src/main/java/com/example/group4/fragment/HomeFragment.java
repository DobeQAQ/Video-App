package com.example.group4.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.group4.R;
import com.example.group4.adapter.HomeAdapter;
import com.example.group4.entity.CategoryEntity;
import com.example.group4.entity.User;
import com.example.group4.service.CategoryService;
import com.example.group4.service.Impl.CategoryServiceImpl;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private int userId;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = view.findViewById(R.id.fixedViewPager);
        slidingTabLayout = view.findViewById(R.id.slidingTabLayout);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //设置当前用户ID
        Intent intent = getActivity().getIntent();
        User user = (User) intent.getSerializableExtra("user");
        userId = user.getUserId();

        /**
         * 获得title,分类
         */
        CategoryService categoryService = new CategoryServiceImpl(this.getActivity());
        List<CategoryEntity> list = categoryService.listCategory();
        List<String> ls = new ArrayList<>();
        for (CategoryEntity ce : list) {
            ls.add(ce.getCategoryName());
            mFragments.add(VideoFragment.newInstance(ce.getCategoryId()));
        }
        mTitles = ls.toArray(new String[ls.size()]);

        //预加载fragment个数
        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.setAdapter(new HomeAdapter(getFragmentManager(), mTitles, mFragments));
        slidingTabLayout.setViewPager(viewPager);
    }
}