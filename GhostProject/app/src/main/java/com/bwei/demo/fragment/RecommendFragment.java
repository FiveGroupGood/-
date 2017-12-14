package com.bwei.demo.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bwei.demo.R;
import com.bwei.demo.activity.DetailsActivity;
import com.bwei.demo.activity.SeekActivity;
import com.bwei.demo.adapter.RecommendBaseAdapter;
import com.bwei.demo.base.BaseFragment;
import com.bwei.demo.bean.RecommendBean;
import com.bwei.demo.mvp.presenter.RecommendPre;
import com.bwei.demo.mvp.view.RecommendView;
import com.bwei.demo.utils.ImageLoaderBanner;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ${郭冲冲} on 2017/12/12.
 * <p>
 * 精选
 */

public class RecommendFragment extends BaseFragment<RecommendView, RecommendPre> implements RecommendView {

    @BindView(R.id.recommend_list)
    ListView listView;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    private View view;
    private Banner banner;
    private LinearLayout linearLayout;


    @Override
    protected void initView() {
        view = View.inflate(getActivity(), R.layout.bann, null);
        banner = (Banner) view.findViewById(R.id.recommend_bann);
        linearLayout = (LinearLayout) view.findViewById(R.id.ll);
        linearLayout.getBackground().setAlpha(100);
        presenter.r();
    }

    @Override
    protected void initData() {
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SeekActivity.class));
            }
        });
        listView.addHeaderView(view);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {
                    toolbarTitle.setVisibility(View.GONE);
                } else if (firstVisibleItem == 1) {
                    toolbarTitle.setVisibility(View.VISIBLE);
                }

            }
        });


    }

    @Override
    public int layout() {

        return R.layout.recommend_layout;
    }

    @Override
    protected RecommendPre getPresenter() {
        return new RecommendPre();
    }


    @Override
    public void showView(RecommendBean bean) {
        List<String> bann_lists = new ArrayList<>();
        RecommendBean.RetBean ret = bean.getRet();
        final List<RecommendBean.RetBean.ListBean> list = ret.getList();
        List<RecommendBean.RetBean.ListBean.ChildListBean> childList = list.get(0).getChildList();
        banner.setImageLoader(new ImageLoaderBanner());
        for (RecommendBean.RetBean.ListBean.ChildListBean cb : childList) {
            cb.getDataId();
            String pic = cb.getPic();
            bann_lists.add(pic);
        }
        banner.setImages(bann_lists);
        banner.start();
        RecommendBaseAdapter adapter = new RecommendBaseAdapter(list, getActivity());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<RecommendBean.RetBean.ListBean.ChildListBean> childList1 = list.get(4).getChildList();
                String dataId = childList1.get(position-1).getDataId();
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("dataId",dataId);
                startActivity(intent);



            }
        });
    }

}
