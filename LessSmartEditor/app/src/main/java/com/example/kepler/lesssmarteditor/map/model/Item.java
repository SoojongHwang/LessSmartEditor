package com.example.kepler.lesssmarteditor.map.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kepler on 2017-05-24.
 */

public class Item implements Parcelable{
    public String title;
    public String roadAddress;
    public float mapx;
    public float mapy;


    protected Item(Parcel in) {
        title = in.readString();
        roadAddress = in.readString();
        mapx = in.readFloat();
        mapy = in.readFloat();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public String toString() {
        return "이름: "+title+"\n주소: "+roadAddress+"\n좌표: "+mapx+", "+mapy;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(roadAddress);
        dest.writeFloat(mapx);
        dest.writeFloat(mapy);
    }
}