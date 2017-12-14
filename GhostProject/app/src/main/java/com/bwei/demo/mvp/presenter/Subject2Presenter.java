package com.bwei.demo.mvp.presenter;

import com.bwei.demo.base.BasePresenter;
import com.bwei.demo.bean.VideoHttpResponse;
import com.bwei.demo.bean.VideoRes;
import com.bwei.demo.mvp.model.Subject2model;
import com.bwei.demo.mvp.view.Subject2View;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * dell 孙劲雄
 * 2017/12/13
 * 19:23
 */

public class Subject2Presenter extends BasePresenter<Subject2View> {


    public Subject2model model=new Subject2model();


    public void data(String s,String p){

        Flowable<VideoHttpResponse<VideoRes>> subject = model.Subject(s, p);

        subject.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<VideoHttpResponse<VideoRes>>() {
                    @Override
                    public void onNext(VideoHttpResponse<VideoRes> httpResponse) {
                        getView().data(httpResponse);
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
