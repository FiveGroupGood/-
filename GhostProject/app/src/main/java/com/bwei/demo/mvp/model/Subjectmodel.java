package com.bwei.demo.mvp.model;

import com.bwei.demo.bean.RecommendBean;
import com.bwei.demo.utils.http.Api;
import com.bwei.demo.utils.http.RetrofitClent;
import com.bwei.demo.utils.http.VideoApi;

import io.reactivex.Flowable;

/**
 * dell 孙劲雄
 * 2017/12/13
 * 16:52
 */

public class Subjectmodel {

       public Flowable<RecommendBean> Subject(){

       return RetrofitClent.getRetrofit(Api.url).create(VideoApi.class).getRecommendData();

   }


}
