package com.example.kiran.instagram;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;



public class MainActivity extends AppCompatActivity {

    private ProgressDialog pDialog;

    ArrayList<Picture> pics;
    PicturesAdapter picsAdapter;
    ListView lvPics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the list view
        lvPics = (ListView) findViewById(R.id.lvItems);

        AsyncTask myTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object... params) {

                InstagramRestClient.get("media/popular?client_id=e05c462ebd86446ea48a5af73769b602", null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.d("Success: ", "> >>>>>>>>>>>>" );
                        // If the response is JSONObject instead of expected JSONArray
                        JSONArray  data = null;
                        //init the items ArrayList
                        pics = new ArrayList<>();
                        try {
                            data = response.getJSONArray("data");
                            int count = data.length();
                            Picture pic;
                            for(int i = 0; i< count; i++) {
                                Log.d("Response: ", "> " + i);
                                JSONObject popularPhoto = data.getJSONObject(i);
                                JSONObject user = (JSONObject)popularPhoto.get("user");
                                JSONObject caption = (JSONObject)popularPhoto.get("caption");
                                JSONObject likes = (JSONObject)popularPhoto.get("likes");

                                JSONObject images = (JSONObject)popularPhoto.get("images");
                                JSONObject standardImage =  (JSONObject)images.get("standard_resolution");




                                pic = new Picture(standardImage.getString("url"),
                                        user.getString("username"),
                                        caption.getString("text"),
                                        likes.getString("count"));
                                pics.add(pic);
                                // Log.i("Photo", popularPhoto.get("user").toString());
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                });
                return null;
            } //end of doInBackground

            @Override
            protected void onPostExecute(Object result) {
                super.onPostExecute(result);

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // HERE SET YOUR VIEWS
                        picsAdapter = new PicturesAdapter(getApplicationContext(), pics);
                        lvPics.setAdapter(picsAdapter);
                    }
                });
            } //end of onPostExecute
        }; //end of AsyncTask

        myTask.execute();
    }

}
