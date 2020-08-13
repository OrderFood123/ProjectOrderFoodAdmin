package com.example.orderfoodadmin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orderfoodadmin.food.FoodListActivity;

import type.TheLoaiActivity;

public class HomeActivity extends AppCompatActivity {

    Button btnFood, btnUserManager, btnCategory;
    private String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnCategory = findViewById(R.id.btn_category);
        btnFood = findViewById(R.id.btnFood);

        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "btnCategory onclick move activity");
                Intent intent = new Intent(HomeActivity.this, TheLoaiActivity.class);
                startActivity(intent);
            }
        });
        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "btnFood onclick move activity");
                Intent intent = new Intent(HomeActivity.this, FoodListActivity.class);
                startActivity(intent);
            }
        });
        btnUserManager = findViewById(R.id.btnUserManager);
        btnUserManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "btnUserManager onclick move activity");
                Intent intent = new Intent(HomeActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });
    }
}