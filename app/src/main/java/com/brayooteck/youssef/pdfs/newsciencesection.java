package com.brayooteck.youssef.pdfs;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brayooteck.youssef.R;
import com.brayooteck.youssef.Rootfragment;

public class newsciencesection  extends Rootfragment {
    static FragmentManager fragmentManager;

    public newsciencesection() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        // Set the content of the activity to use the activity_main.xml layout file
        View view = inflater.inflate(R.layout.newsciencesection, container, false);
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

        TextView PHYSICS = (TextView) view.findViewById(R.id.PHYSICS);

        PHYSICS.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the family category is clicked on.
            @Override
            public void onClick(View view) {
                mylist.url = "http://brayooteck.cu.ma/thanawy/physics.php";
                MainActivity.setfragment(subjectslist);
            }
        });
        TextView PHYSICSARAB = (TextView) view.findViewById(R.id.PHYSICSARAB);

        PHYSICSARAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mylist.url = "http://brayooteck.cu.ma/thanawy/physicsarab.php";
                MainActivity.setfragment(subjectslist);
            }
        });
        TextView CHEMISTRY = (TextView) view.findViewById(R.id.CHEMISTRY);

        CHEMISTRY.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the colors category is clicked on.
            @Override
            public void onClick(View view) {
                mylist.url = "http://brayooteck.cu.ma/thanawy/chemistry.php";
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
        TextView GEOLGY = (TextView) view.findViewById(R.id.GEOLGY);

        GEOLGY.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the phrases category is clicked on.
            @Override
            public void onClick(View view) {
                mylist.url = "http://brayooteck.cu.ma/thanawy/geology.php";
                MainActivity.setfragment(subjectslist);
            }
        });
        TextView GEOLGYARAB = (TextView) view.findViewById(R.id.BIOLOGY);

        GEOLGYARAB.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the phrases category is clicked on.
            @Override
            public void onClick(View view) {
                mylist.url = "http://brayooteck.cu.ma/thanawy/bioaraby.php";
                MainActivity.setfragment(subjectslist);
            }
        });
        return view;
    }



}