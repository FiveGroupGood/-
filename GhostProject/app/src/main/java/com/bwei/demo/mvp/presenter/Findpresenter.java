package com.bwei.demo.mvp.presenter;

import com.bwei.demo.base.BasePresenter;
import com.bwei.demo.bean.VideoHttpResponse;
import com.bwei.demo.bean.VideoRes;
import com.bwei.demo.mvp.model.Findmodel;
import com.bwei.demo.mvp.view.Findview;

import java.util.Random;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * dell 孙劲雄
 * 2017/12/14
 * 13:44
 */

public class Findpresenter extends BasePresenter<Findview> {

    private Findmodel findmodel=new Findmodel();
    final String catalogId = "402834815584e463015584e539330016";
    int max = 108;
    int min = 1;
    public void data(){
        int nextPage = getNextPage();
        Flowable<VideoHttpResponse<VideoRes>> subject = findmodel.Find(catalogId, nextPage+"");

        subject.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<VideoHttpResponse<VideoRes>>() {
                    @Override
                    public void onNext(VideoHttpResponse<VideoRes> httpResponse) {
                        getView().Data(httpResponse);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });



    }
    private int getNextPage() {
        int page = 10;
         page = getRandomNumber(min, max);
        return page;
    }

    public static int getRandomNumber(int min, int max) {
        return new Random().nextInt(max) % (max - min + 1) + min;
    }
}
