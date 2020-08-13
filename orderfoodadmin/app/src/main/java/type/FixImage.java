package type;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.orderfoodadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class FixImage extends AppCompatActivity {
    String name;
    String image;
    String key;
    private EditText editText;
    private ImageView imageView;
    private Button button;
    private static final int PICK_IMAGE_REQUEST = 1;

    private String TAG = "MenuFoodEdit";
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_image);
        editText = findViewById(R.id.etNewFileName);
        imageView = findViewById(R.id.newImage);
        button = findViewById(R.id.btnEditAccept);


        Intent intent = getIntent();
        try {
            key = intent.getStringExtra("key");
            image = intent.getStringExtra("image");
            name = intent.getStringExtra("name");


            Log.e(TAG, " name: " + name + " image: " + image);

            Glide.with(this).load(image).into(imageView);

            editText.setText(name);


        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("category");
        firebaseDatabase = FirebaseDatabase.getInstance();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChoose();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String mName = editText.getText().toString();

                final String mMenuID = "01";

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Map<String, Object> category = new HashMap<>();

                        category.put("Image", image);
                        category.put("Name", mName);
                        category.put("MenuID", mMenuID);
                        Log.e(TAG, category.toString());
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Log.e(TAG, ds.toString());
                        }
                        Log.e(TAG, " " + key);
                        if (key != null) {
                            databaseReference.child("category").child(key).updateChildren(category);
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
    private void openFileChoose() {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, PICK_IMAGE_REQUEST);

    }
}