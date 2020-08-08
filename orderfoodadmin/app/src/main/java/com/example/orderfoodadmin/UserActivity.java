package com.example.orderfoodadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.orderfoodadmin.Adapter.UserAdapter;
import com.example.orderfoodadmin.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class UserActivity extends AppCompatActivity {
    private ArrayList<User> list = new ArrayList<>();
    private RecyclerView recyclerView;
    UserAdapter adapter;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(UserActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("user");

//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Iterable<DataSnapshot> dataSnapshotIterable = snapshot.getChildren();
//                Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
//                while (iterator.hasNext()) {
//                    DataSnapshot next = iterator.next();
//                    User user = next.getValue(User.class);
//                    Log.d("Data", "Value is: " + user.getName());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
//                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();
////                    for (String key : dataMap.keySet()) {
////                        Object data = savedInstanceState.get(key);
////                        try {
////                            HashMap<String, Object> userData = (HashMap<String, Object>) data;
////                            User user = new User((String) userData.get("phonNumber"), (String) userData.get("name"));
////                            list.add(user);
////                        } catch (ClassCastException cce){
////                            try {
////                                String mString = String.valueOf(dataMap.get(key));
////
////                            }catch (ClassCastException cce2){
////
////                            }
////                        }
////                    }

                    Iterable<DataSnapshot> dataSnapshotIterable = snapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = iterator.next();
                        User user = next.getValue(User.class);
                        Log.d("Data ", "Value is: " + user.getName());
//                        Log.d("Data ", "Value is: " + user.getPassword());

//                        Log.d("Data ", "Value is: " + user.getPassword());
                        list.add(user);
//                        Log.d("Data ", "Value is: " + list.size());
                        adapter = new UserAdapter(UserActivity.this, list);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}