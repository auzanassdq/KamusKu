package com.example.auzan.kamusku;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.auzan.kamusku.Database.KamusHelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SplashActivity extends Activity {

    boolean isIndonesia = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadData.class.getSimpleName();
        KamusHelper kamusHelper;
        AppPreference appPreference;

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
                ArrayList<Kamus> kamusEng = preLoadRaw(R.raw.english_indonesia);
                kamusHelper.open();

                kamusHelper.beginTransaction();
                try {
                    for (Kamus model : kamusIndo) {
                        kamusHelper.insertTransaction(model, isIndonesia);
                    }

                    for (Kamus model : kamusEng) {
                        kamusHelper.insertTransaction(model, false);
                    }
                    kamusHelper.setTransactionSuccess();
                } catch (Exception e) {
                    Log.e(TAG, "doInBackground: Exception");
                }
                kamusHelper.endTransaction();

                kamusHelper.close();
                appPreference.setFirstRun(false);

            }
            return null;
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
