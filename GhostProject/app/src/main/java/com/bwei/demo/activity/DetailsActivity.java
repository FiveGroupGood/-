package com.bwei.demo.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwei.demo.R;
import com.bwei.demo.adapter.CommentAdapter;
import com.bwei.demo.base.BaseActivity;
import com.bwei.demo.bean.DetailsBean;
import com.bwei.demo.di.component.DaggerDetailsComponent;
import com.bwei.demo.di.module.DetailsModule;
import com.bwei.demo.mvp.presenter.DetailsPresenter;
import com.bwei.demo.mvp.view.DetailsView;

import butterknife.BindView;
import cn.jzvd.JZVideoPlayerStandard;

public class  DetailsActivity extends BaseActivity<DetailsView, DetailsPresenter> implements DetailsView {


    @BindView(R.id.details_tab)
    TabLayout detailsTab;
    @BindView(R.id.details_rv)
    RecyclerView detailsRv;
    @BindView(R.id.details_title)
    TextView detailsTitle;
    @BindView(R.id.details_download)
    ImageView detailsDownload;
    @BindView(R.id.videoplayer)
    JZVideoPlayerStandard videoplayer;
    private DetailsBean.RetBean detailsRet;
    private CommentAdapter adapter;
    private String url;

    @Override
    protected int getLayout() {
        return R.layout.activity_deyails;
    }

    @Override
    protected void initView() {

        DaggerDetailsComponent.builder().detailsModule(new DetailsModule()).build().inject(this);
    }
    @Override
    protected void initData() {

        Intent intent = getIntent();
        String dataId = intent.getStringExtra("dataId");
        presenter.getDetailsData(dataId);
        detailsTab.addTab(detailsTab.newTab().setText("简介"));
        detailsTab.addTab(detailsTab.newTab().setText("评论"));

    }

    @Override
    protected DetailsPresenter getPresenter() {
        return new DetailsPresenter();
    }


    @Override
    public void returnDetailsData(DetailsBean details) {

        detailsRet = details.getRet();

        String hdurl = detailsRet.getSmoothURL();
        url = hdurl.substring(0, hdurl.length() - 4) + "mp4";

        Log.i("xxx", "returnDetailsData: " + url);
        videoplayer.setUp(url, JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");

        videoplayer.widthRatio = 4;//播放比例
        videoplayer.heightRatio = 3;

        Glide.with(this).load(detailsRet.getPic()).into(videoplayer.thumbImageView);
        detailsTitle.setText(detailsRet.getTitle());


        detailsDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(DetailsActivity.this, DownLoadActivity.class);
                intent1.putExtra("url", url);
                startActivity(intent1);
            }
        });

        if (adapter == null) {

            detailsRv.setLayoutManager(new LinearLayoutManager(this));
            adapter = new CommentAdapter(DetailsActivity.this, detailsRet);
            detailsRv.setAdapter(adapter);
        }
        adapter.notifyDataSetChanged();

    }
}