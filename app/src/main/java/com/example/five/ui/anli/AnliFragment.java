package com.example.five.ui.anli;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.five.R;

public class AnliFragment extends Fragment {

    private AnliViewModel anliViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        anliViewModel = ViewModelProviders.of(this).get(AnliViewModel.class);
        View root = inflater.inflate(R.layout.fragment_anli, container, false);
        final TextView textView = root.findViewById(R.id.text_anli);
        anliViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}