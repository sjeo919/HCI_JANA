package com.jana.overwatch.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jana.overwatch.helper.DeviceListHolder;
import com.jana.overwatch.POJO.Device;
import com.jana.overwatch.R;

public class DeviceActivity extends AppCompatActivity {

    private Device mDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        Intent incomingIntent = getIntent();
        int devicePos = incomingIntent.getIntExtra("device_position", 0);
        mDevice = DeviceListHolder.getInstance().getDeviceList().get(devicePos);
    }
}
