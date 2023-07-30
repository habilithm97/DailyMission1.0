package com.example.dailymission10.ui;

import static com.example.dailymission10.ui.AddActivity.INSERT_MODE;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

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

                if(adapter.getItemCount() != 0) {
                    // 리스트 최하단으로 스크롤 이동
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mainBinding.recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
                        }
                    }, 100);
                }
            }
        });

        mainBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                resultLauncher.launch(intent);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                vm.delete(adapter.getPosition(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(mainBinding.recyclerView);
    }

    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent intent = result.getData();

                    if(result.getResultCode() == INSERT_MODE) {
                        if(intent != null) {
                            String content = intent.getStringExtra(AddActivity.EXTRA_CONTENT);
                            Todo todo = new Todo(content);
                            vm.insert(todo);
                        }
                    }
                }
            }
    );
}