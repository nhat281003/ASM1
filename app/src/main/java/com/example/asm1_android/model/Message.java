package com.example.asm1_android.model;

public class Message {
    String _id;
    String message;


    public Message() {
    }

    public Message(String _id, String message) {
        this._id = _id;
        this.message = message;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
