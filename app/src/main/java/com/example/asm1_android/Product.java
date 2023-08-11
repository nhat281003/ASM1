package com.example.asm1_android;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.asm1_android.adpter.Adapter;
import com.example.asm1_android.api.Apiservide;
import com.example.asm1_android.api.OnItemClickListener;
import com.example.asm1_android.model.DataMong;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product extends AppCompatActivity {
    RecyclerView recyclerView;
    public static  List<DataMong> dataMongList = new ArrayList<>();
    Adapter adapter;
    ImageView imgAdd;
    TextView text;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product);
        imgAdd= findViewById(R.id.imgAdd);
        text = findViewById(R.id.test);
        recyclerView = findViewById(R.id.recyview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        text.setOnClickListener( v->{
            Intent intent = new Intent(Product.this, Chat.class);
            startActivity(intent);

        });

        adapter= new Adapter(dataMongList, new OnItemClickListener() {
            @Override
            public void onClickItem(DataMong dataMong) {
                OnclickItemc(dataMong);
            }
        });
        adapter.interCall(this::load);

        callApi();

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Product.this, AddProduct.class);
                startActivity(intent);
            }
        });

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
        callApi();
        adapter = new Adapter(dataMongList, new OnItemClickListener() {
            @Override
            public void onClickItem(DataMong dataMong) {
                OnclickItemc(dataMong);
            }
        });
        adapter.notifyDataSetChanged();
    }

    private  void OnclickItemc(DataMong dataMong){
        Intent intent = new Intent(this, Detail_item.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object", dataMong);
        intent.putExtras(bundle);
        startActivity(intent);

    }


    public void load() {
        callApi();
    }

}
