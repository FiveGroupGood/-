package com.bwei.demo.mvp.model;

import android.util.Log;

import com.bwei.demo.bean.DetailsBean;
import com.bwei.demo.utils.http.Api;
import com.bwei.demo.utils.http.RetrofitClent;
import com.bwei.demo.utils.http.VideoApi;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by ${李晨阳} on 2017/12/13.
 */

public class DetailsModel {

    public void getData(String dataId, final DetailsI details) {

        VideoApi api = RetrofitClent.getRetrofit(Api.url).create(VideoApi.class);

        Flowable<DetailsBean> flowable = api.getVideoInfo(dataId);

        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<DetailsBean>() {
                    @Override
                    public void onNext(DetailsBean detailsBean) {

                        details.success(detailsBean);
                        Log.i("xxx", "onNext: ");
                    }

                    @Override
                    public void onError(Throwable t) {

                        Log.i("xxx", "onError: ");
                    }

                    @Override
                    public void onComplete() {

                        Log.i("xxx", "onComplete: ");
                    }
                });

    }
}
