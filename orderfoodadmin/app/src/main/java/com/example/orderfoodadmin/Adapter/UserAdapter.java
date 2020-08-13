package com.example.orderfoodadmin.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.orderfoodadmin.model.User;
import com.example.orderfoodadmin.R;

import java.util.List;
public class UserAdapter extends ArrayAdapter<User> {
    private Activity context;
    private List<User> userModelList;
    public UserAdapter(Activity context, List<User> userModelList)
    {
        super(context, R.layout.item_user, userModelList);
        this.context = context;
        this.userModelList = userModelList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View ListItem = inflater.inflate(R.layout.item_user,null,true);

        TextView name_tv = ListItem.findViewById(R.id.tv_user_name);
        TextView password_tv = ListItem.findViewById(R.id.tv_user_password);
        User userModel = userModelList.get(position);

        name_tv.setText("Name: "+userModel.getName());
        password_tv.setText("Password: "+userModel.getPassword());
        return ListItem;
    }
}