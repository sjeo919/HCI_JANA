package com.jana.overwatch.POJO;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cybreus on 10/14/2016.
 */

public class Stream implements Parcelable {
    public String name;
    public String display_name;
    public String type;
    public double value;
    public String latest_value_at;

    protected Stream(Parcel in) {
        name = in.readString();
        display_name = in.readString();
        type = in.readString();
        value = in.readDouble();
        latest_value_at = in.readString();
    }

    public static final Creator<Stream> CREATOR = new Creator<Stream>() {
        @Override
        public Stream createFromParcel(Parcel in) { return new Stream(in); }

        @Override
        public Stream[] newArray(int size) { return new Stream[size]; }
    };

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(display_name);
    }
}
