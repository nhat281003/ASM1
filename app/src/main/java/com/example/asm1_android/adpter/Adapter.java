package com.example.asm1_android.adpter;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm1_android.R;
import com.example.asm1_android.api.Apiservide;
import com.example.asm1_android.api.InterCallAPI;
import com.example.asm1_android.api.OnItemClickListener;
import com.example.asm1_android.model.DataMong;
import com.example.asm1_android.model.TypeMong;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    private List<DataMong> mData;
    static AdapterSpn adapterSpn;
    public static  List<TypeMong> TyppeMongList = new ArrayList<>();
    private OnItemClickListener mListener;
    String selectedItem;
    TypeMong typeMong;


    public void interCall(InterCallAPI interCallAPI) {
        this.interCallAPI = interCallAPI;
    }
    public Adapter(List<DataMong> data, OnItemClickListener mListener) {
        this.mData = data;
        this.mListener= mListener;
    }
    InterCallAPI interCallAPI;

    @SuppressLint("NotifyDataSetChanged")
    public void setmData(List<DataMong> mData){
        this.mData = mData;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return  new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DataMong dataMong = mData.get(position);
        holder.binData(dataMong);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickItem(dataMong);
            }
        });

        holder.imgEdit.setOnClickListener(v -> {
            Dialog dialog = new Dialog(v.getContext());
            dialog.setContentView(R.layout.dialog_update);
            Spinner spinner = dialog.findViewById(R.id.spnDialog);
            EditText edname = dialog.findViewById(R.id.dialog_edtName);
            EditText edprice = dialog.findViewById(R.id.dialog_edtPrice);
            EditText edimage = dialog.findViewById(R.id.dialog_edtImage);
            EditText edDes = dialog.findViewById(R.id.dialog_edtDesscrip);
            EditText edType = dialog.findViewById(R.id.dialog_edtType);
            Button btnUp = dialog.findViewById(R.id.confirm_button);
            edname.setText(dataMong.getName());
            edprice.setText(dataMong.getPrice());
            edimage.setText(dataMong.getImage());
            edDes.setText(dataMong.getDes());
            edType.setText(dataMong.getType().getName());
            Apiservide.apiservice.gettheloai().enqueue(new Callback<List<TypeMong>>() {
                @Override
                public void onResponse(Call<List<TypeMong>> call, Response<List<TypeMong>> response) {
                    TyppeMongList= response.body();
                    adapterSpn= new AdapterSpn(TyppeMongList);
                    adapterSpn.notifyDataSetChanged();
                    spinner.setAdapter(adapterSpn);
                }
                @Override
                public void onFailure(Call<List<TypeMong>> call, Throwable t) {

                }

            });
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedItem = TyppeMongList.get(position).getName();
                    typeMong = TyppeMongList.get(position);
                    if(TyppeMongList.get(position).getName() == null || Objects.equals(TyppeMongList.get(position).getName(), "")){
                        edType.setText(selectedItem);
                    }else {
                        edType.setText(dataMong.getType().getName());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



            btnUp.setOnClickListener(v1 -> {
                DataMong dataMong1 = new DataMong();
                dataMong1.setName(edname.getText().toString().trim());
                dataMong1.setPrice(edprice.getText().toString().trim());
                dataMong1.setImage(edimage.getText().toString().trim());
                dataMong1.setDes(edDes.getText().toString().trim());
                dataMong1.setType(typeMong);

                Apiservide.apiservice.editData(dataMong.get_id(), dataMong1).enqueue(new Callback<Void>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(v.getContext(), "Update thanh cong!", Toast.LENGTH_SHORT).show();
                            interCallAPI.load();
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

            });

            dialog.show();
        });

        holder.imgDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Xóa sản phẩm");
            builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này ?");
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Apiservide.apiservice.deleteData(dataMong.get_id()).enqueue(new Callback<List<DataMong>>() {
                        @Override
                        public void onResponse(Call<List<DataMong>> call, Response<List<DataMong>> response) {
                            Toast.makeText(v.getContext(), "delete thanh cong!", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<List<DataMong>> call, Throwable t) {

                        }
                    });

                    interCallAPI.load();
                }
            });
            builder.show();

        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public  static  class ViewHolder extends  RecyclerView.ViewHolder{
        TextView txtName, txtPrice, txtDes;
        ImageView  imgDelete, imgEdit;

        ImageView imgView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName= itemView.findViewById(R.id.txtName);
            txtPrice= itemView.findViewById(R.id.txtPrice);
            txtDes= itemView.findViewById(R.id.txtdescription);
            imgView= itemView.findViewById(R.id.imgView);
            imgDelete= itemView.findViewById(R.id.imgdele);
            imgEdit= itemView.findViewById(R.id.imgedit);
        }
        @SuppressLint("SetTextI18n")
        public void  binData(DataMong data){
            txtName.setText(data.getName());
            txtPrice.setText("Giá: "+ data.getPrice());
            txtDes.setText("Chi tiết: "+ data.getDes());

            Picasso.get()
                    .load(data.getImage())
                    .into(imgView);
        }



    }
}
