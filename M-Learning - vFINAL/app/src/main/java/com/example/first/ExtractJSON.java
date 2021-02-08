package com.example.first;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ExtractJSON extends AsyncTask<URL, Void, String> {

    //pt fiecare nod
    JSONArray cursFinalizat1 = null;
    JSONArray cursFinalizat2 = null;
    JSONArray cursFinalizat3 = null;
    public static List<CursFinalizat> listaCursuriFinalizate = new ArrayList<>();


    @Override
    protected String doInBackground(URL... urls) {
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) urls[0].openConnection();
            connection.setRequestMethod("GET");
            InputStream ist = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(ist);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            StringBuilder sbuf = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sbuf.append(line);
            }

            loadJSON(sbuf.toString());

            return sbuf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Cannot reach resource '" + urls[0] + "'");
    }

    public void loadJSON(String jsonStr) {
        if (jsonStr != null) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(jsonStr);
            } catch (JSONException e) {
                throw new RuntimeException("JSON is invalid");
            }

            try {
                cursFinalizat1 = jsonObject.getJSONArray("user");

                for (int i = 0; i < cursFinalizat1.length(); i++) {
                    JSONObject c = cursFinalizat1.getJSONObject(i);

                    int id = Integer.parseInt(c.getString("id"));
                    String email = c.getString("email");
                    int nrCursuriFinalizate =  Integer.parseInt(c.getString("nrCursuriFinalizate"));


                    cursFinalizat2 = c.getJSONArray("cursant");

                    for (int j = 0; j < cursFinalizat2.length(); j++) {
                        c = cursFinalizat2.getJSONObject(j);

                        String numePrenume= c.getString("numePrenume");
                        int varsta = Integer.parseInt(c.getString("varsta"));
                        String gen = c.getString("gen");

                        cursFinalizat3 = c.getJSONArray("curs");

                        for (int k = 0; k < cursFinalizat3.length(); k++) {
                            c = cursFinalizat3.getJSONObject(k);

                            String numeCurs = c.getString("numeCurs");
                            String dificultate = c.getString("dificultate");
                            String notita = c.getString("notita");

                            CursFinalizat cursFinalizat3 = new CursFinalizat(id, email, nrCursuriFinalizate, numePrenume, varsta, gen, numeCurs, dificultate, notita);

                            listaCursuriFinalizate.add(cursFinalizat3);
                        }

                    }
                }
            } catch (JSONException e) {
                throw new RuntimeException("JSON is invalid");
            }
        } else throw new RuntimeException("JSON is invalid");
    }
}
