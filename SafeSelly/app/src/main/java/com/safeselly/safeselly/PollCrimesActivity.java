package com.safeselly.safeselly;

import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class PollCrimesActivity extends AsyncTask<String, Void, String> {

    ArrayList<Crime> crimeList = new ArrayList<Crime>();
    CrimeAdapter adapter;


    public PollCrimesActivity(CrimeAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            JSONArray crimes = readJsonFromUrl("https://data.police.uk/api/crimes-street/all-crime?poly=52.442910,-1.953296:52.434101,-1.911356:52.450602,-1.920676");

            for (int i = 0; i < crimes.length(); i++) {
                JSONObject crime = crimes.getJSONObject(i);
                String cat = crime.getString("category");

                JSONObject location = crime.getJSONObject("location");

                double lng = location.getDouble("longitude");

                double lat = location.getDouble("latitude");

                String locdec = location.getJSONObject("street").getString("name");

                String date = crime.getString("month");

                int id = crime.getInt("id");


                crimeList.add(new Crime(cat, locdec, date, lat, lng, id));
            }

            Collections.sort(crimeList, new Comparator<Crime>() {
                @Override
                public int compare(Crime o1, Crime o2) {
                    return Integer.compare(o1.getId(), o2.getId());
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        adapter.clear();
        adapter.addAll(crimeList);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
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
