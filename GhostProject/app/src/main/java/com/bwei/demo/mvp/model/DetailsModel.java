package com.bwei.demo.mvp.model;

import android.util.Log;

import com.bwei.demo.bean.CommentBean;
import com.bwei.demo.bean.DetailsBean;
import com.bwei.demo.utils.http.Api;
import com.bwei.demo.utils.http.RetrofitClent;
import com.bwei.demo.utils.http.VideoApi;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

import static android.content.ContentValues.TAG;

/**
 * Created by ${李晨阳} on 2017/12/13.
 */

public class DetailsModel {

    /**
     * 简介
     *
     * @param dataId
     * @param details
     */
    public void getData(final String dataId, final DetailsI details) {

        VideoApi api = RetrofitClent.getRetrofit(Api.url).create(VideoApi.class);
        Flowable<DetailsBean> flowable = api.getVideoInfo(dataId);

        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<DetailsBean>() {
                    @Override
                    public void onNext(DetailsBean detailsBean) {

                        details.success(detailsBean);
                        Log.i("xxx", "onNext: ");

                        getComment(details, dataId, "1");
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

    /**
     * 评论
     */
    public void getComment(final DetailsI details, String dataId, String pnum) {


        Log.i(TAG, "getComment: " + dataId + pnum);
        VideoApi api = RetrofitClent.getRetrofit(Api.url).create(VideoApi.class);
        Flowable<CommentBean> flowable = api.getCommentList(dataId, pnum);

        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<CommentBean>() {
                    @Override
                    public void onNext(CommentBean commentBean) {

                        Log.i("xxx", "onNext: ");

                        details.commentData(commentBean);
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
