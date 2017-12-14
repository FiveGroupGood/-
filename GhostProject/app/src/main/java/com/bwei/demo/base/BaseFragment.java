package com.bwei.demo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;


/**
 * @author ${张健}
 * @date 2017/11/6/14:33
 */

public abstract class BaseFragment<V, T extends BasePresenter> extends Fragment {

    private View view;
    protected T presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = View.inflate(getActivity(), layout(), null);
        }
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(view);
        }
        presenter = getPresenter();
        if (presenter != null) {
            presenter.attachView((V) this);
        }
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();

    }

    //绑定组件
    protected abstract void initView();

    //操作数据
    protected abstract void initData();

    //加载视图
    public abstract int layout();

    //关联
    protected abstract T getPresenter();
}
