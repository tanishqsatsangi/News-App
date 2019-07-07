package com.android.udacity.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

public class FeedLoader extends AsyncTaskLoader<ArrayList<NewsModel>> {

    String url;

    public FeedLoader(Context context, String weburl) {
        super(context);
        url = weburl;

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<NewsModel> loadInBackground() {
        if (url == null) {
            return null;
        } else {

            ArrayList<NewsModel> data = QueryUtilHelper.getdata(url);
            return data;
        }
    }
}
