package com.brayooteck.youssef.home;


import java.util.Date;

public class BlogPost extends com.brayooteck.youssef.home.BlogPostId {

    public String user_id, image_url, desc;
    public Date timestamp;

    public BlogPost() {}

    public BlogPost(String user_id, String image_url, String desc, Date timestamp) {
        this.user_id = user_id;
        this.image_url = image_url;
        this.desc = desc;
        this.timestamp = timestamp;
    }

    public String getUser_id() {
        return user_id;
    }


    public String getimage_url() {
        return image_url;
    }


    public String getDesc() {
        return desc;
    }


    public Date getTimestamp() {
        return timestamp;
    }




}
