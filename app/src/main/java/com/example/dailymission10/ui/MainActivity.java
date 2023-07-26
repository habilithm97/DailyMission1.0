package com.example.dailymission10.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dailymission10.R;
import com.example.dailymission10.adapter.ItemAdapter;
import com.example.dailymission10.databinding.ActivityMainBinding;
import com.example.dailymission10.room.Todo;
import com.example.dailymission10.viewmodel.VM;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;
    private ItemAdapter adapter;
    private VM vm;

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

        adapter = new ItemAdapter();
        mainBinding.recyclerView.setAdapter(adapter);

        vm = new ViewModelProvider(this).get(VM.class);
        // 리스트에 포함된 객체들을 관찰함
        vm.getAllItems().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                adapter.submitList(todos); // 리스트 데이터 업데이트
            }
        });

        mainBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, 97);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if(requestCode == 97) {
            if(resultCode == RESULT_OK) {
                String content = intent.getStringExtra(AddActivity.EXTRA_CONTENT);
                Todo todo = new Todo(content);
                vm.insert(todo);
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }
}