package com.brayooteck.youssef.notifications;

import java.util.Date;

public class notifications {
    private String Blogpostid;
    private String user_id;
    private String notificationstype;
    private String comment;
    public Date timestamp;

    public notifications() {

    }

    public notifications(String Blogpostid, String user_id, String  notificationstype, String comment,Date timestamp) {
        this.Blogpostid = Blogpostid;
        this.user_id = user_id;
        this.notificationstype  = notificationstype;
        this.comment  = comment;
        this.timestamp = timestamp;

    }

    public String getBlogpostid() {
        return Blogpostid;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getnotificationstype(){
        return  notificationstype;
    }

    public String getcomment(){
        return comment;
    }

    public Date getTimestamp() {
        return timestamp;
    }

}