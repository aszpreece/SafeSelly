package com.example.tomasz.samplejson;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void getCrimes(View v) {
        new LongOperation().execute("");

    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        private String res = "";

        @Override
        protected String doInBackground(String... params) {
            String cats = "";
            try {
                JSONArray crimes = readJsonFromUrl("https://data.police.uk/api/crimes-street/all-crime?poly=52.442910,-1.953296:52.434101,-1.911356:52.450602,-1.920676");

                for (int i = 0; i < crimes.length(); i++) {
                    cats += crimes.getJSONObject(i).getString("category") + " ";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            res = cats;

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            TextView txt = findViewById(R.id.text_view);
            txt.setText("Executed " + res);
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }


    public static JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONArray json = new JSONArray(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }


}
