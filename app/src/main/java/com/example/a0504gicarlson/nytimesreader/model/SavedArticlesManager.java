package com.example.a0504gicarlson.nytimesreader.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 0504gicarlson on 30/04/2018.
 */

public class SavedArticlesManager {
    private static final SavedArticlesManager ourInstance = new SavedArticlesManager();
    ArticleDatabaseHelper articleDatabaseHelper;
    public void initWithContext(Context context){
        if (articleDatabaseHelper == null){
            articleDatabaseHelper = new ArticleDatabaseHelper(context);
            articleDatabaseHelper.addArticle("Fake Title","Fake Abstract");
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
        public void addArticle(String title, String articleAbstract){
            ContentValues articleValues = new ContentValues();
            articleValues.put("TITLE", title);
            articleValues.put("ABSTRACT", articleAbstract);
            getWritableDatabase().insert("ARTICLES",null,articleValues);

        }
        public void clearArticles(){
            String clearDBQuery = "DELETE FROM ARTICLES";
            getWritableDatabase().execSQL(clearDBQuery);
        }
    }

    private SavedArticlesManager() {
    }
}
