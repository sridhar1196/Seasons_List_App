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

import static com.example.sridh.tabbed.MainActivity.TVDB_TOKEN;

public class Get_Data_Show_Summary  extends AsyncTask<String,Integer,ShowDetails> {

    public AsyncResponse response;
    Context context;
    ShowDetails showDetails;
    String distance;
    public Get_Data_Show_Summary(Context context,ShowDetails showDetails, AsyncResponse asyncResponse) {
        this.response = asyncResponse;
        this.context = context;
        this.showDetails = showDetails;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ShowDetails showDetails) {
        super.onPostExecute(showDetails);
        response.processFinish(showDetails);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }




    @Override
    protected ShowDetails doInBackground(String... strings) {
        URL url = null;
        try {
            url = new URL(strings[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            Log.d("URL",url.toString());
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("trakt-api-version", "2");
            con.setRequestProperty("trakt-api-key", MainActivity.TRAKT_KEY);
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
                return SeasonUtil.SeasonJSONParser.parseSeasonInfo(sb.toString(),showDetails);
            }
            else {
                Toast.makeText(context,"Error in loading image", Toast.LENGTH_LONG).show();
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
        void processFinish(ShowDetails showDetails);
    }

}
