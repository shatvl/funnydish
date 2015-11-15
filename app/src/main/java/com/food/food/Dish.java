package com.food.food;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class Dish implements Parcelable{

    private Integer category;
    private Integer offerId;
    private String name;
    private String price;
    private String description;
    private String ulrPicture;
    private HashMap<String, String> totalDescription;
    private String ulrLocation;

    public Dish(){
        totalDescription = new HashMap<>();
    }

    public Dish(String name, String price, String ulrPicture, HashMap<String, String> totalDescription) {
        this.name = name;
        this.price = price;
        this.ulrPicture = ulrPicture;
        this.totalDescription = totalDescription;
    }

    protected Dish(Parcel in) {
        name = in.readString();
        price = in.readString();
        description = in.readString();
        ulrPicture = in.readString();
        ulrLocation = in.readString();
    }

    public static final Creator<Dish> CREATOR = new Creator<Dish>() {
        @Override
        public Dish createFromParcel(Parcel in) {
            return new Dish(in);
        }

        @Override
        public Dish[] newArray(int size) {
            return new Dish[size];
        }
    };

    public String getUlrLocation() {
        return ulrLocation;
    }

    public void setUlrLocation(String ulrLocation) {
        this.ulrLocation = ulrLocation;
    }

    public HashMap<String, String> getTotalDescription() {
        return totalDescription;
    }

    public void setTotalDescription(HashMap<String, String> totalDescription) {
        this.totalDescription = totalDescription;
    }

    public String getUlrPicture() {
        return ulrPicture;
    }

    public void setUlrPicture(String ulrPicture) {
        this.ulrPicture = ulrPicture;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getCategory());
        dest.writeInt(getOfferId());
        dest.writeString(getName());
        dest.writeString(getPrice());
        dest.writeString(getDescription());
        dest.writeString(getUlrPicture());
        dest.writeString(getUlrLocation());
        dest.writeMap(getTotalDescription());

    }
}
