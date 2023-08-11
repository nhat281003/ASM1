package com.example.asm1_android;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm1_android.adpter.Adapter;
import com.example.asm1_android.adpter.AdapterChat;
import com.example.asm1_android.api.Apiservide;
import com.example.asm1_android.api.OnItemClickListener;
import com.example.asm1_android.model.DataMong;
import com.example.asm1_android.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chat extends AppCompatActivity {
    AdapterChat adapterChat;
    public static  List<Message> messageList;
    EditText editText;
    ImageView  button;
    String data ;
    RecyclerView recyclerView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        recyclerView= findViewById(R.id.recyto);
        editText= findViewById(R.id.edtmess);
        button= findViewById(R.id.send);
        SocketManager.getInstance().connect();
        messageList= new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterChat= new AdapterChat(messageList);
        getMess();
        SocketManager.getInstance().on("Notifi", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                data = args[0].toString();
                System.out.println(data);
                try {
                    Thread.sleep(1000);
                    addMess();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread() {
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                addMess();
                                editText.setText("");
                                getMess();
                                adapterChat.notifyDataSetChanged();
                            }
                        });
                    }
                };
                thread.start();
            }
        });

    }


    public void getMess(){
        Apiservide.apiservice.getMessage().enqueue(new Callback<List<Message>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                messageList= response.body();
                adapterChat.setmData(messageList);
                recyclerView.setAdapter(adapterChat);
                adapterChat.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }


    public void addMess() {
        String mess;
        if(data != null){
            mess = data;
        }else{
            mess = editText.getText().toString();
        }
//        SocketManager.getInstance().emit("Send1", mess);
        Message message = new Message();
        message.setMessage(mess);
        Apiservide.apiservice.addMess(message).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                Toast.makeText(Chat.this, "Them thanh cong!", Toast.LENGTH_SHORT).show();
                messageList = response.body();
                adapterChat.setmData(messageList);
                recyclerView.setAdapter(adapterChat);
                adapterChat.notifyDataSetChanged();

            }
            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        getMess();
        adapterChat = new AdapterChat(messageList);
        recyclerView.setAdapter(adapterChat);
        adapterChat.notifyDataSetChanged();
    }

}
