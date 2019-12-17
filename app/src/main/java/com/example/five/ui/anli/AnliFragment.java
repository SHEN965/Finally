package com.example.five.ui.anli;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.five.db.DBUtil;
import com.example.five.MainActivity;
import com.example.five.R;

public class AnliFragment extends Fragment {

    private AnliViewModel anliViewModel;
    private TextView tvTestResult;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        anliViewModel = ViewModelProviders.of(this).get(AnliViewModel.class);
        View root = inflater.inflate(R.layout.fragment_anli, container, false);
        tvTestResult= root.findViewById(R.id.tvTestResult);
        test();
        return root;
    }
    public void test()
    {
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                //测试数据库的语句,在子线程操作，在主线程好像不成功。
                String ret = DBUtil.QuerySQL();
                Message msg = new Message();
                msg.what=1001;
                Bundle data = new Bundle();
                data.putString("result", ret);
                msg.setData(data);
                mHandler.sendMessage(msg);
            }
        };
        new Thread(run).start();

    }

    Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what)
            {
                case 1001:
                    String str = msg.getData().getString("result");
                    tvTestResult.setText(str);
                    break;

                default:
                    break;
            }
        };
    };
}