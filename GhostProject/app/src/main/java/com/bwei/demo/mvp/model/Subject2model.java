package com.bwei.demo.mvp.model;

import com.bwei.demo.bean.RecommendBean;
import com.bwei.demo.bean.VideoHttpResponse;
import com.bwei.demo.bean.VideoRes;
import com.bwei.demo.utils.http.Api;
import com.bwei.demo.utils.http.RetrofitClent;
import com.bwei.demo.utils.http.VideoApi;

import io.reactivex.Flowable;

/**
 * dell 孙劲雄
 * 2017/12/13
 * 19:27
 */

public class Subject2model {

    public Flowable<VideoHttpResponse<VideoRes>> Subject(String catalogId, String pnu){

        return RetrofitClent.getRetrofit(Api.url).create(VideoApi.class).getVideoList(catalogId,pnu);

    }


}
