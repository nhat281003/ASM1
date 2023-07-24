package com.example.asm1_android;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.asm1_android.api.Apiservide;
import com.example.asm1_android.model.DataMong;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product extends AppCompatActivity {
    RecyclerView recyclerView;
    public static  List<DataMong> dataMongList = new ArrayList<>();
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product);
        recyclerView = findViewById(R.id.recyview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this);
        adapter.interCall(this::load);
        callApi();

    }

    public void callApi() {
        Apiservide.apiservice.getdata().enqueue(new Callback<List<DataMong>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<List<DataMong>> call, Response<List<DataMong>> response) {
                dataMongList = response.body();
                adapter.setmData(dataMongList);
                adapter.interCall(Product.this::load);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<DataMong>> call, Throwable t) {

            }

        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        adapter = new Adapter(this);
        callApi();
        adapter.notifyDataSetChanged();
    }


    public void load() {
        callApi();
    }

}
