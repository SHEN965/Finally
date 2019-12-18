package com.example.five.ui.Details;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.five.R;
import com.example.five.entity.Production;

import java.util.Arrays;
import java.util.List;


public class ProDetailsFragment extends Fragment {

    /**
     * 商品id
     */
    private String productId = "";

    /**
     * 商品单价
     */
    private TextView detailPrice;

    /**
     * 商品名
     */
    private TextView detailName;

    /**
     * 商品图片
     */
    private ImageView detailImg;
    /**
     * 返回图标
     */
    private ImageView imgBack;

    private Context mContext;

    private Production production;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pro_details, container, false);

        Intent intent = new Intent();
        productId = intent.getStringExtra("id");
        init(view);
        initCommodity(production);
        return view;
    }




    private void init(View view){
        detailImg = view.findViewById(R.id.detail_img);
        detailPrice = view.findViewById(R.id.detail_price);
        detailName = view.findViewById(R.id.detail_name);

        imgBack = view.findViewById(R.id.detail_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }




    private void initCommodity(Production production) {
        if (production != null) {
            //商品信息以、单价、简介和剩余
            detailName.setText(production.getProName());
            detailPrice.setTextColor(Color.RED);
            detailPrice.setText("￥ " + production.getProPrice() + " 元");
        }

    }
}
