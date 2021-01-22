package com.example.tupperwarestore.model;

import java.util.ArrayList;
import java.util.List;

public class CategoryModel {
    private Integer id;
    private String category = "";
    private Boolean status = false;

    public CategoryModel(Integer id, String category, Boolean status) {
        this.id = id;
        this.category = category;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public CategoryModel() {
    }

    public static List<CategoryModel> init(){
        List<CategoryModel> list = new ArrayList<>();
        list.add(new CategoryModel(0,"Semua", true));
        list.add(new CategoryModel(1,"Botol", false));
        list.add(new CategoryModel(2,"Tempat Makan", false));
        list.add(new CategoryModel(3,"Toples", false));
        list.add(new CategoryModel(4,"Alat Masak", false));
        return list;
    }
}
