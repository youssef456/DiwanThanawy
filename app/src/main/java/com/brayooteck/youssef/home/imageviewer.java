package com.brayooteck.youssef.home;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.brayooteck.youssef.R;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

public class imageviewer extends AppCompatActivity {
    public static String imageurl;
    public Context context;
    private ImageView ImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageviewer);
        context = getApplicationContext();
        ImageView = findViewById(R.id.blog_imageviewer);
        RequestOptions requestOptions = new RequestOptions();
        Glide.with(context).applyDefaultRequestOptions(requestOptions).load(imageurl).into(ImageView);
        SlidrConfig config = new SlidrConfig.Builder().position(SlidrPosition.BOTTOM).build();
        Slidr.attach(this, config);


    }
}
