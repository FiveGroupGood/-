package com.bwei.demo.mvp.model;

import com.bwei.demo.bean.RecommendBean;
import com.bwei.demo.utils.http.Api;
import com.bwei.demo.utils.http.VideoApi;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Flowable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 1.类的用途
 * 2.@author 123
 * 3.@date 2017/12/13 16 :21
 */

public class RecommendModel {

   public  Flowable<RecommendBean> getData(){
       Retrofit retrofit = new Retrofit.Builder()
               .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
               .addConverterFactory(GsonConverterFactory.create())
               .baseUrl(Api.url)
               .build();
        VideoApi videoApi = retrofit.create(VideoApi.class);
        Flowable<RecommendBean> recommendData = videoApi.getRecommendData();
        return recommendData;
   }

}
