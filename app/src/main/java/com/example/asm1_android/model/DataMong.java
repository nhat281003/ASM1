package com.example.asm1_android.model;

import java.io.Serializable;

public class DataMong implements Serializable {

    String _id;
    String name;
    String price;
    String des;
    String image;

    TypeMong type;

    public DataMong(String name, String _id, String price, String des, String image, TypeMong type) {
        this.name = name;
        this._id = _id;
        this.price = price;
        this.des = des;
        this.image = image;
        this.type = type;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public DataMong() {
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public TypeMong getType() {
        return type;
    }

    public void setType(TypeMong type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "DataMong{" +
                "name='" + name + '\'' +
                ", id='" + _id + '\'' +
                ", price='" + price + '\'' +
                ", des='" + des + '\'' +
                ", image='" + image + '\'' +
                ", type=" + type +
                '}';
    }
}
