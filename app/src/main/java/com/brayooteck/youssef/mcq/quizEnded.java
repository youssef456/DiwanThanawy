package com.brayooteck.youssef.mcq;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.brayooteck.youssef.R;
import com.brayooteck.youssef.Rootfragment;

public class quizEnded extends Rootfragment {
    static FragmentManager fragmentManager;
    Menu menu;
    MenuInflater inflater;
    public boolean[] answers;
    int correctAnswers = 0;
    int wrongAnswers = 0;
    public quizEnded() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.quizendedlayout, container, false);
        setHasOptionsMenu(true);

        fragmentManager = getChildFragmentManager();
        for(int i = 0;i< answers.length;i++){
            if(answers[i]){
                correctAnswers ++;
            }
            else {
                wrongAnswers ++;
            }
        }
        TextView txt = (TextView) view.findViewById(R.id.answers);
        txt.setText("Grade " + correctAnswers + "/" + answers.length);


        return view;
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
