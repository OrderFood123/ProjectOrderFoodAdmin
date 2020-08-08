package com.example.orderfoodadmin.food;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.orderfoodadmin.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class FoodAddActivity extends AppCompatActivity {

    private final int PICK_IMAGE_REQUEST = 71;
    String description;
    String discount;
    //    String image = "https://firebasestorage.googleapis.com/v0/b/orderfood-1030f.appspot.com/o/food%2Fbackground.png?alt=media&token=b1a67cb9-969f-4503-a01e-5b5d5b0cf7cc";
    String image;
    String menuID;
    String name;
    String price;
    String fileName;
    String key;
    int count;
    ImageView ivFood;
    EditText edtDescription;
    EditText edtName;
    EditText edtPrice;
    EditText edtDiscount;
    EditText edtFileName;
    Button btnDone;
    private Uri filePath;
    private AlertDialog dialog;

    private String TAG = "FoodAddActivity";

    private StorageReference storageReference;
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
        edtFileName = findViewById(R.id.edtFileName);
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
        storageReference = FirebaseStorage.getInstance().getReference("/food/thucdon/" + key);


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
                if (edtFileName.length() == 0) {
                    edtFileName.setError("Thông tin bắt buộc");
                    dialog.dismiss();
                } else {
                    fileName = edtFileName.getText().toString();
                    if (edtName.length() == 0) {
                        edtName.setError("Thông tin bắt buộc");
                        dialog.dismiss();
                    } else {
                        name = edtName.getText().toString();
                        if (edtPrice.length() == 0) {
                            edtPrice.setError("Thông tin bắt buộc");
                            dialog.dismiss();
                        } else {
                            price = edtPrice.getText().toString();
                            if (edtDiscount.length() == 0) {
                                edtDiscount.setError("Thông tin bắt buộc");
                                dialog.dismiss();
                            } else {
                                discount = edtDiscount.getText().toString();
                                if (edtDescription.length() == 0) {
                                    edtDescription.setError("Thông tin bắt buộc");
                                    dialog.dismiss();
                                } else {
                                    description = edtDescription.getText().toString();
                                    menuID = "01";
                                    upLoadImage(fileName, description, name, price, discount, menuID);
                                }
                            }
                        }
                    }
                }
            }
        });

        ivFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "chose image");
                openFileChoose();
            }
        });

    }

    private void openFileChoose() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            Picasso.get().load(filePath).into(ivFood);
        }
    }

    public void upLoadImage(final String imageFile, final String description, final String name, final String price, final String discount, final String menuID) {
        Log.e(TAG, filePath.toString());
        if (filePath != null) {
            storageReference.putFile(filePath).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return storageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        Log.e(TAG, "Thành công: " + downloadUri.toString());
                        image = downloadUri.toString();
                        uploadData(image, description, name, price, discount, menuID);
//                        Upload upload = new Upload("test_001", downloadUri.toString());
//                        key = databaseReference.child("/food/thucdon").push().getKey();
//                        databaseReference.child(key).setValue(upload);
//                        Log.e(TAG, "Thành công" + String.valueOf(databaseReference.child(key).setValue(upload)));
                        Toast.makeText(FoodAddActivity.this, "Tải thành công!", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(FoodAddActivity.this, "Tải không thành công! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void uploadData(final String image, final String description, final String name, final String price, final String discount, final String menuID) {
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
                key = databaseReference.child("food").push().getKey();
                Log.e(TAG, " " + key);
                if (key != null) {
                    databaseReference.child("food").child(key).updateChildren(food);
                }
                Log.e(TAG, dataSnapshot.getValue().toString());
                dialog.dismiss();
                onBackPressed();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        };
        reference.addListenerForSingleValueEvent(valueEventListener);
    }

    public void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đang cập nhật!");
        builder.setMessage("Vui lòng chờ!");
        dialog = builder.create();
        dialog.show();
    }

}