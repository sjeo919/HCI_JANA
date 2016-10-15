package com.jana.overwatch.POJO;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cybreus on 10/15/2016.
 */

public class Entry implements Parcelable {
    public String trigger;
    public String timeStamp;

    protected Entry(Parcel in) {
        trigger = in.readString();
        timeStamp = in.readString();
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
        parcel.writeString(trigger);
        parcel.writeString(timeStamp);
    }
}
