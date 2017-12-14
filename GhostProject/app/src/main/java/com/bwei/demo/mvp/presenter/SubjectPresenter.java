package com.bwei.demo.mvp.presenter;

import android.util.Log;
import android.view.View;

import com.bwei.demo.base.BasePresenter;
import com.bwei.demo.bean.RecommendBean;
import com.bwei.demo.mvp.model.Subjectmodel;
import com.bwei.demo.mvp.view.SubjectView;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * dell 孙劲雄
 * 2017/12/13
 * 16:55
 */

public class SubjectPresenter extends BasePresenter<SubjectView> {

    private Subjectmodel subjectmodel=new Subjectmodel();

    public void data(){

        Flowable<RecommendBean> subject = subjectmodel.Subject();
        Log.i("++++","asdetteteas");
        subject.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<RecommendBean>() {
                    @Override
                    public void onNext(RecommendBean recommendBean) {

                      getView().Subject(recommendBean);

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
