package com.example.tupperwarestore.repository;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bag_table")
public class Bag {

    @PrimaryKey
    @NonNull
    private String id;

    private String name;
    private Integer qty;
    private Double price;
    private String pict;

    public Bag() {
    }

    public Bag(@NonNull String id, String name, Integer qty, Double price, String pict) {
        this.id = id;
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.pict = pict;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPict() {
        return pict;
    }

    public void setPict(String pict) {
        this.pict = pict;
    }

    @Override
    public String toString() {
        return "Bag{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                ", pict='" + pict + '\'' +
                '}';
    }
}
