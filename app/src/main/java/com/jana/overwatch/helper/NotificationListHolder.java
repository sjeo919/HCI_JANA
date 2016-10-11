package com.jana.overwatch.helper;

import com.jana.overwatch.POJO.Notification;

import java.util.ArrayList;

/**
 * Created by Andrew Jeong on 2016-10-09.
 */

public class NotificationListHolder {
    private ArrayList<Notification> notificationList;
    public NotificationListHolder() {
        notificationList = new ArrayList<Notification>();
    }

    public ArrayList<Notification> getNotificationList() {
        return notificationList;
    }

    private static NotificationListHolder holder = new NotificationListHolder();
    public static NotificationListHolder getInstance() {
        return holder;
    }
}
