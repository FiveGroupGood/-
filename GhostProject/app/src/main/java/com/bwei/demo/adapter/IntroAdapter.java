package com.bwei.demo.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwei.demo.R;
import com.bwei.demo.bean.DetailsBean;
import com.bwei.demo.utils.ExpandableTextView;

import java.util.List;

/**
 * Created by ${李晨阳} on 2017/12/13.
 */

public class IntroAdapter extends RecyclerView.Adapter {

    private Context context;
    private DetailsBean.RetBean detailsRet;
    private int TYPE_ONE = 1;
    private int TYPE_TWO = 2;
    private View view;

    public IntroAdapter(Context context, DetailsBean.RetBean detailsRet) {
        this.context = context;
        this.detailsRet = detailsRet;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ONE) {

            view = View.inflate(context, R.layout.details_item1_layout, null);
            Item1 item1 = new Item1(view);
            return item1;
        } else if (viewType == TYPE_TWO) {

            //     view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_item2_layout, null);
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_item2_layout, null);
            Item2 item2 = new Item2(view);
            return item2;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof Item1) {

            ((Item1) holder).comment_director.setText("导演：" + detailsRet.getDirector());
            ((Item1) holder).comment_actors.setText("主演：" + detailsRet.getActors());

            ((Item1) holder).expandable_text.setText("简介：" + detailsRet.getDescription(), detailsRet.isFlgh());
            ((Item1) holder).expandable_text.setListener(new ExpandableTextView.OnExpandStateChangeListener() {
                @Override
                public void onExpandStateChanged(boolean isExpanded) {
                    detailsRet.setFlgh(isExpanded);
                }
            });

        } else if (holder instanceof Item2) {

            ((Item2) holder).comment_rv.setLayoutManager(new GridLayoutManager(context, 3));
            final List<DetailsBean.RetBean.ListBean.ChildListBean> childList = detailsRet.getList().get(0).getChildList();
            ((Item2) holder).comment_rv.setAdapter(new Intro_ItemAdapter(context, childList));
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {

            return TYPE_ONE;
        } else {

            return TYPE_TWO;
        }
    }

    class Item1 extends RecyclerView.ViewHolder {

        private TextView comment_director, comment_actors;
        ExpandableTextView expandable_text;

        public Item1(View itemView) {
            super(itemView);

            comment_director = (TextView) itemView.findViewById(R.id.comment_director);
            comment_actors = (TextView) itemView.findViewById(R.id.comment_actors);
            expandable_text = (ExpandableTextView) itemView.findViewById(R.id.expandable_text);
        }
    }

    class Item2 extends RecyclerView.ViewHolder {

        private RecyclerView comment_rv;

        public Item2(View itemView) {
            super(itemView);

            comment_rv = (RecyclerView) itemView.findViewById(R.id.comment_rv);
        }
    }
}
