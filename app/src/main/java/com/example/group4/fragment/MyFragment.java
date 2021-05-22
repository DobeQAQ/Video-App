package com.example.group4.fragment;

import android.content.Intent;

import android.view.View;
import android.widget.TextView;

import com.example.group4.MainActivity;
import com.example.group4.R;
import com.example.group4.activity.MyCollectActivity;
import com.example.group4.activity.MyLikeActivity;
import com.example.group4.entity.User;

import butterknife.OnClick;
import skin.support.SkinCompatManager;

public class MyFragment extends BaseFragment {

    private int userId;
    private TextView tvMyName;

    public MyFragment() {
    }

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }


    @Override
    protected int initLayout() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView() {
        tvMyName = mRootView.findViewById(R.id.my_name);

        Intent intent = getActivity().getIntent();
        User user = (User) intent.getSerializableExtra("user");
        userId = user.getUserId();
        tvMyName.setText(user.getUsername());
    }

    @Override
    protected void initData() {
    }

    @OnClick({R.id.my_collect, R.id.my_like, R.id.my_skin, R.id.my_quit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_collect:
                Intent in1 = new Intent(getActivity(), MyCollectActivity.class);
                System.out.println(userId);
                in1.putExtra("userId", userId);
                startActivity(in1);
                break;
            case R.id.my_like:
                Intent in2 = new Intent(getActivity(), MyLikeActivity.class);
                System.out.println(userId);
                in2.putExtra("userId", userId);
                startActivity(in2);
                break;
            case R.id.my_skin:
                String skin = findByKey("skin");
                if (skin.equals("night")) {
                    // 恢复应用默认皮肤
                    SkinCompatManager.getInstance().restoreDefaultTheme();
                    insertVal("skin", "default");
                } else {
                    //修改皮肤
                    SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN); // 后缀加载
                    insertVal("skin", "night");
                }
                break;
            case R.id.my_quit:
                navigateToWithFlag(MainActivity.class,
                        Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                break;
        }
    }
}