package com.brayooteck.youssef.mcq;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.brayooteck.youssef.R;
import com.brayooteck.youssef.Rootfragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Quiz  extends Rootfragment {

    static FragmentManager fragmentManager;
    Menu menu;
    MenuInflater inflater;

    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;
    public String json = null;
    public static String url;

    public static ArrayList<mcqArrayAdapter> mcqQuestionsAdaper = new ArrayList<mcqArrayAdapter>();

    int currentQuestionNumber = 0;
    public static Button ansA;
    public static Button ansB;
    public static Button ansC;
    public static Button ansD;
    public static TextView question;
    public static int correctAnswer;

    public static boolean[] answers;
    public Quiz() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.questionlayout, container, false);
        setHasOptionsMenu(true);

        question = (TextView) view.findViewById(R.id.Question);
        ansA = (Button) view.findViewById(R.id.ansA);
        ansB  = (Button) view.findViewById(R.id.ansB);
        ansC = (Button) view.findViewById(R.id.ansC);
        ansD = (Button) view.findViewById(R.id.ancD);

        fragmentManager = getChildFragmentManager();


        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        json = pref.getString("quizJSON" + url, null);

        if (json != null) {

            //Parser p = new Parser(getContext(), json, lv);
            quizParser p = new quizParser(getActivity(), json);
            fragmentManager = getChildFragmentManager();
            p.execute();
            Log.i("Info", "loaddownloaded");
        }
        else {

            down();

        }

        ansA.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                if (correctAnswer == 1) {
                    //correct
                    answers[currentQuestionNumber] = true;
                }else{
                    //wrong
                    answers[currentQuestionNumber] = false;

                }
                if(currentQuestionNumber < mcqQuestionsAdaper.size() - 1){
                    currentQuestionNumber ++;
                    setQuestion(currentQuestionNumber);
                }
                else {
                    quizended();

                }
            }
        });

        ansB.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the family category is clicked on.
            @Override
            public void onClick(View view) {
                if (correctAnswer == 2) {
                    //correct
                    answers[currentQuestionNumber] = true;
                }else{
                    //wrong
                    answers[currentQuestionNumber] = false;
                }
                if(currentQuestionNumber < mcqQuestionsAdaper.size() - 1){
                    currentQuestionNumber ++;
                    setQuestion(currentQuestionNumber);
                }
                else {
                    quizended();

                }
            }
        });

        ansC.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the colors category is clicked on.
            @Override
            public void onClick(View view) {
                if (correctAnswer == 3) {
                    //correct
                    answers[currentQuestionNumber] = true;
                }else{
                    //wrong
                    answers[currentQuestionNumber] = false;
                }
                if(currentQuestionNumber < mcqQuestionsAdaper.size() - 1){
                    currentQuestionNumber ++;
                    setQuestion(currentQuestionNumber);
                }
                else {
                    quizended();

                }
            }
        });

        ansD.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the phrases category is clicked on.
            @Override
            public void onClick(View view) {
                if (correctAnswer == 4) {
                    //correct
                    answers[currentQuestionNumber] = true;
                }else{
                    //wrong
                    answers[currentQuestionNumber] = false;
                }
                if(currentQuestionNumber < mcqQuestionsAdaper.size() - 1){
                    currentQuestionNumber ++;
                    setQuestion(currentQuestionNumber);
                }
                else {
                    quizended();

                }
            }
        });

        return view;
    }

    public static void setQuestion(int questionIndex){

        question.setText(mcqQuestionsAdaper.get(questionIndex).getQuestionNumber()+ " " + mcqQuestionsAdaper.get(questionIndex).getQuestion() + " ");
        ansA.setText(mcqQuestionsAdaper.get(questionIndex).getAnswerA());
        ansB.setText(mcqQuestionsAdaper.get(questionIndex).getAnswerB());
        ansC.setText(mcqQuestionsAdaper.get(questionIndex).getAnswerC());
        ansD.setText(mcqQuestionsAdaper.get(questionIndex).getAnswerD());
        correctAnswer = mcqQuestionsAdaper.get(questionIndex).getCorrectAnswer();
    }
    public static  void setAnswerarray(){
        answers = new boolean[mcqQuestionsAdaper.size()];
    }
    void quizended() {
        quizEnded quizEnded = new quizEnded();
        quizEnded.answers = answers;
        mcqFragment.setfragment(quizEnded);
    }
    private void down() {
        // final Downloader d = new Downloader(getContext(), url, lv);
        final quizDownloader d = new quizDownloader(getActivity(), url);
        fragmentManager = getChildFragmentManager();
        d.execute();
        Log.i("Info", "willdownload");
    }

   //////////////////////////////////////////////
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
