package com.jana.overwatch.POJO;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Andrew Jeong on 2016-10-01.
 */

public class Device implements Parcelable {

    public String id;
    public String name;
    public String description;
    public String visibility;
    public String status;
    public String serial;
    public String[] tags;
    public String url;
    public String created;
    public String updated;
    public String key;

    protected Device(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        visibility = in.readString();
        status = in.readString();
        serial = in.readString();
        tags = in.createStringArray();
        url = in.readString();
        created = in.readString();
        updated = in.readString();
        key = in.readString();
    }

    public static final Creator<Device> CREATOR = new Creator<Device>() {
        @Override
        public Device createFromParcel(Parcel in) {
            return new Device(in);
        }

        @Override
        public Device[] newArray(int size) {
            return new Device[size];
        }
    };

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
    }
}
