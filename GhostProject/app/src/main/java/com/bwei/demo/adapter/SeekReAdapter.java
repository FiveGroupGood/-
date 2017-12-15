package com.bwei.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwei.demo.R;
import com.bwei.demo.bean.SeekBean;

import java.util.List;

/**
 * 1.类的用途
 * 2.@author 123
 * 3.@date 2017/12/15 11 :39
 */

public class SeekReAdapter extends RecyclerView.Adapter {
    private List<SeekBean.RetBean.ListBean> list;
    private LinearHolder linearHolder;
    private Context context;
    private OnItemClickLitener mOnItemClickLitener;
    private View view;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public SeekReAdapter(List<SeekBean.RetBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seek_item, null);
        //new一个我们的ViewHolder，findViewById操作都在LinearHolder的构造方法中进行了
        linearHolder = new LinearHolder(view);
        return linearHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        linearHolder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).load(list.get(position).getPic()).placeholder(R.mipmap.default_200).into(linearHolder.imageView);
        linearHolder.recycler_item.setText(list.get(position).getTitle());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getLayoutPosition();
                mOnItemClickLitener.onItemClick(linearHolder.itemView, pos);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class LinearHolder extends RecyclerView.ViewHolder {
        TextView recycler_item;
        ImageView imageView;

        public LinearHolder(View itemView) {
            super(itemView);
            recycler_item = (TextView) itemView.findViewById(R.id.seek_te);
            imageView = (ImageView) itemView.findViewById(R.id.seek_im);
        }
    }
    public interface OnItemClickLitener {
        /*点击事件*/
        void onItemClick(View view, int position);
    }
}
