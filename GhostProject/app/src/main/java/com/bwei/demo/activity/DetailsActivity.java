package com.bwei.demo.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwei.demo.R;
import com.bwei.demo.adapter.CommentAdapter;
import com.bwei.demo.adapter.IntroAdapter;
import com.bwei.demo.base.BaseActivity;
import com.bwei.demo.bean.CommentBean;
import com.bwei.demo.bean.DetailsBean;
import com.bwei.demo.di.component.DaggerDetailsComponent;
import com.bwei.demo.di.module.DetailsModule;
import com.bwei.demo.mvp.presenter.DetailsPresenter;
import com.bwei.demo.mvp.view.DetailsView;

import java.util.List;

import butterknife.BindView;
import cn.jzvd.JZVideoPlayerStandard;

public class  DetailsActivity extends BaseActivity<DetailsView, DetailsPresenter> implements DetailsView {


    @BindView(R.id.details_tab)
    TabLayout detailsTab;
    @BindView(R.id.details_rv1)
    RecyclerView detailsRv1;
    @BindView(R.id.details_rv2)
    RecyclerView detailsRv2;
    @BindView(R.id.details_title)
    TextView detailsTitle;
    @BindView(R.id.details_download)
    ImageView detailsDownload;
    @BindView(R.id.videoplayer)
    JZVideoPlayerStandard videoplayer;
    private DetailsBean.RetBean detailsRet;
    private IntroAdapter rv1Adapter;
    private String url;
    private List<CommentBean.RetBean.ListBean> commentList;
    private CommentAdapter rv2Adapter;

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

        String dataId = getIntent().getStringExtra("dataId");
        presenter.getDetailsData(dataId);
        detailsTab.addTab(detailsTab.newTab().setText("简介"));
        detailsTab.addTab(detailsTab.newTab().setText("评论"));

        detailsTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                String str = tab.getText().toString();
                if (str.equals("简介")) {

                    detailsRv1.setVisibility(View.VISIBLE);
                    detailsRv2.setVisibility(View.GONE);
                } else {


                    detailsRv2.setVisibility(View.VISIBLE);
                    detailsRv1.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //断点缓存
        detailsDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(DetailsActivity.this, DownLoadActivity.class);
                intent1.putExtra("url", url);
                startActivity(intent1);
            }
        });
    }

    @Override
    protected DetailsPresenter getPresenter() {
        return new DetailsPresenter();
    }

    /**
     * 返回简介数据
     *
     * @param details
     */
    @Override
    public void returnDetailsData(DetailsBean details) {

        detailsRet = details.getRet();

        String hdurl = detailsRet.getSmoothURL();
        url = hdurl.substring(0, hdurl.length() - 4) + "mp4";

        videoplayer.setUp(url, JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");

//        videoplayer.widthRatio = 4;//播放比例
//        videoplayer.heightRatio = 3;

        Glide.with(this).load(detailsRet.getPic()).into(videoplayer.thumbImageView);
        detailsTitle.setText(detailsRet.getTitle());

        if (rv1Adapter == null && detailsRet != null) {

            detailsRv1.setLayoutManager(new LinearLayoutManager(this));
            rv1Adapter = new IntroAdapter(DetailsActivity.this, detailsRet);
            detailsRv1.setAdapter(rv1Adapter);
        }


    }

    /**
     * 返回评论数据
     *
     * @param commentBean
     */
    @Override
    public void returnCommentData(CommentBean commentBean) {

        commentList = commentBean.getRet().getList();

        if (rv2Adapter == null && commentList != null) {

            detailsRv2.setLayoutManager(new LinearLayoutManager(DetailsActivity.this));
            rv2Adapter = new CommentAdapter(DetailsActivity.this, commentList);
            detailsRv2.setAdapter(rv2Adapter);
        }
    }
}