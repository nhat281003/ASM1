package com.example.asm1_android.model;

import java.io.Serializable;

public class TypeMong implements Serializable {
    String _id;
    String name;
    String des;

    public TypeMong() {
    }

    public TypeMong(String _id, String name, String dess) {
        this._id = _id;
        this.name = name;
        this.des = dess;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDess() {
        return des;
    }

    public void setDess(String dess) {
        this.des = dess;
    }


    @Override
    public String toString() {
        return "TypeMong{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", des='" + des + '\'' +
                '}';
    }
}
