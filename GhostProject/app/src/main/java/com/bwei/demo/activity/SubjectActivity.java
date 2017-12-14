package com.bwei.demo.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bwei.demo.R;
import com.bwei.demo.adapter.Myadapter2;
import com.bwei.demo.base.BaseActivity;
import com.bwei.demo.bean.VideoHttpResponse;
import com.bwei.demo.bean.VideoRes;
import com.bwei.demo.mvp.presenter.Subject2Presenter;
import com.bwei.demo.mvp.view.Subject2View;

import butterknife.BindView;

public class SubjectActivity extends BaseActivity<Subject2View, Subject2Presenter> implements Subject2View {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private Myadapter2 myadapter2;
    private String catalogId;
    private String title;

    @Override
    protected int getLayout() {
        return R.layout.activity_subject;
    }

    @Override
    protected void initView() {

        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));


        catalogId = getIntent().getStringExtra("catalogId");

        title = getIntent().getStringExtra("title");

    }

    @Override
    public void data(final VideoHttpResponse<VideoRes> httpResponse) {

        if (myadapter2 == null) {

            myadapter2 = new Myadapter2(httpResponse.getRet().list, SubjectActivity.this);

            recyclerView.setAdapter(myadapter2);

        } else {

            myadapter2.notifyDataSetChanged();

        }

        myadapter2.setOnItemClickListener(new Myadapter2.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {

                Intent intent = new Intent(SubjectActivity.this, DetailsActivity.class);

                intent.putExtra("dataId", httpResponse.getRet().list.get(position).dataId);

                startActivity(intent);

            }
        });


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.data(catalogId, "");
    }

    @Override
    protected Subject2Presenter getPresenter() {
        return new Subject2Presenter();
    }


}
