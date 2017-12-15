package com.bwei.demo.mvp.model;

import com.bwei.demo.bean.SeekBean;
import com.bwei.demo.utils.http.Api;
import com.bwei.demo.utils.http.VideoApi;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Flowable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 1.类的用途
 * 2.@author 123
 * 3.@date 2017/12/15 11 :23
 */

public class SeekModel {
    private int pnum = 1;
    public Flowable<SeekBean> getData(String keyword){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        VideoApi videoApi = retrofit.create(VideoApi.class);
        Flowable<SeekBean> seekData = videoApi.getSeekData(keyword, pnum + "");
        return seekData;
    }
}
