package com.example.dailymission10;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Todo.class}, version = 1)
public abstract class RoomDB extends RoomDatabase {
    private static String DB_NAME = "my_db";
    private static RoomDB instance;
    public abstract RoomDao roomDao();

    // 최초 DB 생성 시에는 해당 작업이 먼저 이뤄져야 하므로 동기화를 시킴
    public static synchronized RoomDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, DB_NAME)
                    //.fallbackToDestructiveMigration() // 버전이 변경되었을 때 이전의 데이터를 모두 삭제하고 새로 생성함
                    .addCallback(roomCallback) // Callback 메소드로 onCreate()를 작성하면 최초 DB 생성 시의 작업을 선언할 수 있음
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new InitDBTask(instance).execute();
        }
    };

    private static class InitDBTask extends AsyncTask<Void, Void, Void> {
        private RoomDao roomDao;

        private InitDBTask(RoomDB roomDB) {
            roomDao = roomDB.roomDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            roomDao.insert(new Todo(0, "큐브"));
            roomDao.insert(new Todo(1, "운동"));
            roomDao.insert(new Todo(2, "게임"));
            roomDao.insert(new Todo(3, "산책"));
            roomDao.insert(new Todo(4, "데이트"));
            roomDao.insert(new Todo(5, "공부"));
            return null;
        }
    }
}
