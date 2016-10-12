package com.jana.overwatch.helper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jana.overwatch.R;

/**
 * Created by namjunpark on 11/10/16.
 */

public class NotificationViewHolder extends RecyclerView.ViewHolder {
    protected TextView mNotificationTitle, mNotificationBody;

    public NotificationViewHolder(View itemView) {
        super(itemView);
        this.mNotificationTitle = (TextView) itemView.findViewById(R.id.notification_title);
        this.mNotificationBody = (TextView) itemView.findViewById(R.id.notification_body);
    }
}
