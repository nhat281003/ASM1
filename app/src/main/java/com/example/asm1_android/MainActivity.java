package com.example.asm1_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.asm1_android.api.Apiservide;
import com.example.asm1_android.model.User_login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static List<User_login> user_login = new ArrayList<>();
    TextView txtRegister, txttoken;
    Button btnLogin;
  public static EditText edtEmail;
    TextInputEditText edtpass;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getUser();
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            System.out.println("Fetching FCM registration token failed");;
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();
                        System.out.println(token);
                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();

                    }

                });



        txtRegister= findViewById(R.id.txtregi);
        btnLogin= findViewById(R.id.confirm_button);
        edtEmail= findViewById(R.id.edtnameLogin);
        edtpass= findViewById(R.id.edtpassLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtEmail.getText().toString() == "" || edtpass.getText().toString().length() <3){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập email, mk" , Toast.LENGTH_SHORT).show();
                }else{
                    callLogin();
                }
            }
        });
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Regisster.class);
                startActivity(intent);
            }
        });


    }

    private void callLogin(){
        String email, pass;
        email = edtEmail.getText().toString();
        pass = edtpass.getText().toString();

        User_login user = new User_login();
        user.setEmail(email);
        user.setPassword(pass);

        Apiservide.apiservice.login(user).enqueue(new Callback<User_login>() {
            @Override
            public void onResponse(Call<User_login> call, Response<User_login> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Đăng nhập thành công" , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Product.class);
                    startActivity(intent);
                } else {
                    if(response.code() == 500){
                        Toast.makeText(MainActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }else if(response.code() == 401){
                        Toast.makeText(MainActivity.this, "Sai tài khoản/ Mật khẩu", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MainActivity.this, "kut roi", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<User_login> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error" , Toast.LENGTH_SHORT).show();

            }

        });



    }
    public void getUser() {
        Apiservide.apiservice.getUser().enqueue(new Callback<List<User_login>>() {
            @Override
            public void onResponse(Call<List<User_login>> call, Response<List<User_login>> response) {
                user_login = response.body();
            }

            @Override
            public void onFailure(Call<List<User_login>> call, Throwable t) {

            }

        });

    }
}