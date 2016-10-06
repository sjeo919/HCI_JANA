package com.jana.overwatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.jana.overwatch.POJO.Device;

import java.util.List;

public class BeanListActivity extends AppCompatActivity {

    private List<Device> mDevices;
    private Button mNotiButton;
    private RecyclerView mDeviceRecyclerView;
    private MainListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bean_list);

        mDevices = DeviceListHolder.getInstance().getDeviceList();

        mNotiButton = (Button) findViewById(R.id.notification_button);
        mDeviceRecyclerView = (RecyclerView) findViewById(R.id.beans_list);
        mDeviceRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MainListAdapter(getApplicationContext(), mDevices);
        mDeviceRecyclerView.setAdapter(adapter);

        mNotiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                startActivity(intent);
            }
        });

    }

}
