package com.example.dailymission10.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "my_tb")
public class Todo {
    @PrimaryKey(autoGenerate = true) // 자동 ID 할당
    private int id;
    private String content;

    public Todo(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
