package com.example.five.ui.anli;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.five.Adapter.Adapter;
import com.example.five.Adapter.ProMesAdapter;
import com.example.five.ProDetailsActivity;
import com.example.five.db.DBUtil;
import com.example.five.R;
import com.example.five.db.Db;
import com.example.five.entity.Production;
import com.example.five.ui.home.HomeFragment;
import com.example.five.ui.star.StarFragment;
import com.example.five.utils.ListUtil;

import java.util.ArrayList;
import java.util.List;


public class AnliFragment extends Fragment {

    private AnliViewModel anliViewModel;
    private TextView tvTestResult;

    private Production production;
    private List<String> setTitlelist,setPricelist;
    private List<Production> mydata = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerAdapter mMyAdapter;
    private View view;//定义view用来设置fragment的layout
    private ProMesAdapter mProMesAdapter;
private TextView mView;
    private SwipeRefreshLayout mRefreshLayout;

    //最后一个可见Item的位置，关键所在
    private int lastVisibleItem;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private int mLastVisibleItem;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        anliViewModel = ViewModelProviders.of(this).get(AnliViewModel.class);
        view = inflater.inflate(R.layout.fragment_anli, container, false);
        initData();



        mRefreshLayout = view.findViewById(R.id.mSwipeRefresh);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //设置可见
                mRefreshLayout.setRefreshing(true);
                List<String> newSetTitlelist = new ArrayList<String>();
                List<String> newSetPricelist = new ArrayList<String>();
                for (int i = 0; i < 5; i++) {
                    int index = i + 1;
                    newSetTitlelist.add("more item" + index);
                    newSetPricelist.add("Price" + index);
                }
                mMyAdapter.addItem(newSetTitlelist,newSetPricelist);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //模拟加载时间，设置不可见
                        mRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        mRecyclerView = view.findViewById(R.id.mRecyclerView);
        mMyAdapter = new RecyclerAdapter(setTitlelist,setPricelist);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMyAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(view.getContext(), ((TextView)view).getText() + " click", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {

                Toast.makeText(view.getContext(), position + " Long click", Toast.LENGTH_SHORT).show();
                mMyAdapter.removeData(position);
            }
        });
        mRecyclerView.setAdapter(mMyAdapter);

        //设置滑动监听
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //判断是否滑动到底
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 ==mMyAdapter.getItemCount()) {
                    //滑动到底，需要改变状态为 上拉加载更多
                    mMyAdapter.changeMoreStatus(RecyclerAdapter.LOADING_MORE);
                    //模拟加载数据
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            List<String> newSetTitlelist = new ArrayList<String>();
                            List<String> newSetPricelist = new ArrayList<String>();
                            for (int i = 0; i < 5; i++) {
                                int index = i + 1;
                                newSetTitlelist.add("more item" + index);
                                newSetPricelist.add("Price" + index);
                            }
                            mMyAdapter.addMoreItem(newSetTitlelist,newSetPricelist);

                            //此时显示 正在加载中
                            mMyAdapter.changeMoreStatus(RecyclerAdapter.PULLUP_LOAD_MORE);
                        }
                    },2500);
                }
            }

            //更新最后可见位置
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();

            }
        });
        return view;

    }


    private void initData() {
        setTitlelist = new ArrayList<>();
        setPricelist = new ArrayList<>();
        for (int i = 0; i <= 20; i++) {
            setTitlelist.add(getItemName(i));
            setPricelist.add(getItemPrice(i));
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                mMyAdapter.addData(1);
                break;
            case R.id.delete:
                mMyAdapter.removeData(1);
                break;
        }
        return true;
    }

    public String getItemName(int i)
    {
        new AsyncTask<String,Void,List<Production>>(){
            @Override
            protected List<Production> doInBackground(String... strings) {
                return Db.Query(strings[0]);
            }
            @Override
            protected void onPostExecute(List<Production> productions) {
                super.onPostExecute(productions);
                production = productions.get(0);
                production.getProName();

            }
        }.execute("select * from goods goodsid="+i);

        return production.getProName();
    }


    public String getItemPrice(int i)
    {
        new AsyncTask<String,Void,List<Production>>(){
            @Override
            protected List<Production> doInBackground(String... strings) {
                return Db.Query(strings[0]);
            }
            @Override
            protected void onPostExecute(List<Production> productions) {
                super.onPostExecute(productions);
                production = productions.get(0);
                production.getProPrice();

            }
        }.execute("select * from goods goodsid="+i);

        return production.getProPrice();
    }

}