package com.example.five.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



import com.bumptech.glide.Glide;
import com.example.five.R;
import com.example.five.entity.Production;

import java.util.List;

public class Adapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mlayoutInflater;
    private List<Production> list;
    public Production production;

    public Adapter(Context context,List<Production> list) {
        this.mContext = context;
        this.list = list;
        mlayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //写一个静态的class,把item_commodity的控件转移过来使用
    static class ViewHolder {
        public ImageView img;
        public TextView name;
        public TextView price;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        Production production = new Production();
        ViewHolder holder = null;
        production=list.get(position);
        if (convertView == null) {
            //填写ListView的图标和标题等控件的来源，来自于item_commodity这个布局文件
            //把控件所在的布局文件加载到当前类中
            convertView = mlayoutInflater.inflate(R.layout.item, null);
            //生成一个ViewHolder的对象
            holder = new ViewHolder();
            //获取控件对象
            holder.img = convertView.findViewById(R.id.img);
            holder.name = convertView.findViewById(R.id.name);
            holder.price = convertView.findViewById(R.id.price);
            //把ViewHolder对象封装到View对象中
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        Production production = list.get(position);
        String proname = production.getProName();
        String proprice = production.getProPrice();
        String imgurl = production.getImg_url();


        //修改空间属性
        holder.name.setText(proname);
//        holder.Grid_textview_name.setTextSize(10);

        holder.price.setText("￥ " + proprice + "元");
//        holder.Grid_textview_price.setTextSize(14);

        //加载第三方网络图片
        Glide.with(mContext).load(imgurl).into(holder.img);

        return convertView;
    }
}

