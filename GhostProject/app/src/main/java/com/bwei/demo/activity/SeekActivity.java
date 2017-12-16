package com.bwei.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.demo.R;
import com.bwei.demo.adapter.SeekReAdapter;
import com.bwei.demo.base.BaseActivity;
import com.bwei.demo.bean.SeekBean;
import com.bwei.demo.mvp.presenter.SeekPresenter;
import com.bwei.demo.mvp.view.SeekView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeekActivity extends BaseActivity<SeekView, SeekPresenter> implements SeekView {

    @BindView(R.id.seek_et)
    EditText seekEt;
    @BindView(R.id.seek_tv)
    TextView seekTv;
    @BindView(R.id.seek_view)
    RecyclerView seekView;

    @Override
    protected int getLayout() {
        return R.layout.activity_seek;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        seekEt.getBackground().setAlpha(100);
        seekTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = seekEt.getText().toString();
                if(TextUtils.isEmpty(s)){
                    Toast.makeText(SeekActivity.this, "请输入要搜索的关键字", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    presenter.getSeek(s);
                }
            }
        });
    }

    @Override
    protected SeekPresenter getPresenter() {
        return new SeekPresenter();
    }


    @Override
    public void showSeek(SeekBean seekBean) {
        SeekBean.RetBean ret = seekBean.getRet();
        final List<SeekBean.RetBean.ListBean> list = ret.getList();
        SeekReAdapter adapter = new SeekReAdapter(list,SeekActivity.this);
        seekView.setLayoutManager(new GridLayoutManager(SeekActivity.this,3));
        seekView.setAdapter(adapter);
        adapter.setOnItemClickLitener(new SeekReAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

                String dataId = list.get(position).getDataId();
                Intent intent = new Intent(SeekActivity.this,DetailsActivity.class);
                intent.putExtra("dataid",dataId);
                startActivity(intent);
            }
        });


    }



}
