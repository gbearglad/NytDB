package com.example.a0504gicarlson.nytimesreader.model;

/**
 * Created by 0504gicarlson on 23/04/2018.
 */

public class Article {
    String title;
    String articleAbstract;
    String imageURL;

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


    public String getImageURL() {
        return imageURL;
    }

    public String getTitle() {
        return title;
    }

    public String getArticleAbstract() {
        return articleAbstract;
    }

    public Article(String title, String articleAbstract) {
        this.title = title;
        this.articleAbstract = articleAbstract;
    }
}
