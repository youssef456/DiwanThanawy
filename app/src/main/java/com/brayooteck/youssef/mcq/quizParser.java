package com.brayooteck.youssef.mcq;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.brayooteck.youssef.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Hp on 3/24/2016.
 */
public class quizParser extends AsyncTask<Void,Integer,Integer> {
    public static String name;
    Context c;
    public static String data;
    public static ArrayList<mcqArrayAdapter> mcqQuestionsAdaper = new ArrayList<mcqArrayAdapter>();
    ProgressDialog pd;

    public quizParser(Context c, String data) {
        this.c = c;
        this.data = data;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd=new ProgressDialog(c);
        pd.setTitle("Parser");
        pd.setMessage("Parsing ....Please wait");
        pd.show();

    }

    @Override
    protected Integer doInBackground(Void... params) {

        return this.parse();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        if(integer == 1)
        {

            Quiz.mcqQuestionsAdaper = mcqQuestionsAdaper;
            Quiz.setQuestion(0);
            Quiz.setAnswerarray();
            Log.i("Info", "Parse");

        }else
        {
            Toast.makeText(c,"Unable to Parse",Toast.LENGTH_SHORT).show();
            Log.i("Info", "Unable to Parse");

        }

        pd.dismiss();
    }


    private int parse()
    {
        Log.i("Info", "parsing" + data);
        try
        {

            JSONArray ja=new JSONArray(data);

            JSONObject jo=null;

            mcqQuestionsAdaper.clear();

            Log.i("Info", "parsingquiz");

            for(int i=0;i<ja.length();i++) {
                jo = ja.getJSONObject(i);

                int QuestionNumber = jo.getInt("questionnumber");
                String Question = jo.getString("question");
                String AnswerA = jo.getString("answerA");
                String AnswerB = jo.getString("answerB");
                String AnswerC = jo.getString("answerC");
                String AnswerD = jo.getString("answerD");
                int CorrectAnswer = jo.getInt("correctAnswer");

                mcqQuestionsAdaper.add(new mcqArrayAdapter(QuestionNumber, Question, AnswerA, AnswerB, AnswerC,AnswerD,CorrectAnswer));
                Log.i("Info", "doneeequiz " + mcqQuestionsAdaper);

                Log.i("Info", "doneeequiz");

            }
            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }

}

