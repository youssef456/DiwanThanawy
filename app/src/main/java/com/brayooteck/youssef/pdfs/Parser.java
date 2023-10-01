package com.brayooteck.youssef.pdfs;

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
public class Parser extends AsyncTask<Void,Integer,Integer> {
    public static String name;
    Context c;
    GridView lv;
    public static String data;
    public static ArrayList<arrayadapter> players = new ArrayList<arrayadapter>();
    ProgressDialog pd;
    boolean booly;
    public Parser(Context c, String data,GridView lv) {
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


            list adapter= new list(c, R.layout.adapter, players);
            lv.setAdapter(adapter);
        }else
        {
            Toast.makeText(c,"Unable to Parse",Toast.LENGTH_SHORT).show();
        }

        pd.dismiss();
    }





    private int parse()
    {
        try
        {

            JSONArray ja=new JSONArray(data);

            JSONObject jo=null;

            players.clear();

            for(int i=0;i<ja.length();i++) {
                jo = ja.getJSONObject(i);

                name = jo.getString("name");
                String url = jo.getString("url");
                int size = (int) jo.getInt("size");
                int ansp = jo.getInt("ansp");
                int value = jo.getInt("value");
                if (value == 1) {
                    booly = true;
                } else {
                    booly = false;
                }





                    players.add(new arrayadapter(name, url, size, ansp, booly));

                Log.i("Info", "doneee");

            }
            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }

}

