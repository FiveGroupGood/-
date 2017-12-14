package com.bwei.demo.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bwei.demo.R;
import com.bwei.demo.base.BaseActivity;
import com.bwei.demo.base.BasePresenter;
import com.bwei.demo.fragment.DiscoverFragment;
import com.bwei.demo.fragment.MineFragment;
import com.bwei.demo.fragment.RecommendFragment;
import com.bwei.demo.fragment.SubjectFragment;
import com.hjm.bottomtabbar.BottomTabBar;

import butterknife.BindView;
import cn.forward.androids.views.ShapeImageView;

import static com.bwei.demo.R.id.tv_fuli;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.bottom_tab_bar)
    BottomTabBar bottomTabBar;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.top_menu)
    LinearLayout topMenu;
    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.tv_mydown)
    TextView tvMydown;
    @BindView(tv_fuli)
    TextView tvFuli;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.tv_feedback)
    TextView tvFeedback;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.about)
    TextView about;
    @BindView(R.id.theme)
    TextView theme;
    @BindView(R.id.bottom_menu)
    LinearLayout bottomMenu;
    @BindView(R.id.iv)
    ShapeImageView iv;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        iv.setBorderColor(2);
        iv.setShape(2);
        tvFuli.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        bottomTabBar.init(getSupportFragmentManager())
                .setImgSize(70, 70)
                .setFontSize(15)
                .setTabPadding(1, 6, 10)
                .setChangeColor(Color.RED, Color.DKGRAY)
                .addTabItem("精选", R.mipmap.found_select, R.mipmap.found, RecommendFragment.class)
                .addTabItem("专题", R.mipmap.special_select, R.mipmap.special, SubjectFragment.class)
                .addTabItem("发现", R.mipmap.fancy_select, R.mipmap.fancy, DiscoverFragment.class)
                .addTabItem("我的", R.mipmap.my_select, R.mipmap.my, MineFragment.class)
                .setTabBarBackgroundResource(R.mipmap.bottom_bg)
                .isShowDivider(false);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_fuli:

                break;
        }
    }
}
