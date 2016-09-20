package com.jana.overwatch;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by Andrew Jeong on 2016-09-20.
 */
public class Target implements Serializable {
    public int mImage;
    public String mName;

    public Target(int image, String name) {
        this.mImage = image;
        this.mName = name;
    }

    public int getImage() {
        return this.mImage;
    }

    public String getName() {
        return this.mName;
    }
}
