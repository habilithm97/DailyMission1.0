package com.example.dailymission10.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.dailymission10.room.RoomDB;
import com.example.dailymission10.room.RoomDao;
import com.example.dailymission10.room.Todo;

import java.util.List;

// 로컬 DB인 Room과 ViewModel 간의 다리 역할을 해주는 Repository 클래스
public class Repo {
    private RoomDao roomDao;
    private LiveData<List<Todo>> allItems;

    public Repo(Application application) {
        RoomDB roomDB = RoomDB.getInstance(application);
        roomDao = roomDB.roomDao();
        allItems = roomDao.getAll();
    }

    public void insert(Todo todo) {
        new InsertItemTask(roomDao).execute(todo);
    }

    public LiveData<List<Todo>> getAllItems() {
        return allItems;
    }

    private static class InsertItemTask extends AsyncTask<Todo, Void, Void> {
        private RoomDao roomDao;

        public InsertItemTask(RoomDao roomDao) {
            this.roomDao = roomDao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            roomDao.insert(todos[0]);
            return null;
        }
    }
}
