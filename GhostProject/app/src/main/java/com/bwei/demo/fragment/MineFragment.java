package com.bwei.demo.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.demo.R;
import com.bwei.demo.activity.HistoryActivity;
import com.bwei.demo.base.BaseFragment;
import com.bwei.demo.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by ${李晨阳} on 2017/12/12.
 * <p>
 * 我的
 */

public class MineFragment extends BaseFragment {
    @BindView(R.id.mine_lishi)
    TextView mineLishi;
    @BindView(R.id.mine_huancun)
    TextView mineHuancun;
    @BindView(R.id.mine_shoucang)
    TextView mineShoucang;
    @BindView(R.id.mine_zhuti)
    TextView mineZhuti;
    Unbinder unbinder;

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public int layout() {
        return R.layout.mine_layout;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }



    @OnClick({R.id.mine_lishi, R.id.mine_huancun, R.id.mine_shoucang, R.id.mine_zhuti})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_lishi:

                Toast.makeText(getActivity(), "历史", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_huancun:

                Toast.makeText(getActivity(), "缓存", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mine_shoucang:

                Toast.makeText(getActivity(), "收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mine_zhuti:

                Toast.makeText(getActivity(), "主题", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
