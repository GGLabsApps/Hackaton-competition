package com.gglabs.materna.Model;

import android.util.Log;

/**
 * Created by GG on 16/02/2018.
 */

public class Product {

    private static final String TAG = "Product";
    private String id;
    private String barcode;
    private String name;
    private int weight, quantity;
    private float price;

    public Product(String id, String barcode, String name, int weight, float price) {
        this.id = id;
        this.barcode = barcode;
        this.name = name;
        this.weight = weight;
        this.price = price;
        quantity = 1;
    }

    public Product(String barcode, String name, int weight, float price) {
        this(null, barcode, name, weight, price);
    }

    @Override
    public String toString() {
        return barcode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (obj instanceof Product) {
            Product p = (Product) obj;
            Log.d(TAG, "equals(Product p): " + this.barcode.equals(p.getBarcode()));
            return this.barcode.equals(p.getBarcode());
        }

        if (obj instanceof String) {
            Log.d(TAG, "equals(String s): " + this.barcode.equals(obj));
            String s = (String) obj;
            return this.barcode.equals(s);
        }

        Log.d(TAG, "equals(Object obj): " + this.barcode.equals(obj.toString()));
        return this.barcode.equals(obj.toString());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addQuantity() {
        quantity++;
    }

    public void subQuantity() {
        if (quantity > 1) quantity--;
    }
}
