package com.jana.overwatch.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jana.overwatch.R;
import com.jana.overwatch.helper.NotificationListAdapter;
import com.jana.overwatch.helper.NotificationListHolder;
import com.jana.overwatch.POJO.Notification;

import java.lang.reflect.Type;
import java.util.List;

public class NotificationActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private List<Notification> mNotifications;
    private RecyclerView mNotificationRecyclerView;
    private NotificationListAdapter adapter;
    private SharedPreferences sharedPreferences;
    private String jsonNotificationList;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        sharedPreferences = getSharedPreferences("Overwatch_JANA", Context.MODE_PRIVATE);

        mNotifications = NotificationListHolder.getInstance().getNotificationList();
        if(mNotifications.size() == 0) {
            jsonNotificationList = sharedPreferences.getString("Notification_List", "");
            if (!jsonNotificationList.equals("null") && !jsonNotificationList.equals("")){
                Type type = new TypeToken<List<Notification>>(){}.getType();
                mNotifications = gson.fromJson(jsonNotificationList, type);
            }
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mNotifications.add(new Notification(extras.getString("title"), extras.getString("body"), extras.getString("type")));
        }

        jsonNotificationList = gson.toJson(mNotifications);

        sharedPreferences.edit().putString("Notification_List", jsonNotificationList).commit();

        //update the UI - notification list
        mNotificationRecyclerView = (RecyclerView) findViewById(R.id.noti_list);
        mNotificationRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NotificationListAdapter(getApplicationContext(), mNotifications);
        mNotificationRecyclerView.setAdapter(adapter);

        if (mNotifications.size() > 0) {
            findViewById(R.id.no_noti_text).setVisibility(View.INVISIBLE);
        }

    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);

        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.notification_dropdown_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear_option:
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                sharedPreferences.edit().putString("Notification_List", "null").commit();
                                finish();
                                startActivity(getIntent());
                            }
                        })
                        .setNegativeButton("No", null)
                        .create().show();
                return true;
            case R.id.refresh_option:
                finish();
                startActivity(getIntent());
            default:
                return false;
        }
    }
}
