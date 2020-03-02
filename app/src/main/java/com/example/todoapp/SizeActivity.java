package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.todoapp.ui.slideshow.SlideshowFragment;

public class SizeActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton rb14, rb22, rb28;
    EditText editText;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size);
        radioGroup = findViewById(R.id.radioGroup);
        rb14 = findViewById(R.id.RB_size_14);
        rb22 = findViewById(R.id.RB_size_22);
        rb28 = findViewById(R.id.RB_size_28);
        editText = findViewById(R.id.ed_text);
        rb14.setOnClickListener(listener);

        rb22.setOnClickListener(listener);

        rb28.setOnClickListener(listener);

    }


View.OnClickListener listener  = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent ();
        switch (v.getId()){
            case R.id.RB_size_14:
                intent.putExtra(SlideshowFragment.EXTRA_CODE,14);
                Log.d("pop","Size Fragment 14 clicked");
                break;
            case R.id.RB_size_22:
                intent.putExtra(SlideshowFragment.EXTRA_CODE,22);
                Log.d("pop","Size Fragment 22 clicked");
                break;
            case R.id.RB_size_28:
                intent.putExtra(SlideshowFragment.EXTRA_CODE,28);
                Log.d("pop","Size Fragment 28 clicked");
                break;
        }
        setResult(RESULT_OK,intent);
        finish();
    }
};




}
