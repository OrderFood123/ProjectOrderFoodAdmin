package com.example.orderfoodadmin.food;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfoodadmin.Adapter.FoodAdapter;
import com.example.orderfoodadmin.model.Food;
import com.example.orderfoodadmin.R;
import com.example.orderfoodadmin.Adapter.FoodAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FoodListActivity extends AppCompatActivity {

    FloatingActionButton btnAdd;
    FloatingActionButton btnReload;
    int count = 0;
    private RecyclerView recyclerView;
    private ArrayList<Food> arrayList = new ArrayList<>();
    private FoodAdapter adapter;
    private String TAG = "FoodListActivity";
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        mProgressBar = findViewById(R.id.progress);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("food");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Log.e(TAG, snapshot.toString());
                        String key = dataSnapshot.getKey();
                        String description = dataSnapshot.child("Description").getValue().toString();
                        String discount = dataSnapshot.child("Discount").getValue().toString();
                        String image = dataSnapshot.child("Image").getValue().toString();
                        String menuID = dataSnapshot.child("MenuID").getValue().toString();
                        String name = dataSnapshot.child("Name").getValue().toString();
                        String price = dataSnapshot.child("Price").getValue().toString();
                        Log.e(TAG, "data: " + description + " / " + discount + " / " + image + " / " + menuID + " / " + name + " / " + price);
                        Food food = new Food(
                                key,
                                description,
                                discount,
                                image,
                                menuID,
                                name,
                                price
                        );
                        Log.e(TAG, "Food: " + food.getKey());
                        arrayList.add(food);
                        Log.e(TAG, "arrayList: " + arrayList.size());
                        count = arrayList.size();
                    }
                    adapter = new FoodAdapter(arrayList, FoodListActivity.this);
                    adapter.notifyDataSetChanged();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FoodListActivity.this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapter);

                }
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "ERROR" + error);
            }
        });

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "btnAdd onclick and count: " + count);
                Intent intent = new Intent(FoodListActivity.this, FoodAddActivity.class);
                intent.putExtra("count", count);
                startActivity(intent);
            }
        });

        btnReload = findViewById(R.id.btnReLoad);
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart");
        finish();
        startActivity(getIntent());
    }


}