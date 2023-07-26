package com.example.asm1_android;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.asm1_android.adpter.Adapter;
import com.example.asm1_android.adpter.AdapterSpn;
import com.example.asm1_android.api.Apiservide;
import com.example.asm1_android.api.OnItemClickListener;
import com.example.asm1_android.model.DataMong;
import com.example.asm1_android.model.TypeMong;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProduct extends AppCompatActivity {
    private Button btnAdd;
    EditText edtImg, edtname, edtPrice, edtDess, edtType;
    private Adapter adapter;
    private List<DataMong> list;
    public static List<TypeMong> TyppeMongList = new ArrayList<>();
    AdapterSpn adapterSpn;
    Spinner spinner;
    String selectedItem;
    TypeMong typeMong;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);
        initUi();
        getTheloai();
        typeMong = new TypeMong();
        adapter = new Adapter(list, new OnItemClickListener() {
            @Override
            public void onClickItem(DataMong dataMong) {

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = TyppeMongList.get(position).getName();
                typeMong = TyppeMongList.get(position);
                Log.d("AAA", typeMong.toString());
                edtType.setText(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();

            }
        });


    }

    public void getTheloai() {
        Apiservide.apiservice.gettheloai().enqueue(new Callback<List<TypeMong>>() {
            @Override
            public void onResponse(Call<List<TypeMong>> call, Response<List<TypeMong>> response) {
                TyppeMongList = response.body();
                adapterSpn = new AdapterSpn(TyppeMongList);
                adapterSpn.notifyDataSetChanged();
                spinner.setAdapter(adapterSpn);
            }

            @Override
            public void onFailure(Call<List<TypeMong>> call, Throwable t) {

            }

        });

    }


    private void addProduct() {
        String name, img, dess, price;
        name = edtname.getText().toString();
        img = edtImg.getText().toString();
        dess = edtDess.getText().toString();
        price = edtPrice.getText().toString();

        DataMong dataMong = new DataMong();
        dataMong.setName(name);
        dataMong.setImage(img);
        dataMong.setDes(dess);
        dataMong.setPrice(price);
        dataMong.setType(typeMong);

        Log.d("AAA", dataMong.toString());

        Apiservide.apiservice.addData(dataMong).enqueue(new Callback<List<DataMong>>() {
            @Override
            public void onResponse(Call<List<DataMong>> call, Response<List<DataMong>> response) {
                Toast.makeText(AddProduct.this, "Them thanh cong!", Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    list = response.body();

                    if (list != null) {
                        adapter.setmData(list);
                        finish();
                    }

                } else {
                    Toast.makeText(AddProduct.this, "fail response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DataMong>> call, Throwable t) {
                Toast.makeText(AddProduct.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("iii", t.getMessage());
            }
        });


    }

    private void initUi() {
        edtname = findViewById(R.id.addname);
        edtImg = findViewById(R.id.addImg);
        edtPrice = findViewById(R.id.addprice);
        edtDess = findViewById(R.id.addess);
        btnAdd = findViewById(R.id.confirm_button);
        spinner = findViewById(R.id.spn);
        edtType = findViewById(R.id.addType);
    }
}
