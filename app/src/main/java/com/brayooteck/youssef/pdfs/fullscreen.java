package com.brayooteck.youssef.pdfs;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;

import com.brayooteck.youssef.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;

public class fullscreen  extends AppCompatActivity implements OnPageChangeListener {
    PDFView pdfView;
    public static String path;
    public static int currentpage;
    public static int anspage;
    public static int size;
    public static boolean ans;
    SharedPreferences.Editor editor;
    boolean ansORback = false;
    BottomNavigationView mainbottomNav;
    private AdView AdView;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreen);
        getSupportActionBar().hide();
        ////////////////////////////////////////ad////////////////////////////
///        MobileAds.initialize(fullscreen.this);
        AdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        AdView.loadAd(adRequest);
        ////////////////////////////////////////////////////////////////////////
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mainbottomNav = findViewById(R.id.fullscreennavigation);
        pdfView = (PDFView) findViewById(R.id.pdfViewer);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(fullscreen.this);
        editor = pref.edit();
        open(currentpage);
        if(ans == false){
               mainbottomNav.getMenu().findItem(R.id.ans).setVisible(false);
        }
        navigationcontroller();


    }
    protected void  open(int page){
        pdfView.fromFile(new File(path)).onPageChange(this).defaultPage(page).enableSwipe(true).load();
    }
    @Override
    public void onPageChanged(int page, int pageCount) {
        if(ansORback == true){ //we are in answers section
            anspage = page;
        }
        else if (ansORback == false){
            currentpage = page;
        }
      //  navigationcontroller(page);
        String title = String.valueOf(page);
        mainbottomNav.getMenu().findItem(R.id.pagenum).setTitle(title);
    }
//    public void onDestroy() {
//        super.onDestroy();
//        download.currentpage = currentpage;
//        download.ansp = anspage;
//     //   editor.putInt(String.valueOf(size), currentpage);
//     //   editor.putInt(String.valueOf("ans " + size), anspage);
//     //   editor.commit();
//
//    }
    @Override
    public void onBackPressed() {
        download.currentpage = currentpage;
        download.ansp = anspage;
        super.onBackPressed();

    }
    public void ans(){
        if(ans == true){
            if(ansORback == false){

                open(currentpage);
                ansORback = true;

            }
            else if(ansORback == true){
                open(anspage);
                ansORback = false;
            }
        }else{
            //   menu.findItem(R.id.ans).setVisible(false);

        }
    }
    public void navigationcontroller(){

        mainbottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.ans:
                    ans();
                       return true;

                    default:
                        return false;
                }
            }
        });


    }
}
