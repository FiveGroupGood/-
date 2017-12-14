package com.bwei.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwei.demo.R;
import com.bwei.demo.bean.DetailsBean;

import java.util.List;

/**
 * Created by ${李晨阳} on 2017/12/14.
 */

public class Comment_ItemAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<DetailsBean.RetBean.ListBean.ChildListBean> childList;

    public Comment_ItemAdapter(Context context, List<DetailsBean.RetBean.ListBean.ChildListBean> childList) {
        this.context = context;
        this.childList = childList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.comment_item2_layout, null);
        Item item = new Item(view);
        return item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof Item) {

            Glide.with(context).load(childList.get(position).getPic()).into(((Item) holder).iv);
            ((Item) holder).tv.setText(childList.get(position).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return childList.size();
    }

    class Item extends RecyclerView.ViewHolder {

        private ImageView iv;
        private TextView tv;

        public Item(View itemView) {
            super(itemView);

            iv = (ImageView) itemView.findViewById(R.id.comment_iv);
            tv = (TextView) itemView.findViewById(R.id.comment_tv);
        }
    }
}
