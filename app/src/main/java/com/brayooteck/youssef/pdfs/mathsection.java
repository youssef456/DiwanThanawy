package com.brayooteck.youssef.pdfs;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brayooteck.youssef.R;
import com.brayooteck.youssef.Rootfragment;

public class mathsection extends Rootfragment {
    static FragmentManager fragmentManager;


    public mathsection() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mathsection, container, false);

        final mylist subjectslist = new mylist();
        fragmentManager = getChildFragmentManager();

        TextView ARABIC = (TextView) view.findViewById(R.id.ARABIC);

        ARABIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mylist.url = "http://brayooteck.cu.ma/thanawy/arabic.php";
                MainActivity.setfragment(subjectslist);
            }
        });

        TextView PHYSICS = (TextView) view.findViewById(R.id.PHYSICS);

        PHYSICS.setOnClickListener(new View.OnClickListener() {
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
             TextView ll = (TextView) view.findViewById(R.id.lamy);

           ll.setOnClickListener(new View.OnClickListener() {
               @Override
             public void onClick(View view) {
                  // Create a new intent to open the {@link NumbersActivity}
                  download.name = "kk";
                  download.size = 36246249;
                  download.url = "https://drive.google.com/uc?authuser=0&id=11tO3tWHbThUSC27wTPZ9o0oQaQEOshZH&export=download";
                  download.ans = true;
                  download.ansp =107;
                   download downclass = new download();
                   MainActivity.setfragment(downclass);

               }
         });
        TextView CHEMISTRY = (TextView) view.findViewById(R.id.CHEMISTRY);

        CHEMISTRY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mylist.url = "http://brayooteck.cu.ma/thanawy/chemistry.php";
                MainActivity.setfragment(subjectslist);
            }
        });

        TextView English = (TextView) view.findViewById(R.id.English);

        English.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mylist.url = "http://brayooteck.cu.ma/thanawy/english.php";
                MainActivity.setfragment(subjectslist);
            }
        });
        TextView FRANCE = (TextView) view.findViewById(R.id.FRANCE);

        FRANCE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mylist.url = "http://brayooteck.cu.ma/thanawy/france.php";
                MainActivity.setfragment(subjectslist);
            }
        });
        TextView statics = (TextView) view.findViewById(R.id.STATICS);

        statics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mylist.url = "http://brayooteck.cu.ma/thanawy/statics.php";
                MainActivity.setfragment(subjectslist);
            }
        });
        TextView dynamics = (TextView) view.findViewById(R.id.DYNAMICS);

        dynamics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mylist.url = "http://brayooteck.cu.ma/thanawy/dynamics.php";
                MainActivity.setfragment(subjectslist);
            }
        });
        TextView algebra = (TextView) view.findViewById(R.id.ALGEBRA);

        algebra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mylist.url = "http://brayooteck.cu.ma/thanawy/algebrasolid.php";
                MainActivity.setfragment(subjectslist);
            }
        });
        TextView calclus = (TextView) view.findViewById(R.id.CALCLUS);

        calclus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mylist.url = "http://brayooteck.cu.ma/thanawy/calclus.php";
                MainActivity.setfragment(subjectslist);
            }
        });
return view;
    }


}