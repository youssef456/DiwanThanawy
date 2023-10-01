package com.brayooteck.youssef.mcq;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.brayooteck.youssef.R;

import java.util.ArrayList;

public class mcqList extends ArrayAdapter<mcqQuizAdapter>  {
    Context c;
    ArrayList<mcqArrayAdapter> questionsadapter;
    // FragmentManager fragmentManager;



    public mcqList(Context c, int adapter, ArrayList<mcqQuizAdapter> quizez) {
        super(c,adapter,0,quizez);
        this.c = c;


    }


    static class ViewHolder {
        Button btn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //final mcqArrayAdapter mcqAdapter =getItem(position);
        final mcqQuizAdapter mcqQuizAdapter = getItem(position);
        View rowView = convertView;


        if (rowView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            rowView = inflater.inflate(R.layout.adapter, parent, false);
            ViewHolder h = new ViewHolder();
            h.btn = rowView.findViewById(R.id.but);
            rowView.setTag(h);

        }

        ViewHolder h = (ViewHolder) rowView.getTag();

        String quizname = mcqQuizAdapter.getQuizName();

        h.btn.setText(quizname);
        h.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Quiz quiz = new Quiz();
                quiz.url = mcqQuizAdapter.getQuizLink();
                Log.i("Info", "willdownloadthequiz " + mcqQuizAdapter.getQuizLink());
                mcqFragment.setfragment(quiz);

            }
        });

    return rowView;

    }


}