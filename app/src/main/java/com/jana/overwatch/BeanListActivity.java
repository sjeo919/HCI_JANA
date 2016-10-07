package com.jana.overwatch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jana.overwatch.POJO.Device;

import java.lang.reflect.Type;
import java.util.List;

public class BeanListActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private List<Device> mDevices;
    private Button mDropButton;
    private RecyclerView mDeviceRecyclerView;
    private MainListAdapter adapter;
    private SharedPreferences sharedPreferences;
    private String jsonDeviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bean_list);

        sharedPreferences = getSharedPreferences("Overwatch_JANA", Context.MODE_PRIVATE);

        mDevices = DeviceListHolder.getInstance().getDeviceList();
        if(mDevices.size() == 0) {
            jsonDeviceList = sharedPreferences.getString("Device_List", "");

            Gson gson = new Gson();
            Type type = new TypeToken<List<Device>>(){}.getType();
            mDevices = gson.fromJson(jsonDeviceList, type);
        }
        sharedPreferences = getSharedPreferences("Overwatch_JANA", Context.MODE_PRIVATE);

        mDropButton = (Button) findViewById(R.id.dropdown_button);
        mDeviceRecyclerView = (RecyclerView) findViewById(R.id.beans_list);
        mDeviceRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MainListAdapter(getApplicationContext(), mDevices);
        mDeviceRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);

        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.dropdown_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notification_option:
                Intent intent = new Intent(this, NotificationActivity.class);
                startActivity(intent);
                return true;
            case R.id.signout_option:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Master_API_Key", "");
                editor.commit();

                Intent signoutIntent = new Intent(this, MainActivity.class);
                Toast.makeText(this, "Successfully signed out", Toast.LENGTH_SHORT).show();
                startActivity(signoutIntent);
                finish();
                return true;
            default:
                return false;
        }
    }
}
