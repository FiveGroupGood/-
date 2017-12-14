package com.bwei.demo.activity;

import android.widget.EditText;
import android.widget.TextView;

import com.bwei.demo.R;
import com.bwei.demo.base.BaseActivity;
import com.bwei.demo.base.BasePresenter;

import butterknife.BindView;

public class SeekActivity extends BaseActivity {


    @BindView(R.id.seek_et)
    EditText seekEt;
    @BindView(R.id.seek_tv)
    TextView seekTv;

    @Override
    protected int getLayout() {
        return R.layout.activity_seek;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        seekEt.getBackground().setAlpha(100);


    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }


}
