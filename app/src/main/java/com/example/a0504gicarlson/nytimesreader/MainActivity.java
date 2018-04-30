package com.example.a0504gicarlson.nytimesreader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.a0504gicarlson.nytimesreader.model.Article;
import com.example.a0504gicarlson.nytimesreader.model.HttpRequestService;
import com.example.a0504gicarlson.nytimesreader.model.SavedArticlesManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String nytUrl = "https://api.nytimes.com/svc/topstories/v2/technology.json?api-key=88111103e9a649df9be4e7f7145e0f2d";
    NytArticlesAdapter nytArticlesAdapter;

    private class HttpReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("httpRequestComplete")) {
                String response = intent.getStringExtra("responseString");
                ArrayList<Article> articles = parseTopStoriesResponse(response);
                nytArticlesAdapter.setArticleArrayList(articles);
                SavedArticlesManager.getInstance().saveArticles(articles);
                Log.d("SecondTag", response);
            }
            else if (intent.getAction().equalsIgnoreCase("httpRequestFailed")){
                ArrayList<Article> articles = SavedArticlesManager.getInstance().getAllArticles();
                nytArticlesAdapter.setArticleArrayList(articles);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SavedArticlesManager.getInstance().initWithContext(getApplicationContext());
        setContentView(R.layout.activity_main);
        HttpRequestService.startActionRequestHttp(this, nytUrl);
        HttpReceiver httpReceiver = new HttpReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("httpRequestComplete");
        intentFilter.addAction("httpRequestFailed");
        registerReceiver(httpReceiver, intentFilter);


        nytArticlesAdapter = new NytArticlesAdapter();
        RecyclerView articleRecyclerView = findViewById(R.id.articleRecyclerView);
        articleRecyclerView.setAdapter(nytArticlesAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        articleRecyclerView.setLayoutManager(linearLayoutManager);


    }


    private ArrayList<Article> parseTopStoriesResponse(String jsonString) {
        ArrayList<Article> articles = new ArrayList<Article>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject jsonArticle = jsonArray.getJSONObject(i);
                String title = jsonArticle.getString("title");
                String articleAbstract = jsonArticle.getString("abstract");
                JSONArray multimedia = jsonArticle.getJSONArray("multimedia");
                JSONObject firstMultimedia = multimedia.getJSONObject(0);
                String imageURL = firstMultimedia.getString("url");
                Article newArticle = new Article(title, articleAbstract);
                newArticle.setImageURL(imageURL);
                articles.add(newArticle);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return articles;
    }
}
