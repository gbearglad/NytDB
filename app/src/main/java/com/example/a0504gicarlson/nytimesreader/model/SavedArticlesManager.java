package com.example.a0504gicarlson.nytimesreader.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 0504gicarlson on 30/04/2018.
 */

public class SavedArticlesManager {
    private static final SavedArticlesManager ourInstance = new SavedArticlesManager();
    ArticleDatabaseHelper articleDatabaseHelper;

    public void initWithContext(Context context) {
        if (articleDatabaseHelper == null) {
            articleDatabaseHelper = new ArticleDatabaseHelper(context);
            articleDatabaseHelper.addArticle("Fake Title", "Fake Abstract");
        }
    }

    public static SavedArticlesManager getInstance() {
        return ourInstance;
    }

    private class ArticleDatabaseHelper extends SQLiteOpenHelper {
        private static final String DB_NAME = "NYTimesReaderDB";
        private static final int DB_VERSION = 1;

        ArticleDatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String sqlInstruction = "CREATE TABLE ARTICLES (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "TITLE TEXT," +
                    "ABSTRACT TEXT)";
            sqLiteDatabase.execSQL(sqlInstruction);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        }

        public void addArticle(String title, String articleAbstract) {
            ContentValues articleValues = new ContentValues();
            articleValues.put("TITLE", title);
            articleValues.put("ABSTRACT", articleAbstract);
            getWritableDatabase().insert("ARTICLES", null, articleValues);

        }

        public void clearArticles() {
            String clearDBQuery = "DELETE FROM ARTICLES";
            getWritableDatabase().execSQL(clearDBQuery);
        }
    }

    public void saveArticles(List<Article> articles) {
        articleDatabaseHelper.clearArticles();
        for (Article article : articles) {
            articleDatabaseHelper.addArticle(article.title, article.articleAbstract);
        }

    }

    public ArrayList<Article> getAllArticles() {
        Cursor cursor = articleDatabaseHelper.getReadableDatabase().query("ARTICLES", new String[]{"_id", "TITLE", "ABSTRACT"}, null, null, null, null, null);
        ArrayList<Article> articles = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            if (cursor.moveToNext()) {
                String title = cursor.getString(1);
                String articleAbstract = cursor.getString(2);
                Article article = new Article(title, articleAbstract);
                articles.add(article);
            }
        }
        cursor.close();
        return articles;
    }
}
