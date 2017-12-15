package com.bwei.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwei.demo.R;
import com.bwei.demo.bean.CommentBean;
import com.bwei.demo.utils.CircleImageView;

import java.util.List;

/**
 * Created by ${李晨阳} on 2017/12/15.
 */

public class CommentAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<CommentBean.RetBean.ListBean> commentList;

    public CommentAdapter(Context context, List<CommentBean.RetBean.ListBean> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(context, R.layout.details_comment_layout, null);
        Item item = new Item(view);
        return item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof Item) {

            if (commentList.get(position).getUserPic() != null) {

                Glide.with(context).load(commentList.get(position).getUserPic()).into(((Item) holder).userPc);
                ((Item) holder).msg.setText(commentList.get(position).getMsg());
                ((Item) holder).phoneNumber.setText(commentList.get(position).getPhoneNumber());
                ((Item) holder).time.setText(commentList.get(position).getTime());
            }
        }
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    class Item extends RecyclerView.ViewHolder {

        private CircleImageView userPc;
        private TextView phoneNumber, msg, time;

        public Item(View itemView) {
            super(itemView);

            userPc = (CircleImageView) itemView.findViewById(R.id.userPic);
            phoneNumber = (TextView) itemView.findViewById(R.id.phoneNumber);
            msg = (TextView) itemView.findViewById(R.id.msg);
            time = (TextView) itemView.findViewById(R.id.time);

        }
    }

}
