package com.example.tupperwarestore.model.resp;

public class UserDetailResp {
    private String email;
    private String name;
    private String address;

    public UserDetailResp() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserDetailResp(String email, String name, String address) {
        this.email = email;
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserDetailResp{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
