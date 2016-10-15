package com.jana.overwatch.POJO;

/**
 * Created by Andrew Jeong on 2016-10-09.
 */

public class Notification {
    public String notification_title;
    public String notification_body;
    public String notification_type;

    public Notification (String title, String body, String type) {
        this.notification_title = title;
        this.notification_body = body;
        this.notification_type = type;
    }

}
