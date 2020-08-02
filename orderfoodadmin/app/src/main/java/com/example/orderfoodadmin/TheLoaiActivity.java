package com.example.orderfoodadmin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class TheLoaiActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private  Button btnChooseFile, btnUpLoad;
    private TextView tvShowUpload;
    private EditText etFileName;
    private ImageView imageTheLoai;
    private ProgressBar progress;

    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_loai);

//        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        final DatabaseReference theloai = FirebaseDatabase.getInstance().getReference().child("category");

        btnChooseFile = findViewById(R.id.btnChooseFile);
        btnUpLoad = findViewById(R.id.btnUpLoad);
        etFileName = findViewById(R.id.etFileName);
        tvShowUpload = findViewById(R.id.tvShowUpload);
        imageTheLoai = findViewById(R.id.imageTheLoai);
        progress = findViewById(R.id.progress);

        btnChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChoose();
            }
        });

        btnUpLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tvShowUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
            && data != null && data.getData() != null) {
            uri = data.getData();

            Picasso.get().load(uri).into(imageTheLoai);
//            Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageTheLoai);
        }
    }
}