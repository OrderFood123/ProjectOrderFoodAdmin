package com.example.orderfoodadmin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orderfoodadmin.model.Food;
import com.example.orderfoodadmin.R;
import com.example.orderfoodadmin.food.FoodDetailActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Food> arrayList;
    private Context context;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


    public FoodAdapter(
            ArrayList<Food> arrayList,
            Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.items_food, parent, false);
        FoodViewHolder foodViewHolder = new FoodViewHolder(view);

        return foodViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FoodViewHolder holder, int position) {
        Food food = arrayList.get(position);
        final String key = food.getKey();
        final String description = food.getDescription();
        final String discount = food.getDiscount();
        final String image = food.getImage();
        final String menuID = food.getMenuID();
        final String name = food.getName();
        final String price = food.getPrice();

        Log.e(TAG, "price: " + price + " name: " + name + " image: " + image);
        if (arrayList.get(position).getImage() != null) {
            Glide.with(context).load(food.getImage()).into(holder.ivFood);
        } else {
            holder.ivFood.setImageResource(R.drawable.background);
        }
        holder.tvPrice.setText(food.getPrice());
        holder.tvName.setText(food.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "itemView onclick: ");
                Intent intent = new Intent(context.getApplicationContext(), FoodDetailActivity.class);
                intent.putExtra("key", key);
                intent.putExtra("description", description);
                intent.putExtra("discount", discount);
                intent.putExtra("image", image);
                intent.putExtra("menuID", menuID);
                intent.putExtra("name", name);
                intent.putExtra("price", price);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivFood;
        private TextView tvName;
        private TextView tvPrice;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            ivFood = itemView.findViewById(R.id.ivFood);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
