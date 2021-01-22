package com.example.tupperwarestore.model.resp;

import com.example.tupperwarestore.model.CategoryModel;

public class ProductResp {
    private String id;
    private String name;
    private String pict;
    private String desc;
    private Double price;
    private CategoryModel category;

    @Override
    public String toString() {
        return "ProductResp{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pict='" + pict + '\'' +
                ", desc='" + desc + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", variant=" + variant +
                '}';
    }

    public ProductResp(String id, String name, String pict, String desc, Double price, CategoryModel category, VariantResp variant) {
        this.id = id;
        this.name = name;
        this.pict = pict;
        this.desc = desc;
        this.price = price;
        this.category = category;
        this.variant = variant;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    private VariantResp variant;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPict() {
        return pict;
    }

    public void setPict(String pict) {
        this.pict = pict;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public VariantResp getVariant() {
        return variant;
    }

    public void setVariant(VariantResp variant) {
        this.variant = variant;
    }

    public ProductResp() {
    }

}
