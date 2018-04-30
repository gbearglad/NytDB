package com.example.a0504gicarlson.nytimesreader.model;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 0504gicarlson on 23/04/2018.
 */

public class HttpRequestService extends IntentService {
 static String EXTRA_URLSTRING = "123456";

    public HttpRequestService() {
        super("HttpRequestService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String url = intent.getStringExtra(EXTRA_URLSTRING);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseString = new String(response.body().string());
            Intent completeIntent = new Intent("httpRequestComplete");
            completeIntent.putExtra("responseString", responseString);
            Log.d("TAG",responseString);
            sendBroadcast(completeIntent);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void startActionRequestHttp(Context context, String url){
        Intent intent = new Intent(context, HttpRequestService.class);
        intent.putExtra(EXTRA_URLSTRING,url);
        context.startService(intent);


    }
}
