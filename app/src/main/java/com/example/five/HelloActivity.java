package com.example.five;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class HelloActivity extends AppCompatActivity {
    private ImageView iv;
    Timer timer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_hello);
        iv = findViewById(R.id.iv);
        timer = new Timer(true);
        timer.schedule(hello,2000);
    }
    TimerTask hello = new TimerTask() {
        @Override
        public void run() {
            Intent intent = new Intent(HelloActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
}
