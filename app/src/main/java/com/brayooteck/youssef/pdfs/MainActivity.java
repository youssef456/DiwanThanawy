package com.brayooteck.youssef.pdfs;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brayooteck.youssef.R;
import com.brayooteck.youssef.Rootfragment;

public class MainActivity extends Rootfragment {

    static FragmentManager fragmentManager;

    public MainActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main, container, false);

        TextView math = (TextView) view.findViewById(R.id.math);

        math.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {

                mathsection fragment = new mathsection();
                fragmentManager = getChildFragmentManager();
                setfragment(fragment);

            }
        });


        TextView science = (TextView) view.findViewById(R.id.science);

        science.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {

               newsciencesection sciencesction = new newsciencesection();
               fragmentManager = getChildFragmentManager();
               setfragment(sciencesction);
            }
        });



        TextView adaby = (TextView) view.findViewById(R.id.adaby);

        adaby.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {

                adabysection adabysction = new adabysection();
                fragmentManager = getChildFragmentManager();
                setfragment(adabysction);
            }
        });
    return view;
    }

    public static void setfragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      //  FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.sectionscontaitner, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

}