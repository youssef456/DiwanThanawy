package com.brayooteck.youssef.home;

import java.util.Date;

public class Comments {

    private String message, user_id;
    private Date timestamp;
    private  String userimage;
    String username;
    String commentimage;
    String notificationid;
    public Comments(){

    }

    public Comments(String message, String user_id, Date timestamp, String notificationoid) {
        this.message = message;
        this.user_id = user_id;
        this.timestamp = timestamp;
        this.userimage = userimage;
        this.username = username;
        this.commentimage = commentimage;
        this.notificationid = notificationoid;

    }

    public String getMessage() {
        return message;
    }

    public String getcommentimage() {
        return commentimage;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser_id() {
        return user_id;
    }
    public String getnotificationid() {
        return notificationid;
    }
    public String getUserimage() {
        return userimage;
    }
    public String getUsername() {
        return username;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
