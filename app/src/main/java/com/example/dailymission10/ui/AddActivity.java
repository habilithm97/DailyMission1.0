package com.example.dailymission10.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.dailymission10.R;
import com.example.dailymission10.databinding.ActivityAddBinding;

public class AddActivity extends AppCompatActivity {
    private ActivityAddBinding addBinding;

    public static final String EXTRA_CONTENT = "com.example.dailymission10.EXTRA_CONTENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addBinding = DataBindingUtil.setContentView(this, R.layout.activity_add);

        initView();
    }

    private void initView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Toolbar 뒤로가기 버튼
        setTitle(R.string.add);

        addBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    private void save() {
        String content = addBinding.edtContent.getText().toString();
        if(content.trim().isEmpty()) {
            Toast.makeText(this, R.string.toast, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CONTENT, content);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { // Toolbar 뒤로가기 버튼 클릭 시 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}