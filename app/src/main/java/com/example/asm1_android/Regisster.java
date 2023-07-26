package com.example.asm1_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm1_android.api.Apiservide;
import com.example.asm1_android.model.User_login;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Regisster extends AppCompatActivity {
    Button btnRegi;
    EditText edtEmail;
    TextInputEditText edtpass, repass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regisster);
        btnRegi = findViewById(R.id.confirm_button);
        edtEmail= findViewById(R.id.edtNameregi);
        edtpass= findViewById(R.id.edtpassregi);
        repass= findViewById(R.id.edtRepass);
        btnRegi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(edtpass.getText().toString().equals(repass.getText().toString())){
                   callRegisster();
               }else{
                   Toast.makeText(Regisster.this, "Vui lòng nhập đúng mật khẩu" , Toast.LENGTH_SHORT).show();
               }

            }
        });
    }

    private void callRegisster(){
        String email, pass;

        email = edtEmail.getText().toString();
        pass = edtpass.getText().toString();
        User_login user = new User_login();
        user.setEmail(email);
        user.setPassword(pass);

        Apiservide.apiservice.register(user).enqueue(new Callback<User_login>() {
            @Override
            public void onResponse(Call<User_login> call, Response<User_login> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Regisster.this, "Đăng ký thành công" , Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    if(response.code() == 500){
                        Toast.makeText(Regisster.this, "Đăng ký xịt", Toast.LENGTH_SHORT).show();
                    }else if(response.code() == 404){
                        Toast.makeText(Regisster.this, "sai", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(Regisster.this, "kut roi", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<User_login> call, Throwable t) {
                Toast.makeText(Regisster.this, "Error" , Toast.LENGTH_SHORT).show();
            }


        });



    }
}