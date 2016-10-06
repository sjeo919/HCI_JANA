package com.jana.overwatch;

import com.jana.overwatch.POJO.Device;

import java.util.ArrayList;

/**
 * Created by Andrew Jeong on 2016-10-06.
 */

public class DeviceListHolder {
    private ArrayList<Device> deviceList;
    public DeviceListHolder() {
        deviceList = new ArrayList<Device>();
    }

    public ArrayList<Device> getDeviceList() {
        return deviceList;
    }

    private static DeviceListHolder holder = new DeviceListHolder();
    public static DeviceListHolder getInstance() {
        return holder;
    }
}
