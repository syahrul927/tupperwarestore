package com.example.tupperwarestore.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;


public class RegisterModel {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RegisterModel{" +
                "email='" + email + '\'' +
                ", nama='" + nama + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
