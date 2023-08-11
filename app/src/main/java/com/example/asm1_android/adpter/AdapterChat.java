package com.example.asm1_android.adpter;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm1_android.R;
import com.example.asm1_android.model.DataMong;
import com.example.asm1_android.model.Message;

import java.util.List;


public class AdapterChat extends RecyclerView.Adapter<AdapterChat.ChatViewHolder>{
    List<Message> listmess;

    public AdapterChat(List<Message> listmess) {
        this.listmess = listmess;
        notifyDataSetChanged();
    }

    public void setmData(List<Message> listmess){
        this.listmess = listmess;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return  new ChatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Message message = listmess.get(position);
        holder.mess.setText(message.getMessage());
    }

    @Override
    public int getItemCount() {
        return listmess.size();
    }

    public  static class ChatViewHolder extends  RecyclerView.ViewHolder{
        TextView mess;
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            mess = itemView.findViewById(R.id.txtmess);
        }
    }
}
