package com.brayooteck.youssef;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class splashscreen extends Activity {
    long cacheExpiration = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        check();
        startHeavyProcessing();

    }

    private void startHeavyProcessing(){
        new LongOperation().execute("");
    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            //some heavy processing resulting in a Data String is was 5 but i changed it to 4 to reduce splash time
            for (int i = 0; i < 2; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
            return "whatever result you have";
        }

        @Override
        protected void onPostExecute(String result) {
            Intent i = new Intent(splashscreen.this, mymainactivity.class);
            i.putExtra("data", result);
            startActivity(i);
            finish();
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
    private void check() {
        final FirebaseRemoteConfig mfirebaseremotecofig = FirebaseRemoteConfig.getInstance();
        mfirebaseremotecofig.setDefaultsAsync(R.xml.remote_config_defaults);

        mfirebaseremotecofig.fetch(cacheExpiration).addOnCompleteListener(new OnCompleteListener<Void>() {
            // @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                   mymainactivity.isauthenabled = mfirebaseremotecofig.getBoolean("authenabled");
                   mymainactivity.ishomeenabled = mfirebaseremotecofig.getBoolean("homeenabledforall");
                    mfirebaseremotecofig.activate();
                    Log.i("Info","valuses"  + mymainactivity.isauthenabled + mymainactivity.ishomeenabled);

                }
                else {
                    Log.i("AppConfig","AppConfig  failed to load");
                }
            }
        });
    }
}