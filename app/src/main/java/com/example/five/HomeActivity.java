package com.example.five;

import android.app.AppComponentFactory;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.five.ui.home.HomeFragment;

public class HomeActivity extends AppCompatActivity{

    EditText e1, e2;
    ImageView m1, m2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.frame);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        SearchCar();
    }

    private void SearchCar(){
        e1 = (EditText) findViewById(R.id.search);
        m1 = (ImageView) findViewById(R.id.btn_search);
        HomeFragment.addclerListener(e1,m1);
    }
}
