package com.jana.overwatch.helper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jana.overwatch.R;

/**
 * Created by Owner on 2016-10-06.
 */

public class DeviceViewHolder extends RecyclerView.ViewHolder {

    protected TextView mDeviceName, mDeviceStatus, mDeviceUpdate;

    public DeviceViewHolder(View itemView) {
        super(itemView);
        this.mDeviceName = (TextView) itemView.findViewById(R.id.list_item_name);
        this.mDeviceStatus = (TextView) itemView.findViewById(R.id.device_status);
        this.mDeviceUpdate = (TextView) itemView.findViewById(R.id.updated);
    }
}
