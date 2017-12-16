package com.bwei.demo.mvp.presenter;


import android.util.Log;

import com.bwei.demo.base.BasePresenter;
import com.bwei.demo.bean.CommentBean;
import com.bwei.demo.bean.DetailsBean;
import com.bwei.demo.mvp.model.DetailsI;
import com.bwei.demo.mvp.model.DetailsModel;
import com.bwei.demo.mvp.view.DetailsView;

/**
 * Created by ${李晨阳} on 2017/12/13.
 */

public class DetailsPresenter extends BasePresenter<DetailsView> {

    DetailsModel detailsModel = new DetailsModel();

    public void getDetailsData(String dataId) {

        Log.i("xxx", "getDetailsData: " + dataId);
        detailsModel.getData(dataId, new DetailsI() {
            @Override
            public void success(DetailsBean details) {

                getView().returnDetailsData(details);
            }

            @Override
            public void commentData(CommentBean commentBean) {


                getView().returnCommentData(commentBean);
            }
        });

    }
}
