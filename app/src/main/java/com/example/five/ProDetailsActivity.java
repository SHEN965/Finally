package com.example.five;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.five.Adapter.Adapter;
import com.example.five.Adapter.ProMesAdapter;
import com.example.five.db.Db;
import com.example.five.entity.Production;
import com.example.five.ui.home.HomeFragment;
import com.example.five.ui.star.StarFragment;
import com.example.five.utils.ListUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    private int star;
    /**
     * 返回图标
     */
    private ImageView imgBack;

    private Context mContext;


    private List<Production> list;
    private Production production;
    private Adapter adapter;
    //收藏按钮
    private Button btn_buy;
    private String text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pro_details);
        mContext = this;
        Intent intent = getIntent();
        productId = intent.getStringExtra("id");
        star=intent.getIntExtra("star",0);
        btn_buy = (Button)findViewById(R.id.btn_buy);
        if(star==1){
            btn_buy.setText("已收藏");
        }
        init();
        initCommodityData();
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                text = btn_buy.getText().toString();
                if(text=="收藏"){
                    new Thread() {
                        public void run() {
                            try {
                                //insert("admin", "222");   //上传调用
                                // delete(8);               //删除调用
                                update(1,productId);        //修改调用
                                adapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    btn_buy.setText("已收藏");
                }
                else{
                    new Thread() {
                        public void run() {
                            try {
                                //insert("admin", "222");   //上传调用
                                // delete(8);               //删除调用
                                update(0,productId);        //修改调用
                                adapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    btn_buy.setText("收藏");
                }
            }
        }) ;
    }
    public void update(int a,String b) throws Exception {
        Connection m_con = null;
        String sql = "UPDATE goods SET star=? WHERE goodsid=?" ;
        PreparedStatement pstmt = null ;
        Class.forName("net.sourceforge.jtds.jdbc.Driver");    //加载MYSQL JDBC驱动程序
        Log.d("加载驱动", "成功");
//      m_con =(Connection) DriverManager.getConnection("jdbc:jtds:sqlserver://"+ "175.102.4.147" + ":1433/" + "数据名" ,"用户名", "密码");
        m_con =(Connection) DriverManager.getConnection("jdbc:jtds:sqlserver://"+ "10.148.119.177" + ":1433/" + "guzzi" ,"sa", "123456");
        try {
            pstmt = m_con.prepareStatement(sql) ;
            pstmt.setInt(1,a) ;
            pstmt.setString(2,b) ;
            pstmt.executeUpdate();
            pstmt.close() ;
        } catch (Exception e) {
            throw new Exception("操作中出现错误！！！") ;
        } finally {
            m_con.close() ;
        }
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


    public void initCommodityData(){
        new AsyncTask<String,Void,List<Production>>(){
            @Override
            protected List<Production> doInBackground(String... strings) {
                return Db.Query(strings[0]);
            }
            @Override
            protected void onPostExecute(List<Production> productions) {
                super.onPostExecute(productions);
                production = productions.get(0);
                String imgurl = production.getImg_url();

                //商品信息以、单价、简介和剩余
                Glide.with(mContext).load(imgurl).into(detailImg);
                detailName.setText(production.getProName());
                detailPrice.setTextColor(Color.RED);
                detailPrice.setText("￥ " + production.getProPrice());
            }
        }.execute("select * from goods where goodsid="+productId);
    }
}

