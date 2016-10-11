package com.jana.overwatch.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jana.overwatch.POJO.Notification;
import com.jana.overwatch.R;

import java.util.List;

/**
 * Created by namjunpark on 11/10/16.
 */

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationViewHolder>{
    private Context mContext;
    private List<Notification> mNotifications;

    public NotificationListAdapter(Context context, List<Notification> notifications) {
        this.mContext = context;
        this.mNotifications = notifications;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_notification, null);

        NotificationViewHolder holder = new NotificationViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        Notification notification = mNotifications.get(position);

        if (notification != null) {
            holder.mNotificationTitle.setText(notification.notification_title);
            holder.mNotificationBody.setText(notification.notification_body);
        }
    }

    @Override
    public int getItemCount() {
        return (null != mNotifications ? mNotifications.size() : 0);
    }
}
