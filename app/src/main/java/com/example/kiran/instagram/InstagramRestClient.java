package com.example.kiran.instagram;

import android.os.Looper;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

/**
 * Created by kiran on 12/1/15.
 */
public class InstagramRestClient {

    private static final String BASE_URL = "https://api.instagram.com/v1/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    // A SyncHttpClient is an AsyncHttpClient
    private static AsyncHttpClient syncHttpClient= new SyncHttpClient();
    private static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        getClient().get(getAbsoluteUrl(url), responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        getClient().post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    /**
     * @return an async client when calling from the main thread, otherwise a sync client.
     */
    private static AsyncHttpClient getClient()
    {
        // Return the synchronous HTTP client when the thread is not prepared
        if (Looper.myLooper() == null)
            return syncHttpClient;
        return asyncHttpClient;
    }
}
