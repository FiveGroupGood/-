package com.bwei.demo.mvp.view;

import com.bwei.demo.bean.VideoHttpResponse;
import com.bwei.demo.bean.VideoRes;

/**
 * dell 孙劲雄
 * 2017/12/13
 * 19:24
 */

public interface Subject2View {

    void data(VideoHttpResponse<VideoRes> httpResponse);

}
