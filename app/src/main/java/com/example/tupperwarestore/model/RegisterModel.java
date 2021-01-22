package com.example.tupperwarestore.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;


public class RegisterModel extends BaseObservable {
    private String email = "";
    private String nama = "";
    private String password = "";

    public RegisterModel() {
    }

    public RegisterModel(String email, String nama, String password) {
        this.email = email;
        this.nama = nama;
        this.password = password;
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Bindable
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
