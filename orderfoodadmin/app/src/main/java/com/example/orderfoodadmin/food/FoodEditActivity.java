package com.example.orderfoodadmin.food;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.orderfoodadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class FoodEditActivity extends AppCompatActivity {

    String description;
    String discount;
    String image = "https://firebasestorage.googleapis.com/v0/b/orderfood-1030f.appspot.com/o/food%2Fbackground.png?alt=media&token=b1a67cb9-969f-4503-a01e-5b5d5b0cf7cc";
    String menuID;
    String name;
    String price;
    String key;

    ImageView ivFood;
    EditText edtDescription;
    EditText edtName;
    EditText edtPrice;
    EditText edtDiscount;
    Button btnDone;

    private String TAG = "FoodEditActivity";
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_edit);

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
        edtDescription = findViewById(R.id.edtDescription);
        edtDiscount = findViewById(R.id.edtDiscount);
        edtName = findViewById(R.id.edtName);
        edtPrice = findViewById(R.id.edtPrice);
        btnDone = findViewById(R.id.btnDone);

        Intent intent = getIntent();
        try {
            key = intent.getStringExtra("key");
            description = intent.getStringExtra("description");
            discount = intent.getStringExtra("discount");
            image = intent.getStringExtra("image");
            menuID = intent.getStringExtra("menuID");
            name = intent.getStringExtra("name");
            price = intent.getStringExtra("price");

            Log.e(TAG, "key: " + key + " description: " + description + " discount: " + discount + " price: " + price + " name: " + name + " image: " + image);

            Glide.with(this).load(image).into(ivFood);
            edtDescription.setText(description);
            edtDiscount.setText(discount);
            edtName.setText(name);
            edtPrice.setText(price);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mDescription = edtDescription.getText().toString();
                final String mName = edtName.getText().toString();
                final String mPrice = edtPrice.getText().toString();
                final String mDiscount = edtDiscount.getText().toString();
                final String mMenuID = "01";

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Map<String, Object> food = new HashMap<>();
                        food.put("Image", image);
                        food.put("Description", mDescription);
                        food.put("Name", mName);
                        food.put("Price", mPrice);
                        food.put("Discount", mDiscount);
                        food.put("MenuID", mMenuID);
                        Log.e(TAG, food.toString());
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Log.e(TAG, ds.toString());
                        }
                        Log.e(TAG, " " + key);
                        if (key != null) {
                            databaseReference.child("food").child(key).updateChildren(food);
                        }
                        onBackPressed();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, error.getMessage());
                    }
                };
                databaseReference.addListenerForSingleValueEvent(valueEventListener);

            }
        });
    }
}