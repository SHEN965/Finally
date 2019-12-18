package com.example.five;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.five.Adapter.ProMesAdapter;
import com.example.five.db.Db;
import com.example.five.entity.Production;
import com.example.five.ui.home.HomeFragment;
import com.example.five.utils.ListUtil;

import java.util.List;


public class ProDetailsActivity extends AppCompatActivity{

    /**
     * 商品id
     */
    private String productId;

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

    private List<Production> list;

    private Handler uiHandler = new Handler(){

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pro_details);

        mContext = this;
        Intent intent = getIntent();
        productId = intent.getStringExtra("id");
        init();
        initCommodityData();
    }

    private void init(){
        detailImg = findViewById(R.id.detail_img);
        detailPrice = findViewById(R.id.detail_price);
        detailName = findViewById(R.id.detail_name);


        imgBack = findViewById(R.id.detail_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

    private void initCommodityData(){
        new AsyncTask<String,Void,List<Production>>(){
            @Override
            protected List<Production> doInBackground(String... strings) {
                return Db.Query(strings[0]);
            }
            @Override
            protected void onPostExecute(List<Production> productions) {
                super.onPostExecute(productions);
            }
        }.execute("select * from goods where goodsid='12' ");
    }




}
