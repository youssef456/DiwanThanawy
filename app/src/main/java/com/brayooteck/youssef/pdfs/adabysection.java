package com.brayooteck.youssef.pdfs;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brayooteck.youssef.R;
import com.brayooteck.youssef.Rootfragment;

public class adabysection  extends Rootfragment {
    static FragmentManager fragmentManager;

    public adabysection() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.adabysection, container, false);


        final mylist subjectslist = new mylist();
        fragmentManager = getChildFragmentManager();
        TextView ARABIC = (TextView) view.findViewById(R.id.ARABIC);

        ARABIC.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                mylist.url = "http://brayooteck.cu.ma/thanawy/arabic.php";
                MainActivity.setfragment(subjectslist);
            }
        });

        TextView phalspha = (TextView) view.findViewById(R.id.phalspha);

        phalspha.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the family category is clicked on.
            @Override
            public void onClick(View view) {
                mylist.url = "http://brayooteck.cu.ma/thanawy/phalsapha.php";
                MainActivity.setfragment(subjectslist);
            }
        });

        TextView soch = (TextView) view.findViewById(R.id.soch);

        soch.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the colors category is clicked on.
            @Override
            public void onClick(View view) {
                mylist.url = "http://brayooteck.cu.ma/thanawy/elmnafs.php";
                MainActivity.setfragment(subjectslist);
            }
        });

        TextView English = (TextView) view.findViewById(R.id.English);

        English.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the phrases category is clicked on.
            @Override
            public void onClick(View view) {
                mylist.url = "http://brayooteck.cu.ma/thanawy/english.php";
                MainActivity.setfragment(subjectslist);
            }
        });
        TextView FRANCE = (TextView) view.findViewById(R.id.FRANCE);

        FRANCE.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the phrases category is clicked on.
            @Override
            public void onClick(View view) {
                mylist.url = "http://brayooteck.cu.ma/thanawy/france.php";
                MainActivity.setfragment(subjectslist);
            }
        });
        TextView history = (TextView) view.findViewById(R.id.history);

        history.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the phrases category is clicked on.
            @Override
            public void onClick(View view) {
                mylist.url = "http://brayooteck.cu.ma/thanawy/history.php";
                MainActivity.setfragment(subjectslist);
            }
        });
        TextView gogh = (TextView) view.findViewById(R.id.gogh);

        gogh.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the phrases category is clicked on.
            @Override
            public void onClick(View view) {
                mylist.url = "http://brayooteck.cu.ma/thanawy/goghraphya.php";
                MainActivity.setfragment(subjectslist);
            }
        });

return view;
    }




}

