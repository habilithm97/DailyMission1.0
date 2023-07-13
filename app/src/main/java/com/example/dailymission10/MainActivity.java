package com.example.dailymission10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.dailymission10.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initView();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // RecyclerView 역순 출력
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mainBinding.recyclerView.setLayoutManager(layoutManager);
        mainBinding.recyclerView.setHasFixedSize(true); // 항상 고정된 사이즈의 RecyclerView
    }
}