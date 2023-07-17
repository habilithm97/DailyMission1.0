package com.example.dailymission10;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

// UI에 매칭시킬 데이터를 저장 및 처리하는 ViewModel 클래스
// Repository와 UI의 다리 역할을 함
// Observe를 통해 DataSet의 변화를 관찰하여 UI에 적용함
public class VM extends AndroidViewModel {
    private Repo repo;
    private LiveData<List<Todo>> allItems;

    public VM(@NonNull Application application) {
        super(application);
        repo = new Repo(application);
        allItems = repo.getAllItems();
    }

    public void insert(Todo todo) {
        repo.insert(todo);
    }

    public LiveData<List<Todo>> getAllItems() {
        return allItems;
    }
}
