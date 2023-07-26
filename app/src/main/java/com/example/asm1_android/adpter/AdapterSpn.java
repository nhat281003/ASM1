package com.example.asm1_android.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asm1_android.R;
import com.example.asm1_android.model.TypeMong;

import java.util.List;

public class AdapterSpn extends BaseAdapter {
    List<TypeMong> list;
    public AdapterSpn(List<TypeMong> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderr mViewHolder = null;
        if(convertView == null){
            mViewHolder = new ViewHolderr();
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(parent.getContext().LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_spn,null);
            mViewHolder.name = convertView.findViewById(R.id.txtNamespn);
            convertView.setTag(mViewHolder);
        }else mViewHolder = (ViewHolderr)  convertView.getTag();
        mViewHolder.name.setText(list.get(position).getName());

        return convertView;
    }
    public static class ViewHolderr{
        private TextView name;
    }
}
