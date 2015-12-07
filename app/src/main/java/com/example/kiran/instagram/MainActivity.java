package com.example.kiran.instagram;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

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
        initImageLoader(getApplicationContext());

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

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }


}
