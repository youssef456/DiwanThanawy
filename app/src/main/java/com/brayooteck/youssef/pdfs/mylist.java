package com.brayooteck.youssef.pdfs;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.fragment.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.brayooteck.youssef.R;
import com.brayooteck.youssef.Rootfragment;
import com.google.android.gms.ads.MobileAds;


public class mylist  extends Rootfragment {
    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;
    public static String url;
    GridView lv;
    public String json = null;
    static FragmentManager fragmentManager;
    MenuInflater inflater;
    Menu menu;
    public mylist() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.list, container, false);
///        MobileAds.initialize(getContext());
        setHasOptionsMenu(true);

        lv =  view.findViewById(R.id.lv);
        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        json = pref.getString(url, null);

        if (json != null) {


           //Parser p = new Parser(getContext(), json, lv);
           Parser p = new Parser(getActivity(), json, lv);
           fragmentManager = getChildFragmentManager();
           p.execute();
           Log.i("Info", "loaddownloaded");
       }
         else {

             down();

        }
         return view;
    }

    private void down() {
       // final Downloader d = new Downloader(getContext(), url, lv);
        final Downloader d = new Downloader(getActivity(), url, lv);
        fragmentManager = getChildFragmentManager();
        d.execute();
        Log.i("Info", "willdownload");
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        this.menu = menu;
        this.inflater = inflater;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
        inflater.inflate(R.menu.mainmenu, menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
        down();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume() {
        super.onResume();
        if(menu != null){
            menu.clear();
            setHasOptionsMenu(false);
            setHasOptionsMenu(true);
        }

    }

}




