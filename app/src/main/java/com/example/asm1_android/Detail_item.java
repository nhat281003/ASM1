package com.example.asm1_android;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.asm1_android.model.DataMong;
import com.squareup.picasso.Picasso;

public class Detail_item extends AppCompatActivity {
    TextView name, price, dess;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_item);
        imageView= findViewById(R.id.image);
        name= findViewById(R.id.namebundle);
        price= findViewById(R.id.pricebundle);
        dess= findViewById(R.id.dessbundle);
        Bundle bundle = getIntent().getExtras();
        if(bundle ==null){
            return;
        }
        DataMong dataMong = (DataMong) bundle.get("object");
        name.setText(dataMong.getName());
        price.setText(dataMong.getPrice());
        dess.setText(dataMong.getDes());
        Picasso.get()
                .load(dataMong.getImage())
                .into(imageView);


    }
}
