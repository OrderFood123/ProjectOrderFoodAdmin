package com.example.orderfoodadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.orderfoodadmin.Adapter.UserAdapter;
import com.example.orderfoodadmin.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;



public class UserActivity extends AppCompatActivity {
    DatabaseReference databaseuser;
    EditText edt_phone, edt_name, edt_password;
    Button btn_add_user;
    ListView userListView;
    List<User> userlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        edt_phone = findViewById(R.id.edt_user_phone);
        edt_name = findViewById(R.id.edt_user_name);
        edt_password = findViewById(R.id.edt_user_password);
        btn_add_user = findViewById(R.id.btn_user_add);
        userListView = findViewById(R.id.listview_user);
        userlist = new ArrayList<>();
        databaseuser = FirebaseDatabase.getInstance().getReference("user");
        btn_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adduser(edt_phone.getText().toString().trim(),
                        edt_name.getText().toString().trim(),
                        edt_password.getText().toString().trim());
            }
        });
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final User userModel = userlist.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
                LinearLayout layout = new LinearLayout(UserActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                final EditText phone_et = new EditText(UserActivity.this);
                final EditText name_et = new EditText(UserActivity.this);
                final EditText password_et = new EditText(UserActivity.this);
                layout.addView(phone_et);
                layout.addView(name_et);
                layout.addView(password_et);
                phone_et.setHint("Phone");
                name_et.setHint("Name");
                password_et.setHint("Password");
                builder.setTitle("Update Information")
                        .setMessage("Write information to Update")
                        .setView(layout)
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user").child(userModel.getId());
                                User userModel1 = new User(userModel.getId(),
                                        phone_et.getText().toString().trim(),
                                        name_et.getText().toString().trim(),
                                        password_et.getText().toString().trim());


                                reference.setValue(userModel1);

                            }
                        }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user").child(userModel.getId());

                        reference.removeValue();
                    }
                }).show();
            }
        });
    }
    private void adduser(String Phone, String Name, String Password)
    {
        String id = databaseuser.push().getKey();
        User userModel = new User(id,Phone,Name,Password);
        databaseuser.child(id).setValue(userModel);
//        databaseuser.child(Phone).setValue(id);  // xung dot userModel trong onDataChange
    }
    @Override
    protected void onStart() {
        super.onStart();
        databaseuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userlist.clear();  // everytime when data updates in your firebase database it creates the list with updated items
                // to avoid duplicate fields we clear the list everytime
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot usersnapshot : dataSnapshot.getChildren())
                    {
                        User userModel= usersnapshot.getValue(User.class);
                        userlist.add(userModel);
                    }
                    UserAdapter adaptor = new UserAdapter(UserActivity.this, userlist);
                    userListView.setAdapter(adaptor);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}