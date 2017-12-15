package com.bwei.demo.mvp.presenter;

import android.util.Log;

import com.bwei.demo.base.BasePresenter;
import com.bwei.demo.bean.SeekBean;
import com.bwei.demo.mvp.model.SeekModel;
import com.bwei.demo.mvp.view.SeekView;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * 1.类的用途
 * 2.@author 123
 * 3.@date 2017/12/15 11 :27
 */

public class SeekPresenter extends BasePresenter<SeekView> {
    SeekModel seekModel = new SeekModel();


    public void getSeek(String kerword){
        Flowable<SeekBean> data = seekModel.getData(kerword);
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<SeekBean>() {
                    @Override
                    public void onNext(SeekBean seekBean) {
                        getView().showSeek(seekBean);
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
