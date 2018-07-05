package com.gglabs.materna.Model;

import android.location.Location;

/**
 * Created by GG on 21/02/2018.
 */

public class Delivery extends Task {

    private Location location;
    private String address;
    private long timeCreated, timeToPick;
    private String[] products;

    public Delivery(String userCreatedId, long timeCreated, long timeToPick, String address, String... products) {
        super(userCreatedId);
        this.timeCreated = timeCreated;
        this.timeToPick = timeToPick;
        this.products = products;
        this.address = address;
    }

    @Override
    public String toString() {
        return _id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (obj instanceof Delivery) return _id.equals(((Task) obj).getId());

        if (obj instanceof Task) return _id.equals(((Task) obj).getId());

        if (obj instanceof String) return _id.equals(obj);

        return super.equals(obj);
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public long getTimeToPick() {
        return timeToPick;
    }

    public void setTimeToPick(long timeToPick) {
        this.timeToPick = timeToPick;
    }

    public String[] getProducts() {
        return products;
    }

    public void setProducts(String[] products) {
        this.products = products;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
