package com.food.food;

import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.LatLng;

public class CustomLocation {

    private Drawable image;
    private String name;
    private LatLng latLng;
    private String phone;

    public CustomLocation(Drawable im, String na, LatLng l, String ph){
        this.image = im;
        this.name = na;
        this.latLng = l;
        this.phone = ph;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
