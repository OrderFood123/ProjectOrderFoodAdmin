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

public class FoodAddActivity extends AppCompatActivity {

    String description;
    String discount;
    String image = "https://firebasestorage.googleapis.com/v0/b/orderfood-1030f.appspot.com/o/food%2Fbackground.png?alt=media&token=b1a67cb9-969f-4503-a01e-5b5d5b0cf7cc";
    String menuID;
    String name;
    String price;
    int count;
    ImageView ivFood;
    EditText edtDescription;
    EditText edtName;
    EditText edtPrice;
    EditText edtDiscount;
    Button btnDone;

    private String TAG = "FoodAddActivity";

    private DatabaseReference databaseReference;
    private DatabaseReference reference;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_add);

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

        Glide.with(this).load(image).into(ivFood);


        try {
            Intent intent = getIntent();
            count = intent.getIntExtra("count", 0);
            Log.e(TAG, "count: " + count);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "ERROR: " + e.toString());
        }


        databaseReference = FirebaseDatabase.getInstance().getReference();
        reference = databaseReference.child("food");

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image = "https://firebasestorage.googleapis.com/v0/b/orderfood-1030f.appspot.com/o/food%2Fbackground.png?alt=media&token=b1a67cb9-969f-4503-a01e-5b5d5b0cf7cc";
                description = edtDescription.getText().toString();
                name = edtName.getText().toString();
                price = edtPrice.getText().toString();
                discount = edtDiscount.getText().toString();
                menuID = "01";
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, Object> food = new HashMap<>();
                        food.put("Image", image);
                        food.put("Description", description);
                        food.put("Name", name);
                        food.put("Price", price);
                        food.put("Discount", discount);
                        food.put("MenuID", menuID);
                        Log.e(TAG, food.toString());
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Log.e(TAG, ds.toString());
                        }
                        String key = databaseReference.child("food").push().getKey();
                        Log.e(TAG, " " + key);
                        if (key != null) {
                            databaseReference.child("food").child(key).updateChildren(food);
                        }
                        Log.e(TAG, dataSnapshot.getValue().toString());
                        onBackPressed();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, databaseError.getMessage());
                    }
                };
                reference.addListenerForSingleValueEvent(valueEventListener);
            }
        });

    }
}