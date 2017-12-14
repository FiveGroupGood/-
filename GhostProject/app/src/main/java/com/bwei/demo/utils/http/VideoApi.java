package com.bwei.demo.utils.http;

import com.bwei.demo.bean.DetailsBean;
import com.bwei.demo.bean.RecommendBean;
import com.bwei.demo.bean.VideoHttpResponse;
import com.bwei.demo.bean.VideoRes;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ${李晨阳} on 2017/12/13.
 */

public interface VideoApi {

    /*
     * 首页
     * http://api.svipmovie.com/front/homePageApi/homePage.do
     */
    @GET("homePageApi/homePage.do")
    Flowable<RecommendBean> getRecommendData();

    //发现
    @GET("columns/getVideoList.do")
    Flowable<VideoHttpResponse<VideoRes>> getVideoList(@Query("catalogId") String catalogId, @Query("pnum") String pnum);


    /**
     * 影片详情
     *
     * @param mediaId 影片id
     * @return
     */
    @GET("videoDetailApi/videoDetail.do")
    Flowable<DetailsBean> getVideoInfo(@Query("mediaId") String mediaId);




}