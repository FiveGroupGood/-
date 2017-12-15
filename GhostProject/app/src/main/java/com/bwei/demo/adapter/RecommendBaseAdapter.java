package com.bwei.demo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwei.demo.R;
import com.bwei.demo.bean.RecommendBean;

import java.util.List;

/**
 * 1.类的用途
 * 2.@author 123
 * 3.@date 2017/12/13 15 :43
 */

public class RecommendBaseAdapter extends BaseAdapter {
    private List<RecommendBean.RetBean.ListBean> list;
    private Context context;
    public RecommendBaseAdapter(List<RecommendBean.RetBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler;
        if(convertView == null){
            convertView = View.inflate(context, R.layout.recommend_item, null);
            viewHodler = new ViewHodler();
            viewHodler.imageView= (ImageView) convertView.findViewById(R.id.item_im);
            viewHodler.textView = (TextView) convertView.findViewById(R.id.item_tv);
            convertView.setTag(viewHodler);
        }else{
            viewHodler = (ViewHodler) convertView.getTag();
        }
        List<RecommendBean.RetBean.ListBean.ChildListBean> childList = list.get(4).getChildList();
        String title = childList.get(position).getTitle();
        viewHodler.textView.setText(title);
        viewHodler.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).load(childList.get(position).getPic()).placeholder(R.mipmap.default_320).into(viewHodler.imageView);


        return convertView;
    }
    class  ViewHodler{
        ImageView imageView;
        TextView textView;
    }
}
