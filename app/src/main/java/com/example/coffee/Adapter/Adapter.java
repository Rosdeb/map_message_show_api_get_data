package com.example.coffee.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.coffee.MainActivity2;
import com.example.coffee.Models.Models_1;
import com.example.coffee.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    List<Models_1> itemlist;

    public Adapter(Context context, List<Models_1> itemlist) {
        this.context = context;
        this.itemlist = itemlist;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycleview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {

        Models_1 models1 = itemlist.get(position);
        holder.price.setText("$"+models1.getPrice());
        holder.textView.setText(models1.getTitle());
        Glide.with(context)
                .load(models1.getImage())
                .override(200, 200)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)// Resize to appropriate dimensions// Cache efficiently
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;
                intent = new Intent(context, MainActivity2.class);
                intent.putExtra("name",models1.getTitle());
                intent.putExtra("price",models1.getPrice());
                intent.putExtra("image",models1.getImage());
                intent.putExtra("description",models1.getDescription());

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }
    public  void addProducts(List<Models_1> newProductes){
        itemlist.addAll(newProductes);
        notifyItemRangeInserted(itemlist.size() - newProductes.size(), newProductes.size());
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        TextView price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.itemImageView);
            textView = itemView.findViewById(R.id.textviewid);
            price = itemView.findViewById(R.id.price);

        }
    }
}
