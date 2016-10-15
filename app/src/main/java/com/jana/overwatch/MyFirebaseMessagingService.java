package com.jana.overwatch;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import android.os.Handler;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jana.overwatch.POJO.Notification;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by namjunpark on 11/10/16.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        SharedPreferences sharedPreferences = getSharedPreferences("Overwatch_JANA", Context.MODE_PRIVATE);
        String jsonNotificationList = sharedPreferences.getString("Notification_List", "");
        Gson gson = new Gson();

        List<Notification> mNotifications;
        if (!jsonNotificationList.equals("null")){
            Type type = new TypeToken<List<Notification>>(){}.getType();
            mNotifications = gson.fromJson(jsonNotificationList, type);
        } else {
            mNotifications = new ArrayList<Notification>();
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("TAG", "Message data payload: " + remoteMessage.getData());
            mNotifications.add(new Notification(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"), remoteMessage.getData().get("type")));
            jsonNotificationList = gson.toJson(mNotifications);
            sharedPreferences.edit().putString("Notification_List", jsonNotificationList).commit();

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Notification Arrived!", Toast.LENGTH_LONG).show();
                }
            });
            //sound should be played with this toast.
        }

    }

}


