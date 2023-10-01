package com.brayooteck.youssef.mcq;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.brayooteck.youssef.R;
import com.brayooteck.youssef.mymainactivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Hp on 3/24/2016.
 */
public class mcqParser extends AsyncTask<Void,Integer,Integer> {
    public static String name;
    Context c;
    GridView lv;
    public static String data;
    public static ArrayList<mcqQuizAdapter> mcqQuizAdapter = new ArrayList<mcqQuizAdapter>();
    ProgressDialog pd;

    public mcqParser(Context c, String data,GridView lv) {
        this.c = c;
        this.data = data;
        this.lv = lv;
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


            mcqList adapter= new mcqList(c, R.layout.adapter, mcqQuizAdapter);
            lv.setAdapter(adapter);
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

            mcqQuizAdapter.clear();

            Log.i("Info", "parsing");

            for(int i=0;i<ja.length();i++) {
                jo = ja.getJSONObject(i);

                int quiznumber = jo.getInt("quiznumber");
                String quizname = jo.getString("quizname");
                String quizLink = jo.getString("quizLink");

                mcqQuizAdapter.add(new mcqQuizAdapter(quiznumber,quizname,quizLink));

                Log.i("Info", "doneee");

            }
            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }

}

