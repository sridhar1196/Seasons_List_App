package com.example.sridh.tabbed.utilities;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Get_Data_TVDB_Login extends AsyncTask<String,Integer,String> {
    public AsyncResponse response;
    Context context;
    Response responseVal;
    public Get_Data_TVDB_Login(Context context, AsyncResponse asyncResponse) {
        this.response = asyncResponse;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String token) {
        super.onPostExecute(token);
        response.processFinish(token);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }


    @Override
    protected String doInBackground(String... strings) {
        URL url = null;
        String token = "";
        try {
            url = new URL(strings[0]);

            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n  \"apikey\": \"ECE04396F2545DE2\"\n}");
            Request request = new Request.Builder()
                    .url("https://api.thetvdb.com/login")
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .addHeader("accept", "application/json")
                    .build();

            Log.d("request", String.valueOf(request));
            try {
                responseVal = client.newCall(request).execute();

                token =  SeasonUtil.SeasonJSONParser.parseTVDB_Login(responseVal.body().string());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return token;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface AsyncResponse {
        void processFinish(String token);
    }

}
