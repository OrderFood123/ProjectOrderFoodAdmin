package com.example.orderfoodadmin;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.orderfoodadmin.model.Upload;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class TheLoaiActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button btnChooseFile, btnUpLoad;
    private TextView tvShowUpload;
    private EditText etFileName;
    private ImageView imageTheLoai;
    private ProgressBar mProgress;

    private Uri uriImage;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private String TAG;

    private StorageTask storageTask;

    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_loai);

        btnChooseFile = findViewById(R.id.btnChooseFile);
        btnUpLoad = findViewById(R.id.btnUpLoad);
        etFileName = findViewById(R.id.etFileName);
        tvShowUpload = findViewById(R.id.tvShowUpload);
        imageTheLoai = findViewById(R.id.imageTheLoai);
        mProgress = findViewById(R.id.progress);

        storageReference = FirebaseStorage.getInstance().getReference("/food/thucdon/" + key);
        databaseReference = FirebaseDatabase.getInstance().getReference("category");
        btnChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChoose();
            }
        });

        btnUpLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upLoadFile();
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
            uriImage = data.getData();

            Picasso.get().load(uriImage).into(imageTheLoai); }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cR.getType(uri));

    }

    private void upLoadFile() {
        if (uriImage != null) {
            storageReference.putFile(uriImage).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
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
                        Log.e(TAG, "then: " + downloadUri.toString());


                        Upload upload = new Upload(etFileName.getText().toString().trim(),
                                downloadUri.toString());
                        String key = databaseReference.child("/food/thucdon").push().getKey();
                        databaseReference.child(key).setValue(upload);

                        Toast.makeText(TheLoaiActivity.this, "Tải thành công!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(TheLoaiActivity.this, "Tải không thành công! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

}