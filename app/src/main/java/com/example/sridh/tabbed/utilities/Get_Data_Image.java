package com.example.sridh.tabbed.utilities;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.sridh.tabbed.MainActivity;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Get_Data_Image extends AsyncTask<String,Integer,Images_List> {
    public AsyncResponse response;
    MainActivity context;
    String distance;
    public Get_Data_Image(MainActivity context, AsyncResponse asyncResponse) {
        this.response = asyncResponse;
        this.context = context;
        this.distance = distance;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Images_List images_list) {
        super.onPostExecute(images_list);
        response.processFinish(images_list);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Images_List doInBackground(String... strings) {
        URL url = null;
        try {
            url = new URL(strings[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            Log.d("URL Image",url.toString());
            con.setRequestProperty("Content-Type", "application/json");
            con.connect();
            int status = con.getResponseCode();

            if(status == HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();
                while (line != null){
                    sb.append(line);
                    line = reader.readLine();
                }
                return SeasonUtil.SeasonJSONParser.parseSeasonImage(sb.toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public interface AsyncResponse {
        void processFinish(Images_List images_list);
    }
}
