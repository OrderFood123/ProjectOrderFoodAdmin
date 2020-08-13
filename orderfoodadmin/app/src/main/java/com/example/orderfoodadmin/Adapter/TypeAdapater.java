package com.example.orderfoodadmin.Adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.MenuItemHoverListener;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfoodadmin.R;
import com.example.orderfoodadmin.model.Category;
import com.example.orderfoodadmin.model.Upload;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TypeAdapater  extends RecyclerView.Adapter<TypeAdapater.TypeHolder> {
   Context context;
   List<Category> mupload;
   OnItemClickListener mListener;
   public TypeAdapater(Context context, List<Category> mupload) {
      this.context = context;
      this.mupload = mupload;
   }

   @NonNull
   @Override
   public TypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(context).inflate(R.layout.item_type,parent,false);
      return new TypeHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull TypeHolder holder, int position) {
         Category currentUpload = mupload.get(position);
         holder.mTV.setText(currentUpload.getName());
      Picasso.get ().load(currentUpload.getImage()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(holder.mIM);
   }

   @Override
   public int getItemCount() {
      return mupload.size();
   }

   public class TypeHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
      TextView mTV;
      ImageView mIM;
      public TypeHolder(@NonNull View itemview){
         super(itemview);
         mIM = itemview.findViewById(R.id.itemImage);
         mTV = itemview.findViewById(R.id.itemName);
         itemview.setOnClickListener(this);
         itemview.setOnCreateContextMenuListener(this);
      }

      @Override
      public void onClick(View v) {
         if(mListener != null){
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
               mListener.onItemClick(position);
            }
         }
      }

      @Override
      public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
         menu.setHeaderTitle("Select Action");
         MenuItem fix = menu.add(Menu.NONE,1,1,"Fix image");
         MenuItem delete = menu.add(Menu.NONE,2,2,"Delete image");

         fix.setOnMenuItemClickListener(this);
         delete.setOnMenuItemClickListener(this);
      }

      @Override
      public boolean onMenuItemClick(MenuItem item) {
         int position = getAdapterPosition();
         if(position != RecyclerView.NO_POSITION){
            switch (item.getItemId()){
               case 1:mListener.onFix(position);
               return true;
               case 2:mListener.onDelete(position);
                  return true;
            }
         }
         return false;
      }
   }
   public interface  OnItemClickListener{
      void onItemClick(int position);
      void onFix(int position);
      void onDelete(int position);
   }
   public void setOnClickListener(OnItemClickListener listener){
      mListener = listener;
   }
}
