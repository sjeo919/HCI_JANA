package com.jana.overwatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jana.overwatch.POJO.Device;

import java.util.List;

public class BeanListActivity extends AppCompatActivity {

    private List<Device> mDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bean_list);

        Intent intent = getIntent();
        mDevices = intent.getParcelableArrayListExtra("device-list");


    }

}
