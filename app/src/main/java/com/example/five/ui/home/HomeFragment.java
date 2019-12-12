package com.example.five.ui.home;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProviders;


import com.example.five.R;
import com.example.five.ScanActivity;
import com.example.five.view.ImageBannerFramLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements ImageBannerFramLayout.FramLayoutLisenner {

    private View mView;
    private HomeViewModel homeViewModel;
    private ImageView scanView;
    private int SCAN_REQUEST_CODE = 200;
    private OnScanClickListener mListener;
    private EditText searchText;
    private ImageView btn_delete;
    private ImageBannerFramLayout mGroup;
    private int[] ids = new int[]{
            R.drawable.image_ac1,//图片资源1
            R.drawable.image_ac2//图片资源2
    };


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
        scanView = root.findViewById(R.id.btn_scanning);
        searchText = root.findViewById(R.id.search);
        btn_delete = root.findViewById(R.id.btn_delete);
        addclerListener(searchText, btn_delete);
        mGroup = root.findViewById(R.id.image_group);
        mGroup.setLisenner(this);
        ImageAction();


//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        scanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ScanActivity.class));
            }
        });

    }

    public static void addclerListener(final EditText e1, final ImageView m1) {
        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                // 监听如果输入串长度大于0那么就显示clear按钮。
                String s1 = s + "";
                if (s.length() > 0) {
                    m1.setVisibility(View.VISIBLE);
                } else {
                    m1.setVisibility(View.INVISIBLE);
                }
            }
        });
        m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // 清空输入框
                e1.setText("");
            }
        });
    }

    public void ImageAction(){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
//        mGroup = (ImageBannerFramLayout) findViewById(R.id.image_group);
//        mGroup.setLisenner(get);
        List<Bitmap> list = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),ids[i]);
            list.add(bitmap);
        }
        mGroup.addBitmaps(list);
    }

    //此处填写点击事件相关的业务代码
    @Override
    public void chickImageIndex(int pos) {
        Toast.makeText(getActivity(),"点击了第" + pos +  "张图片" , Toast.LENGTH_SHORT).show();
    }
}