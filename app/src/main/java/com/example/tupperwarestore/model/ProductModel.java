package com.example.tupperwarestore.model;

import java.util.ArrayList;
import java.util.List;

public class ProductModel {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer id;
    private String titleProduct = "";
    private String priceProduct = "";
    private String url = "";
    private Integer idCategory;

    public String getTitleProduct() {
        return titleProduct;
    }

    public void setTitleProduct(String titleProduct) {
        this.titleProduct = titleProduct;
    }

    public String getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(String priceProduct) {
        this.priceProduct = priceProduct;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ProductModel() {
    }

    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
    }

    public ProductModel(Integer id, String titleProduct, String priceProduct, String url, Integer idCategory) {
        this.id = id;
        this.titleProduct = titleProduct;
        this.priceProduct = priceProduct;
        this.url = url;
        this.idCategory = idCategory;
    }

    public static List<ProductModel> init(){
        List<ProductModel> list = new ArrayList<>();
        list.add(new ProductModel(1,"Coffe To Go", "Rp 150.000", "https://picsum.photos/200/300",1));
        list.add(new ProductModel(2,"Ilumina Tumbler Set", "Rp 140.000", "https://picsum.photos/200/300",2));
        list.add(new ProductModel(3, "Eco Bottle", "Rp 70.000", "https://picsum.photos/200/300",1));
        list.add(new ProductModel(4,"H2Go Bottle", "Rp 50.000", "https://picsum.photos/200/300",1));
        list.add(new ProductModel(5,"Extreme Bottle", "Rp 85.000", "https://picsum.photos/200/300",1));
        list.add(new ProductModel(6,"Ezy Pour", "Rp 85.000", "https://picsum.photos/200/300",3));
        list.add(new ProductModel(7,"Urban Spice T Go", "Rp 125.000", "https://picsum.photos/200/300",4));
        list.add(new ProductModel(8,"Browl Besar", "Rp 185.000", "https://picsum.photos/200/300",3));
        list.add(new ProductModel(9,"Blossom Collection", "Rp 220.000", "https://picsum.photos/200/300",2));
        return list;
    }
}
