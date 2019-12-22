package com.example.five.ui.anli;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.five.Adapter.ProMesAdapter;
import com.example.five.R;
import com.example.five.entity.Production;
import com.example.five.utils.ListUtil;

import java.util.ArrayList;
import java.util.List;


public class AnliFragment extends Fragment {

    private AnliViewModel anliViewModel;
    private TextView tvTestResult;

    private Production production;
    private List<String> setTitlelist,setPricelist,setImagelist,setIdlist;
    private List<String> newSetTitlelist,newSetPricelist,newSetImagelist,newsetIdlist;

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
                List<String> addSetTitlelist = new ArrayList<String>();
                List<String> addSetPricelist = new ArrayList<String>();
                List<String> addSetImagelist = new ArrayList<String>();
                List<String> addSetIdlist = new ArrayList<String>();

                for (int i = 10; i < 15; i++) {
                    addSetTitlelist.add(newSetTitlelist.get(i));
                    addSetPricelist.add(newSetPricelist.get(i));
                    addSetImagelist.add(newSetImagelist.get(i));
                    addSetIdlist.add(newsetIdlist.get(i));
                }
                mMyAdapter.addItem(addSetTitlelist,addSetPricelist,addSetImagelist,addSetIdlist);
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
        mMyAdapter = new RecyclerAdapter(AnliFragment.this.getContext(),setTitlelist,setPricelist,setImagelist,setIdlist);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMyAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(view.getContext(), ((TextView)view).getText(), Toast.LENGTH_SHORT).show();
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
                            List<String> addSetTitlelist = new ArrayList<String>();
                            List<String> addSetPricelist = new ArrayList<String>();
                            List<String> addSetImagelist = new ArrayList<String>();
                            List<String> addSetIdlist = new ArrayList<String>();
                            for (int i = 10; i < 15; i++) {
                                addSetTitlelist.add(newSetTitlelist.get(i));
                                addSetPricelist.add(newSetPricelist.get(i));
                                addSetImagelist.add(newSetImagelist.get(i));
                                addSetIdlist.add(newsetIdlist.get(i));
                            }
                            mMyAdapter.addMoreItem(addSetTitlelist,addSetPricelist,addSetImagelist,addSetIdlist);

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
        setImagelist = new ArrayList<>();
        setIdlist = new ArrayList<>();
        newSetTitlelist = new ArrayList<>();
        newSetPricelist = new ArrayList<>();
        newSetImagelist = new ArrayList<>();
        newsetIdlist = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            InitData initData = new InitData();
            initData.setmAsyncResponse(new InitData.AsyncResponse() {
                @Override
                public void setNameData(String i) {
                    setTitlelist.add(i);
                }

                @Override
                public void setPriceData(String i) {
                    setPricelist.add(i);
                }

                @Override
                public void setImageData(String i) {
                    setImagelist.add(i);
                }

                @Override
                public void setIdData(String i) {
                    setIdlist.add(i);
                }

            });
            initData.execute("select * from goods where goodsid="+i);
        }
        for (int i = 1; i <= 30; i++) {
            InitData initData = new InitData();
            initData.setmAsyncResponse(new InitData.AsyncResponse() {
                @Override
                public void setNameData(String i) {
                    newSetTitlelist.add(i);
                }

                @Override
                public void setPriceData(String i) {
                    newSetPricelist.add(i);
                }

                @Override
                public void setImageData(String i) {
                    newSetImagelist.add(i);
                }

                @Override
                public void setIdData(String i) {
                    newsetIdlist.add(i);
                }

            });
            initData.execute("select * from goods where goodsid="+i);
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


}