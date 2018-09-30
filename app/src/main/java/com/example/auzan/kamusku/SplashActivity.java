package com.example.auzan.kamusku;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.auzan.kamusku.Database.KamusHelper;
import com.rey.material.widget.ProgressView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000;
//    ProgressView pv;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        pv = findViewById(R.id.progress_circular);
        pb = findViewById(R.id.progress_bar);

        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadData.class.getSimpleName();
        KamusHelper kamusHelper;
        AppPreference appPreference;
        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {
            kamusHelper = new KamusHelper(SplashActivity.this);
            appPreference = new AppPreference(SplashActivity.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            Boolean firsRun = appPreference.getFirstRun();

            if (firsRun) {

                ArrayList<Kamus> kamusIndo = preLoadRaw(R.raw.indonesia_english);
//                ArrayList<Kamus> kamusEng = preLoadRaw(R.raw.english_indonesia);
                kamusHelper.open();

                progress = 30;
                publishProgress((int) progress);
                Double progressMaxInsert = 80.0;
                Double progressDiff = (progressMaxInsert - progress) / kamusIndo.size();
                kamusHelper.beginTransaction();

                try {
                    for (Kamus model1 : kamusIndo) {
                        kamusHelper.insertTransaction(model1);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }

//                    for (Kamus model2 : kamusEng) {
//                        kamusHelper.insertTransaction(model2);
//                        progress += progressDiff;
//                        publishProgress((int) progress);
//                    }

                    kamusHelper.setTransactionSuccess();
                } catch (Exception e) {
                    Log.e(TAG, "doInBackground: Exception");
                }

                kamusHelper.endTransaction();
                kamusHelper.close();

                appPreference.setFirstRun(false);

                publishProgress((int) maxprogress);

            } else {
                try {
                    synchronized (this) {
                        this.wait(2000);

                        publishProgress(50);

                        this.wait(2000);
                        publishProgress((int) maxprogress);
                    }
                } catch (Exception e) {
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            pb.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    public ArrayList<Kamus> preLoadRaw(int raw) {
        ArrayList<Kamus> kamus = new ArrayList<>();
        String line;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(raw);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                Kamus wordses;

                wordses = new Kamus(splitstr[0], splitstr[1]);
                kamus.add(wordses);
                count++;
            } while (line != null);
        } catch (Exception e) {
        }
        return kamus;
    }
}
