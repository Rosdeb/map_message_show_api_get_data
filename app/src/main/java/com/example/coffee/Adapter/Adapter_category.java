package com.example.coffee.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coffee.Models.Category;
import com.example.coffee.R;

import java.util.List;

public class Adapter_category extends RecyclerView.Adapter<Adapter_category.Viewholder> {
    List<Category> list;
    Context context;

    public Adapter_category(List<Category> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter_category.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.category,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_category.Viewholder holder, int position) {
        Category category = list.get(position);
        holder.catetory.setText(category.getName());
        Glide.with(context).load(category.getImage()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
     public void addProducts(List<Category> categoryAdapters){
        list.addAll(categoryAdapters);
        notifyDataSetChanged();
     }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView catetory;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.categoryImage);
            catetory=itemView.findViewById(R.id.categoryName);
        }
    }
}
