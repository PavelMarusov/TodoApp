package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class FormActivity extends AppCompatActivity {
private EditText editTitle;
private EditText editDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        editTitle = findViewById(R.id.editTitle);
        editDesc = findViewById(R.id.editDesc);
    }

    public void onClick(View view) {
        String text = editTitle.getText().toString().trim();
        Intent intent = new Intent();
        intent.putExtra("title",text);
        setResult(RESULT_OK,intent);
        finish();
    }
}
