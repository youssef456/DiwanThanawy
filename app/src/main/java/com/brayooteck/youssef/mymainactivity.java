package com.brayooteck.youssef;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.brayooteck.youssef.notifications.NotificationFragment;
import com.brayooteck.youssef.notifications.opennotification;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class mymainactivity extends AppCompatActivity{

    private FirebaseAuth mAuth;
    public static BottomNavigationView mainbottomNav;
    public static String baackstack;
    public static FragmentManager fragmentManager;
    public static ImageView notificationdot;
    private AdView AdView;
    public static boolean isauthenabled = true;
    public static boolean ishomeenabled = true;
    public Menu menu;
    fragmentbridge fragment;

;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newmainlayout);


        FirebaseApp.initializeApp(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();
        mainbottomNav = findViewById(R.id.mainBottomNav);

      //  mainbottomNav.getMenu().findItem(R.id.pdfs).isChecked();
        notificationdot = findViewById(R.id.notificationdot);
        fragmentManager = getSupportFragmentManager();
        mainbottomNav.getMenu().findItem(R.id.home).setVisible(false);
        mainbottomNav.getMenu().findItem(R.id.notifications).setVisible(false);
///        notificationdot.setVisibility(View.GONE);
///        mainbottomNav.getMenu().findItem(R.id.account).setVisible(true);
        if(mAuth.getCurrentUser() == null && isauthenabled == true) {                          //enable sign up
///            sendToLogin();
        }else if(mAuth.getCurrentUser() == null && isauthenabled == false){                    //disable sign up

            notificationdot.setVisibility(View.GONE);
        }else if(mAuth.getCurrentUser() !=null && ishomeenabled == true){                       //enable news feed

        }else if(mAuth.getCurrentUser() != null && ishomeenabled == false){                       //disable news feed

        }
        isStoragePermissionGranted();
         ////////////////////////////////////////////
        MobileAds.initialize(mymainactivity.this);
        AdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        AdView.loadAd(adRequest);
        //////////////////////////////////////////////////////////////
        createNotificationChannel();
        //////////////////////////////////////////////////////////////
        fragment = new fragmentbridge();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.maincontainer, fragment);
        Log.i("Info", "mymainactivity");
        fragmentTransaction.commit();
        /////////////////////////////////////////////////
        mainbottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {

                        case R.id.pdfs:
                            fragmentbridge.viewPager.setCurrentItem(0);
                            return true;
                        case R.id.MCQ:
                            fragmentbridge.viewPager.setCurrentItem(1);
                            return true;
                        case R.id.lectures:
                            fragmentbridge.viewPager.setCurrentItem(2);
                            return true;
                        case R.id.home:
                            fragmentbridge.viewPager.setCurrentItem(3);
                            return true;
                        case R.id.notifications:
                            fragmentbridge.viewPager.setCurrentItem(4);
                            return true;
///                        case R.id.account:
///                            fragmentbridge.viewPager.setCurrentItem(5);
///                            return true;

                        default:
                            return false;


                    }

                }
            });
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        String type = getIntent().getStringExtra("From");
        final String blog_postid = getIntent().getStringExtra("blogpostid");
        if (type != null) {
            switch (type) {
                case "notifyFrag":
                    Log.i("Info", blog_postid);
                    //////////////////////////////////delay////////////////////////////////////////////////
                    fragmentbridge.viewPager.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            fragmentbridge.viewPager.setCurrentItem(3);
                            opennotification open = new opennotification();
                            open.blogpostid = blog_postid;
                            open.c = mymainactivity.this;
                            Log.i("Info", "openingnotification");
                            NotificationFragment.setfragment(open);
                        }
                    }, 100);

                    break;
            }
        }else {
            // i am suppost to move all oncreate methods here!!
        }

        }

//       private void logOut() {
//
//
//        mAuth.signOut();
//        sendToLogin();
//    }

    private void sendToLogin() {

        Intent loginIntent = new Intent(mymainactivity.this, login.class);
        startActivity(loginIntent);
        finish();

    }

    public void isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(mymainactivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        } else { //permission is automatically granted on sdk<23 upon installation
        }
    }


    @Override
    public void onBackPressed() {

        if (!fragment.onBackPressed()) {
            // container Fragment or its associates couldn't handle the back pressed task
            // delegating the task to super class
            super.onBackPressed();

        } else {
            // carousel handled the back pressed task
            // do not call super
        }
    }
/////////////////////////////////////////////////////////notificationchannel///////////////////////////////////////////////////
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channelname";
            String description = "channeldesc";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channelid", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
