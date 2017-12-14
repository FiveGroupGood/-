package com.bwei.demo.mvp.presenter;

import com.bwei.demo.base.BasePresenter;
import com.bwei.demo.bean.RecommendBean;
import com.bwei.demo.mvp.model.RecommendModel;
import com.bwei.demo.mvp.view.RecommendView;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * 1.类的用途
 * 2.@author 郭冲冲
 * 3.@date 2017/12/13 16 :26
 */

public class RecommendPre extends BasePresenter<RecommendView> {
    RecommendModel model ;
    public RecommendPre() {
        model = new RecommendModel();
    }
    public void r(){
        Flowable<RecommendBean> data = model.getData();
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<RecommendBean>() {
                    @Override
                    public void onNext(RecommendBean bean) {
                        getView().showView(bean);
                    }

                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });



    }


}
