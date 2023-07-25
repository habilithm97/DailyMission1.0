package com.example.dailymission10.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // 충돌 처리 방식 -> PrimaryKey가 겹칠 경우 덮어쓰기
    void insert(Todo todo);

    @Query("select * from my_tb")
    LiveData<List<Todo>> getAll();
}
