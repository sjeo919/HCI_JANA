package com.jana.overwatch.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jana.overwatch.POJO.Device;
import com.jana.overwatch.R;

import java.util.List;

/**
 * Created by Andrew Jeong on 2016-09-20.
 */
public class MainListAdapter extends RecyclerView.Adapter<DeviceViewHolder> {
    private Context mContext;
    private List<Device> mDevices;

    public MainListAdapter(Context context, List<Device> devices) {
        this.mContext = context;
        this.mDevices = devices;
    }

    @Override
    public DeviceViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bean_list_item, null);

        DeviceViewHolder holder = new DeviceViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DeviceViewHolder holder, int position) {
        Device device = mDevices.get(position);

        if (device != null) {
            holder.mDeviceName.setText(device.name);
            holder.mDeviceStatus.setText(device.status);
            holder.mDeviceUpdate.setText(device.updated);
        }
    }

    @Override
    public int getItemCount() {
        return (null != mDevices ? mDevices.size() : 0);
    }
}
