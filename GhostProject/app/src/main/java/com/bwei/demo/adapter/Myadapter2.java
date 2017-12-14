package com.bwei.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwei.demo.R;
import com.bwei.demo.bean.VideoType;

import java.util.List;

/**
 * dell 孙劲雄
 * 2017/12/13
 * 16:04
 */

public class Myadapter2 extends RecyclerView.Adapter<Myadapter2.Myviewholder> {
    private OnItemClickListener onItemClickListener;
    private List<VideoType> list;
    private Context context;

    public Myadapter2(List<VideoType> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public Myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View inflate = LayoutInflater.from(context).inflate(R.layout.item2, parent, false);

        final Myviewholder myviewholder=new Myviewholder(inflate);

        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){

                    int position = myviewholder.getPosition();

                    onItemClickListener.onClick(inflate, position);

                }
            }
        });

        return myviewholder;
    }

    @Override
    public void onBindViewHolder(Myviewholder holder, int position) {


        holder.textView.setText(list.get(position).title);
        holder.imageViewl.setScaleType(ImageView.ScaleType.FIT_XY);
        ViewGroup.LayoutParams params = holder.imageViewl.getLayoutParams();

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels / 2;//宽度为屏幕宽度一半
//          int height = data.getHeight()*width/data.getWidth();//计算View的高度
        params.height = (int) (width / 1.8);
        holder.imageViewl.setLayoutParams(params);
        Glide.with(context).load(list.get(position).pic).into(holder.imageViewl);

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class Myviewholder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView imageViewl;

        public Myviewholder(View itemView) {
            super(itemView);

            imageViewl= (ImageView) itemView.findViewById(R.id.img_video);
            textView=(TextView)itemView.findViewById(R.id.tv_title);

        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onClick(View view, int position);
    }
}
