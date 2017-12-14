package com.bwei.demo.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bwei.demo.R;
import com.bwei.demo.activity.MainActivity;
import com.bwei.demo.adapter.Myadapter2;
import com.bwei.demo.base.BaseFragment;
import com.bwei.demo.bean.VideoHttpResponse;
import com.bwei.demo.bean.VideoRes;
import com.bwei.demo.bean.VideoType;
import com.bwei.demo.mvp.presenter.Findpresenter;
import com.bwei.demo.mvp.view.Findview;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import me.yuqirong.cardswipelayout.CardConfig;
import me.yuqirong.cardswipelayout.CardItemTouchHelperCallback;
import me.yuqirong.cardswipelayout.CardLayoutManager;
import me.yuqirong.cardswipelayout.OnSwipeListener;

/**
 * Created by发现
 * <p>
 * 发现
 */

public class DiscoverFragment extends BaseFragment<Findview, Findpresenter> implements Findview {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.dianwo)
    Button button;
    @Override
    protected void initView() {

        presenter.data();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.data();
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    public int layout() {
        return R.layout.discover_layout;
    }

    @Override
    protected Findpresenter getPresenter() {
        return new Findpresenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.data();
    }

    @Override
    public void Data(VideoHttpResponse<VideoRes> videoResVideoHttpResponse) {

        List<VideoType> list = null;

        for (int i=0;i<videoResVideoHttpResponse.getRet().list.size();i++){

          list = videoResVideoHttpResponse.getRet().list;
            

        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new MyAdapter(list,getActivity()));
        
        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(recyclerView.getAdapter(), list);
        cardCallback.setOnSwipedListener(new OnSwipeListener<VideoType>() {
            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
                MyAdapter.MyViewHolder myHolder = (MyAdapter.MyViewHolder) viewHolder;
                viewHolder.itemView.setAlpha(1 - Math.abs(ratio) * 0.2f);

            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, VideoType o, int direction) {
                MyAdapter.MyViewHolder myHolder = (MyAdapter.MyViewHolder) viewHolder;
                viewHolder.itemView.setAlpha(1f);
//                Toast.makeText(getActivity(), direction == CardConfig.SWIPED_LEFT ? "swiped left" : "swiped right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipedClear() {
                Toast.makeText(getActivity(), "data clear", Toast.LENGTH_SHORT).show();
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.data();
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                }, 3000L);
            }

        });
        final ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        final CardLayoutManager cardLayoutManager = new CardLayoutManager(recyclerView, touchHelper);
        recyclerView.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(recyclerView);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
    }


    private class MyAdapter extends RecyclerView.Adapter {

        private List<VideoType>  strings;
        private Context context;

        public MyAdapter(List<VideoType> strings, Context context) {
            this.strings = strings;
            this.context = context;
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.find, parent, false);

            final MyViewHolder myViewHolder=new MyViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = myViewHolder.getPosition();

//                 Intent intent=new Intent(getActivity(),);
//                    intent.putExtra("dataId",strings.get(position).dataId);
//                    startActivity(intent);


                }

            });

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

//            ViewGroup.LayoutParams params = ((MyViewHolder) holder).avatarImageView.getLayoutParams();

//            DisplayMetrics dm = context.getResources().getDisplayMetrics();
//            int width = dm.widthPixels / 2;//宽度为屏幕宽度一半
////          int height = data.getHeight()*width/data.getWidth();//计算View的高度
//            params.height = (int) (width / 1.8);
//         ((MyViewHolder) holder).avatarImageView.setLayoutParams(params);

            if (strings.get(position).title.length()>0){
          Glide.with(context).load(strings.get(position).pic).into(((MyViewHolder) holder).avatarImageView);


                ((MyViewHolder) holder).textView.setText(strings.get(position).title);
                ((MyViewHolder) holder).textView2.setText(strings.get(position).description);
            }
        }

        @Override
        public int getItemCount() {
            return strings.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView avatarImageView;
            TextView textView,textView2;
            MyViewHolder(View itemView) {
                super(itemView);
                avatarImageView = (ImageView) itemView.findViewById(R.id.iv_avatar);
                textView= (TextView) itemView.findViewById(R.id.title);
                textView2= (TextView) itemView.findViewById(R.id.jianjie);

            }

        }



    }


}
