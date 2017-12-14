package com.bwei.demo.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bwei.demo.R;
import com.bwei.demo.activity.SubjectActivity;
import com.bwei.demo.adapter.SubjectAdapter;
import com.bwei.demo.base.BaseFragment;
import com.bwei.demo.bean.RecommendBean;
import com.bwei.demo.mvp.presenter.SubjectPresenter;
import com.bwei.demo.mvp.view.SubjectView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by ${李晨阳} on 2017/12/12.
 * <p>
 * 专题
 */

public class SubjectFragment extends BaseFragment<SubjectView,SubjectPresenter> implements SubjectView{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private SubjectAdapter adapter;
    @Override
    protected void initView() {
        presenter.data();
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
         }

    @Override
    protected void initData() {

    }
    @Override
    public void onResume() {
        super.onResume();
        presenter.data();

    }
    @Override
    public int layout() {
        return R.layout.subject_layout;
    }

    @Override
    protected SubjectPresenter getPresenter() {

        return new SubjectPresenter();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
    }

    @Override
    public void Subject(RecommendBean recommendBean) {


        final List<RecommendBean.RetBean.ListBean.ChildListBean> list=new ArrayList<>();

        final List<RecommendBean.RetBean.ListBean> list1=new ArrayList<>();

        for(int i=0;i<recommendBean.getRet().getList().size();i++){

            if(!TextUtils.isEmpty(recommendBean.getRet().getList().get(i).getMoreURL())&&!TextUtils.isEmpty(recommendBean.getRet().getList().get(i).getTitle())){
                RecommendBean.RetBean.ListBean.ChildListBean childListBean = recommendBean.getRet().getList().get(i).getChildList().get(0);
                RecommendBean.RetBean.ListBean moreURL = recommendBean.getRet().getList().get(i);
                moreURL.setMoreURL(recommendBean.getRet().getList().get(i).getMoreURL());
                list1.add(moreURL) ;
                childListBean.setPic(childListBean.getPic());
                childListBean.setTitle(childListBean.getTitle());
                list.add(childListBean);

            }
            if(adapter==null){

                adapter =new SubjectAdapter(list,getActivity());

                recyclerView.setAdapter(adapter);

            }else{
                 adapter.notifyDataSetChanged();
            }
            adapter.setOnItemClickListener(new SubjectAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, int position) {

                    Intent intent=new Intent(getActivity(),SubjectActivity.class);

                    String catalogId = getCatalogId(list1.get(position).getMoreURL());

                    intent.putExtra("catalogId",catalogId);

                    intent.putExtra("title",list.get(position).getTitle());

                    startActivity(intent);

                }
            });

        }



    }
    public static String getCatalogId(String url) {
        String catalogId = "";
        String key = "catalogId=";
        if (!TextUtils.isEmpty(url) && url.contains("="))
            catalogId = url.substring(url.indexOf(key) + key.length(), url.lastIndexOf("&"));
        return catalogId;
    }
}
