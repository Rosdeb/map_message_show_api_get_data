package com.example.coffee.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffee.Models.Message_list;
import com.example.coffee.R;

import java.util.List;

public class Message_adapter extends RecyclerView.Adapter<Message_adapter.Viewholder> {
    Context context ;
    List<Message_list> messageLists;

    public Message_adapter(Context context,List<Message_list> messageLists) {
        this.context=context;
        this.messageLists = messageLists;
    }

    @NonNull
    @Override
    public Message_adapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_items,parent, false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Message_adapter.Viewholder holder, int position) {
         Message_list message =messageLists.get(position);
      //  holder.senderTextView.setText(message.getSender());
        holder.messageTextView.setText(message.getContent());
        holder.timestampTextView.setText(message.getTimestamp());

    }

    @Override
    public int getItemCount() {
        return messageLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView senderTextView, messageTextView, timestampTextView;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            //senderTextView =itemView.findViewById(R.id.sender);
            messageTextView =itemView.findViewById(R.id.message);
            timestampTextView =itemView.findViewById(R.id.timestamp);


        }
    }
}
