package com.example.asm1_android.api;

import com.example.asm1_android.model.DataMong;

import com.example.asm1_android.model.TypeMong;
import com.example.asm1_android.model.User_login;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Apiservide {
    Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd HH:mm:ss").create();

    Apiservide apiservice = new Retrofit.Builder()
            .baseUrl("http://192.168.1.22:3000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(Apiservide.class);

    @GET("danhsach/danhsach")
    Call<List<DataMong>> getdata();

    @GET("danhsach/theloai")
    Call<List<TypeMong>> gettheloai();

    @POST("danhsach/add")
    Call<List<DataMong>> addData( @Body DataMong dataMong);

    @PUT("danhsach/edit/{idsp}")
    Call<Void> editData(@Path("idsp") String id,@Body DataMong dataMong);

    @DELETE("danhsach/delete/{idsp}")
    Call<List<DataMong>> deleteData(@Path("idsp") String id);

    @POST("danhsach/login")
    Call<User_login> login (@Body User_login user_login);

    @POST("danhsach/register")
    Call<User_login> register (@Body User_login user_login);



}
