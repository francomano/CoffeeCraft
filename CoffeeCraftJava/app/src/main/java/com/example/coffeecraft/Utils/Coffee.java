package com.example.coffeecraft.Utils;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Coffee implements Parcelable {

    private String description;
    private int imageResourceId;

    public Coffee(String description, int imageResourceId) {
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    protected Coffee(Parcel in) {
        description = in.readString();
        imageResourceId = in.readInt();
    }

    public static final Creator<Coffee> CREATOR = new Creator<Coffee>() {
        @Override
        public Coffee createFromParcel(Parcel in) {
            return new Coffee(in);
        }

        @Override
        public Coffee[] newArray(int size) {
            return new Coffee[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeInt(imageResourceId);
    }
}
