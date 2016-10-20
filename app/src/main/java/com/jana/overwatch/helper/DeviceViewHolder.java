package com.jana.overwatch.helper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jana.overwatch.R;

/**
 * Created by Andrew Jeong on 2016-10-06.
 */

public class DeviceViewHolder extends RecyclerView.ViewHolder {

    protected TextView mDeviceName, mDeviceStatus, mDeviceUpdate;
    protected ImageView mDeviceStatusIcon;

    public DeviceViewHolder(View itemView) {
        super(itemView);
        this.mDeviceName = (TextView) itemView.findViewById(R.id.list_item_name);
        this.mDeviceStatus = (TextView) itemView.findViewById(R.id.device_status);
        this.mDeviceUpdate = (TextView) itemView.findViewById(R.id.updated);
        this.mDeviceStatusIcon = (ImageView) itemView.findViewById(R.id.status_icon);
    }
}
