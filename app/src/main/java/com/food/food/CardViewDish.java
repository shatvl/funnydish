package com.food.food;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class CardViewDish implements Parcelable {
    private Integer id;
    private String name;
    private Integer price;
    private Integer weight;
    private Drawable image;

    public CardViewDish(Integer id, Drawable image, String name, Integer price, Integer weight) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.weight = weight;
    }

    protected CardViewDish(Parcel in) {
        name = in.readString();
    }

    public static final Creator<CardViewDish> CREATOR = new Creator<CardViewDish>() {
        @Override
        public CardViewDish createFromParcel(Parcel in) {
            return new CardViewDish(in);
        }

        @Override
        public CardViewDish[] newArray(int size) {
            return new CardViewDish[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeString(getName());
        dest.writeInt(getPrice());
        dest.writeInt(getWeight());
        dest.writeParcelable(((BitmapDrawable) getImage()).getBitmap(), flags);
    }
}
