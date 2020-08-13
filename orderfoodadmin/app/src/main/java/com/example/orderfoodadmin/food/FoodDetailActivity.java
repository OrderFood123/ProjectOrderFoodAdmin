package com.example.orderfoodadmin.food;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.orderfoodadmin.Adapter.FoodAdapter;
import com.example.orderfoodadmin.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FoodDetailActivity extends AppCompatActivity {

    String description;
    String discount;
    String image;
    String menuID;
    String name;
    String price;
    String key;

    ImageView ivFood;
    TextView tvDescription;
    TextView tvName;
    TextView tvPrice;
    TextView tvDiscount;
    Button btnEdit;
    Button btnDelete;
    private String TAG = "FoodDetailActivity";
    private FoodAdapter adapter;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

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

        ivFood = findViewById(R.id.ivFood);
        tvDescription = findViewById(R.id.tvDescription);
        tvDiscount = findViewById(R.id.tvDiscount);
        tvName = findViewById(R.id.tvName);
        tvPrice = findViewById(R.id.tvPrice);
        btnDelete = findViewById(R.id.btnDelete);
        btnEdit = findViewById(R.id.btnEdit);

        databaseReference = FirebaseDatabase.getInstance().getReference("food");
        firebaseDatabase = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        try {
            key = intent.getStringExtra("key");
            description = intent.getStringExtra("description");
            discount = intent.getStringExtra("discount");
            image = intent.getStringExtra("image");
            menuID = intent.getStringExtra("menuID");
            name = intent.getStringExtra("name");
            price = intent.getStringExtra("price");

            Log.e(TAG, "price: " + price + " name: " + name + " image: " + image + " description: " + description);

            Glide.with(this).load(image).into(ivFood);
            tvDescription.setText(description);
            tvDiscount.setText(discount);
            tvPrice.setText(price);
            tvName.setText(name);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "btnEdit onclick");
                Intent intent = new Intent(FoodDetailActivity.this, FoodEditActivity.class);
                intent.putExtra("key", key);
                intent.putExtra("description", description);
                intent.putExtra("discount", discount);
                intent.putExtra("image", image);
                intent.putExtra("menuID", menuID);
                intent.putExtra("name", name);
                intent.putExtra("price", price);
                startActivity(intent);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Log.e(TAG, String.valueOf(dataSnapshot));
                        }
                        Log.e(TAG, "btnDelete onclick");
                        Log.e(TAG, " " + key);
                        if (key != null) {
                            firebaseDatabase.getReference("food").child(key).removeValue();
                        }
                        Toast.makeText(FoodDetailActivity.this, "Remove success", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, error.getMessage());
                    }
                });
            }
        });

    }

}