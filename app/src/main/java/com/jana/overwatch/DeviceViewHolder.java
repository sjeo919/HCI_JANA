package com.jana.overwatch;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Owner on 2016-10-06.
 */

public class DeviceViewHolder extends RecyclerView.ViewHolder {

    protected TextView mDeviceName, mDeviceId, mDeviceUpdate;

    public DeviceViewHolder(View itemView) {
        super(itemView);
        this.mDeviceName = (TextView) itemView.findViewById(R.id.list_item_name);
        this.mDeviceId = (TextView) itemView.findViewById(R.id.id_number);
        this.mDeviceUpdate = (TextView) itemView.findViewById(R.id.updated);
    }
}
