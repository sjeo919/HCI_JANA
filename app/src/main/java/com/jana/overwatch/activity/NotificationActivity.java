package com.jana.overwatch.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jana.overwatch.R;
import com.jana.overwatch.helper.MainListAdapter;
import com.jana.overwatch.helper.NotificationListAdapter;
import com.jana.overwatch.helper.NotificationListHolder;
import com.jana.overwatch.POJO.Notification;

import java.lang.reflect.Type;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

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

            Type type = new TypeToken<List<Notification>>(){}.getType();
            mNotifications = gson.fromJson(jsonNotificationList, type);
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mNotifications.add(new Notification(extras.getString("title"), extras.getString("body")));
        }

        jsonNotificationList = gson.toJson(mNotifications);

        sharedPreferences.edit().putString("Notification_List", jsonNotificationList).commit();

        //update the UI - notification list
        mNotificationRecyclerView = (RecyclerView) findViewById(R.id.beans_list);
        mNotificationRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MainListAdapter(getApplicationContext(), mNotifications);
        mNotificationRecyclerView.setAdapter(adapter);

    }

}
