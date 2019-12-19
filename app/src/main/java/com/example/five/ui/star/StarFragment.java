package com.example.five.ui.star;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.five.Adapter.Adapter;
import com.example.five.Adapter.ProMesAdapter;
import com.example.five.MainActivity;
import com.example.five.R;
import com.example.five.db.Db;
import com.example.five.entity.Production;
import com.example.five.ui.home.HomeFragment;
import com.example.five.utils.ListUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StarFragment extends Fragment {

    private StarViewModel starViewModel;

    private ProgressBar starProgressBar;
    private ListView starProductListView;
    private List<Production> listItemStar = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        starViewModel = ViewModelProviders.of(this).get(StarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_star, container, false);
        initView(root);
        test();
        return root;
    }
    private void initView(View view){
        starProgressBar = view.findViewById(R.id.star_progress);
        starProductListView = view.findViewById(R.id.star_listview);
    }
    public void test()
    {
        new AsyncTask<String,Void,List<Production>>(){
            @Override
            protected List<Production> doInBackground(String... strings) {
                return Db.Query(strings[0]);
            }
            @Override
            protected void onPostExecute(List<Production> productions) {
                super.onPostExecute(productions);
                listItemStar = ListUtil.getRandomList(productions, 100);
                //模拟获取热评商品
                starProductListView.setAdapter(new Adapter(StarFragment.this.getContext(),listItemStar));
                starProgressBar.setVisibility(View.GONE);
                starProductListView.setVisibility(View.VISIBLE);
            }
        }.execute("select * from goods");
    }
}