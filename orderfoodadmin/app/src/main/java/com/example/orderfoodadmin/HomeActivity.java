package com.example.orderfoodadmin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orderfoodadmin.food.FoodListActivity;

public class HomeActivity extends AppCompatActivity {

    Button btnFood, btnUserManager;
    private String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnFood = findViewById(R.id.btnFood);
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