package com.brayooteck.youssef.mcq;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brayooteck.youssef.R;

import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.brayooteck.youssef.Rootfragment;
import com.brayooteck.youssef.pdfs.MainActivity;
import com.brayooteck.youssef.pdfs.mylist;

public class mcqFragment extends Rootfragment{
    static FragmentManager fragmentManager;
    Menu menu;
    MenuInflater inflater;
    public mcqFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mcqmain, container, false);
        setHasOptionsMenu(true);

        final Getmcq Mcq = new Getmcq();
        fragmentManager = getChildFragmentManager();
        TextView ARABIC = (TextView) view.findViewById(R.id.ARABIC);

        ARABIC.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                Getmcq.url = "https://brayootech.000webhostapp.com/Diwan/arabic.php";
                setfragment(Mcq);
            }
        });

        TextView phalspha = (TextView) view.findViewById(R.id.phalspha);

        phalspha.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the family category is clicked on.
            @Override
            public void onClick(View view) {
                Getmcq.url = "http://brayooteck.cu.ma/thanawy/phalsapha.php";
                setfragment(Mcq);
            }
        });

        TextView soch = (TextView) view.findViewById(R.id.soch);

        soch.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the colors category is clicked on.
            @Override
            public void onClick(View view) {
                Getmcq.url = "http://brayooteck.cu.ma/thanawy/elmnafs.php";
                setfragment(Mcq);
            }
        });

        TextView English = (TextView) view.findViewById(R.id.English);

        English.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the phrases category is clicked on.
            @Override
            public void onClick(View view) {
                Getmcq.url = "http://brayooteck.cu.ma/thanawy/english.php";
                setfragment(Mcq);
            }
        });
        TextView FRANCE = (TextView) view.findViewById(R.id.FRANCE);

        FRANCE.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the phrases category is clicked on.
            @Override
            public void onClick(View view) {
                Getmcq.url = "http://brayooteck.cu.ma/thanawy/france.php";
                setfragment(Mcq);
            }
        });
        TextView history = (TextView) view.findViewById(R.id.history);

        history.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the phrases category is clicked on.
            @Override
            public void onClick(View view) {
                Getmcq.url = "http://brayooteck.cu.ma/thanawy/history.php";
                setfragment(Mcq);
            }
        });
        TextView gogh = (TextView) view.findViewById(R.id.gogh);

        gogh.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the phrases category is clicked on.
            @Override
            public void onClick(View view) {
                Getmcq.url = "http://brayooteck.cu.ma/thanawy/goghraphya.php";
                setfragment(Mcq);
            }
        });

        return view;
    }
    public static void setfragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //  FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mcqcontaitner, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        this.menu = menu;
        this.inflater = inflater;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
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
