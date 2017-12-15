package com.bwei.demo.activity;

import android.util.Log;

import com.bwei.demo.R;
import com.bwei.demo.base.BaseActivity;
import com.bwei.demo.bean.GankItemBean;
import com.bwei.demo.mvp.presenter.WelfarePresenter;
import com.bwei.demo.mvp.view.IGankView;

public class WelfareActivity extends BaseActivity<IGankView,WelfarePresenter> implements IGankView {



    @Override
    protected int getLayout() {
        return R.layout.activity_welfare;
    }

    @Override
    protected void initView() {

        presenter.getWelfarePresenter(20,1);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected WelfarePresenter getPresenter() {
        return new WelfarePresenter();
    }


    @Override
    public void getIGankView(GankItemBean gankItemBean) {
        Log.i("fff",gankItemBean.toString());
    }
}
