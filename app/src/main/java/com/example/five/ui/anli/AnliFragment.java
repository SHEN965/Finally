package com.example.five.ui.anli;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.five.db.DBUtil;
import com.example.five.R;

public class AnliFragment extends Fragment {

    private AnliViewModel anliViewModel;
    private TextView tvTestResult;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        anliViewModel = ViewModelProviders.of(this).get(AnliViewModel.class);
        View root = inflater.inflate(R.layout.fragment_anli, container, false);

        return root;
    }

}