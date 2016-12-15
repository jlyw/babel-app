package com.babel.mybabelapplication.network;

import android.content.Context;
import android.os.AsyncTask;

import com.babel.mybabelapplication.model.Verb;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Lucas on 14/06/2016.
 */
public class JsonTaskVerbSingle extends AsyncTask<String, String, Verb> {

    Context context;
    Verb verb;

    public JsonTaskVerbSingle(Context context, Verb verb) {
        this.context = context;
        this.verb = verb;
    }

    @Override
    protected Verb doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            // Store the Json we got
            String finalJson = buffer.toString();
            // make a JSONObject
            JSONObject parentObject = new JSONObject(finalJson);

            verb.setInfinitive(parentObject.getString("verb"));
            verb.setSimplePast(parentObject.getJSONObject("tenses").getJSONObject("2").getJSONArray("forms").getJSONObject(0).getString("form"));
            verb.setPastParticiple(parentObject.getJSONObject("tenses").getJSONObject("22").getJSONArray("forms").getJSONObject(0).getString("form"));
            return verb;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            // if there is a connection, disconnect
            if(connection != null) {
                connection.disconnect();
            }
            try {
                // if there is a reader, close it
                if(reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Verb result) {
        super.onPostExecute(result);
    }
}