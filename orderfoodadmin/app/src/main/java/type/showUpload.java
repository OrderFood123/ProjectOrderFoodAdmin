package type;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.orderfoodadmin.Adapter.TypeAdapater;
import com.example.orderfoodadmin.R;
import com.example.orderfoodadmin.model.Category;
import com.example.orderfoodadmin.model.Upload;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class showUpload extends AppCompatActivity implements TypeAdapater.OnItemClickListener {
        RecyclerView recyclerView;
        TypeAdapater typeAdapater;
        DatabaseReference databaseReference;
        List<Category> mupload;
        ImageView icon;
private FirebaseStorage firebaseStorage;
private ValueEventListener  valueEventListener;
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_upload);
        icon = findViewById(R.id.icon);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        firebaseStorage  = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("category");
        mupload = new ArrayList<>();
        typeAdapater = new TypeAdapater(showUpload.this, mupload);
        recyclerView.setAdapter(typeAdapater);
        typeAdapater.setOnClickListener(showUpload.this);
        icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(showUpload.this, TheLoaiActivity.class);
                        startActivity(intent);
                }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
@Override
public void onDataChange(@NonNull DataSnapshot snapshot) {
        mupload.clear();
        for (DataSnapshot snapshot1: snapshot.getChildren()){
        Category category = snapshot1.getValue(Category.class);
        category.setKey(snapshot1.getKey());
        mupload.add(category);
        }
        typeAdapater.notifyDataSetChanged();
        }

@Override
public void onCancelled(@NonNull DatabaseError error) {
        Toast.makeText(showUpload.this,error.getMessage(),Toast.LENGTH_SHORT).show();
        }
        });
        }

@Override
public void onItemClick(int position) {
        Toast.makeText(this,"hold to edit food"+position,Toast.LENGTH_SHORT).show();
        }

@Override
public void onFix(int position) {
        Intent intent1 = new Intent(showUpload.this,FixImage.class);
        startActivity(intent1);
        Toast.makeText(this,"Fix information of food"+position,Toast.LENGTH_SHORT).show();
        }

@Override
public void onDelete(int position) {

        Category selectedItem = mupload.get(position);
final String selectedkey = selectedItem.getKey();
        StorageReference imageRef = firebaseStorage.getReferenceFromUrl(selectedItem.getImage());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
@Override
public void onSuccess(Void aVoid) {
        databaseReference.child(selectedkey).removeValue();
        Toast.makeText(showUpload.this, "Food delete", Toast.LENGTH_SHORT).show();
        }
        });
        }

@Override
protected void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(valueEventListener);
    }
        }