package com.jana.overwatch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jana.overwatch.POJO.Device;

import java.util.List;

/**
 * Created by Andrew Jeong on 2016-09-20.
 */
public class MainListAdapter extends RecyclerView.Adapter<DeviceViewHolder> implements View.OnClickListener {
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

        holder.mDeviceName.setOnClickListener(this);
        holder.mDeviceStatus.setOnClickListener(this);
        holder.mDeviceUpdate.setOnClickListener(this);

        holder.mDeviceName.setTag(holder);
        holder.mDeviceName.setTag(holder);
        holder.mDeviceName.setTag(holder);
    }

    @Override
    public int getItemCount() {
        return (null != mDevices ? mDevices.size() : 0);
    }

    @Override
    public void onClick(View view) {
        DeviceViewHolder holder = (DeviceViewHolder) view.getTag();
        int position = holder.getPosition();

        Device device = mDevices.get(position);
        Toast.makeText(mContext, device.name, Toast.LENGTH_SHORT).show();
    }
}
