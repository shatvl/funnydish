package com.food.food;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;


public class CatalogItems implements Parcelable {
    private Integer id;
    private String name;

    protected CatalogItems(Parcel in) {
        name = in.readString();
    }

    public static final Creator<CatalogItems> CREATOR = new Creator<CatalogItems>() {
        @Override
        public CatalogItems createFromParcel(Parcel in) {
            return new CatalogItems(in);
        }

        @Override
        public CatalogItems[] newArray(int size) {
            return new CatalogItems[size];
        }
    };

    public CatalogItems() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeString(getName());
    }
}
