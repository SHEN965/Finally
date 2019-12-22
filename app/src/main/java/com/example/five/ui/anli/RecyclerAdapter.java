package com.example.five.ui.anli;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.five.Adapter.Adapter;
import com.example.five.R;
import com.example.five.db.Db;
import com.example.five.entity.Production;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //上拉状态0：上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //上拉状态1：正在加载中
    public static final int LOADING_MORE = 1;

    private static final int TYPE_FOOTER = 0;//带Footer的Item
    private static final int TYPE_NORMAL = 1;//不带Footer的Item

    //数据源
    private List<String> mTitleList,mPriceList,mImagelist,mIdList;
    //上拉加载状态，默认为状态0-上拉加载更多
    private int load_more_status = 0;

    private Context context;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public RecyclerAdapter(Context context,List<String> getTitleList,List<String> getPriceList,List<String> getImagelist,List<String> getIdList) {
        this.context = context;
        mTitleList = getTitleList;
        mPriceList = getPriceList;
        mImagelist = getImagelist;
        mIdList = getIdList;

    }
    //定义点击事件的接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    //点击事件的监听
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    //根据Item位置返回viewType，供onCreateViewHolder方法内获取不同的Holder
    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    //返回item个数，由于footerview的存在，所以item个数要+1
    @Override
    public int getItemCount() {
        return mTitleList.size() + 1;
    }

    //创建ViewHolder，如果是header或者footer直接返回响应holder即可
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            return new NormalHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false));
        } else {
            return new FooterHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer, parent, false));
        }
    }





    //填充视图
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        //普通Item，直接设置
        if (holder instanceof NormalHolder) {
            //为普通Item填充数据
            ((NormalHolder) holder).mTitle.setText(mTitleList.get(position));
            ((NormalHolder) holder).mPrice.setText(mPriceList.get(position));
            ((NormalHolder) holder).mId.setText(mIdList.get(position));
            if (context != null){
                Glide.with(context).load(mImagelist.get(position)).into(((NormalHolder) holder).mImg);
            }

            //设置监听
            if (mOnItemClickListener!=null){
                ((NormalHolder) holder).mTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(((NormalHolder) holder).mTitle,position);
                    }
                });
                ((NormalHolder) holder).mTitle.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mOnItemClickListener.onItemLongClick(((NormalHolder) holder).mTitle,position);
                        return false;
                    }
                });
            }
        }else {
            //footerview，需要判断上拉状态

            switch (load_more_status){
                //状态：上拉加载更多
                case PULLUP_LOAD_MORE:
                    ((FooterHolder) holder).mView.setText("上拉加载更多哦");
                    break;
                //状态：正在加载中
                case LOADING_MORE:
                    ((FooterHolder) holder).mView.setText("正在加载中哦");
                    break;
            }
        }
    }



    //改变当前上拉状态
    public void changeMoreStatus(int status){
        load_more_status= status;
        notifyDataSetChanged();
    }

    //Footer对应Holder
    public class FooterHolder extends RecyclerView.ViewHolder {
        private TextView mView;

        public FooterHolder(View itemView) {
            super(itemView);
            mView = itemView.findViewById(R.id.footer);
        }
    }
    //普通Item对应的Holder
    public class NormalHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public TextView mPrice;
        public TextView mId;
        public ImageView mImg;
        public LinearLayout my_item;

        public NormalHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.my_title);
            mPrice = itemView.findViewById(R.id.my_content);
            mImg =  itemView.findViewById(R.id.my_img);
            mId = itemView.findViewById(R.id.anliId);
            my_item = itemView.findViewById(R.id.my_item);
        }
    }
    //头部添加Item，供上拉刷新时调用
    public void addItem(List<String> getTitleList,List<String> getPriceList,List<String> getImageList,List<String> getIdList){
        getTitleList.addAll(mTitleList);
        getPriceList.addAll(mPriceList);
        getImageList.addAll(mImagelist);
        getIdList.addAll(mIdList);
        mTitleList.clear();
        mPriceList.clear();
        mImagelist.clear();
        mIdList.clear();
        mTitleList.addAll(getTitleList);
        mPriceList.addAll(getPriceList);
        mImagelist.addAll(getImageList);
        mIdList.addAll(getIdList);
        notifyDataSetChanged();
    }
    //末尾添加Item，供上拉加载更多时调用
    public void addMoreItem(List<String> getTitleList,List<String> getPriceList,List<String> getImageList,List<String> getIdList){
        mTitleList.addAll(getTitleList);
        mPriceList.addAll(getPriceList);
        mImagelist.addAll(getImageList);
        mIdList.addAll(getIdList);
        notifyDataSetChanged();
    }

    //menu添加
    public void addData(int position) {
        mTitleList.add(position, "Insert One");
        mPriceList.add(position, "Insert One");
        mImagelist.add(position, "Insert One");
        notifyItemInserted(position);
    }

    //menu删除
    public void removeData(int position) {
        mTitleList.remove(position);
        mPriceList.remove(position);
        mImagelist.remove(position);
        notifyItemRemoved(position);
    }



}
