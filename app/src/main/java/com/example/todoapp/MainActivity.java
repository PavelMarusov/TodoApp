package com.example.todoapp;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.todoapp.ui.onBoard.OnBoardActivity;
import com.example.todoapp.ui.slideshow.SlideshowFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private final int RC_WRITE_EXTERNAL = 101;
    private File file;
    private File foldet;
    private EditText editText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isShow = Prefs.getInstance(this).isShown();
        if (!isShow) {
            startActivity(new Intent(this, OnBoardActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                startActivityForResult(intent, 100);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }
// создание файла и запись его в память способ 1 через метод
    @AfterPermissionGranted(RC_WRITE_EXTERNAL)// если файл создастся по новой запустит метот по реквест коду
    private void initFile(String content) {
        // разрешение пишем в манивесте
        String [] premision = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
        // проверка
        if (EasyPermissions.hasPermissions(this, premision)) {
            // создаем файл
            file = new File(Environment.getExternalStorageDirectory(), "TodoApp");
            file.mkdirs();//обязательно написать этот метод он создаст папку в памяти
            foldet = new File(file, "note.txt");// в нашу папку помешаем файл
            try {
                foldet.createNewFile();// метод создает файл
                FileOutputStream fos = new FileOutputStream(foldet);// метод записывает в фаил инфу
                fos.write(content.getBytes());// через метод write() записываем данные,
                fos.close();// заканчивааем запись
                Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            EasyPermissions.requestPermissions(this, "", RC_WRITE_EXTERNAL, premision);
        }
    }
    public void read(){
        File sdcard = new File(Environment.getExternalStorageDirectory(),"TodoApp") ;
        File file = new File(sdcard,"note.txt");
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
        }
        EditText editText = findViewById(R.id.ed_text);
        editText.setText(text.toString());
    }

    @Override
    public void onBackPressed() {
        editText  = findViewById(R.id.ed_text);
        initFile(editText.getText().toString());
        super.onBackPressed();

    }// закоментили что бы проверить второй спосов в тулсфрагменте



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        for(Fragment fragment : navHostFragment.getChildFragmentManager().getFragments()){
            fragment.onActivityResult(requestCode,resultCode,data);
        }
        if (resultCode == RESULT_OK && requestCode == 100) {
            String text = data.getStringExtra("title");

        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Prefs.getInstance(this).deleteAll();
                finish();
            case R.id.text_size:
                Intent intent = new Intent(MainActivity.this, SizeActivity.class);
                startActivity(intent);

        }


        return super.onOptionsItemSelected(item);
    }
}
