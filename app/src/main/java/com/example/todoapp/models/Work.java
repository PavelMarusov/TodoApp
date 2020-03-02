package com.example.todoapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

// создаем первый компонент Entity(модель базы данных)
@Entity // указывает что Work будет это таблица в базе данных
public class Work implements Serializable {
    @PrimaryKey(autoGenerate = true)// указывает что ID уникальный ключ,функция автогенерэт сам создаст уникальные ключи
    private long id;
    private String title;
    private String description;
    public Work(){}// пустой конструктор нужен для базы данных

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
