package com.android.udacity.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<NewsModel>> {

    TextView emptytext;
    private static final String Guardian_Api_query = "http://content.guardianapis.com/search?";
    RecyclerView newsfeedview;
    public FeedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptytext = findViewById(R.id.emptytextview);
        newsfeedview = findViewById(R.id.newsfeedview);
        newsfeedview.setHasFixedSize(true);
        adapter = new FeedAdapter(this, new ArrayList<NewsModel>());
        newsfeedview.setLayoutManager(new LinearLayoutManager(this));
        newsfeedview.setAdapter(adapter);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nw = cm.getActiveNetworkInfo();
        if (nw != null && nw.isConnected()) {

            LoaderManager lm = getLoaderManager();
            lm.initLoader(1, null, this);
        } else {
            //to display no data available if not connected
            emptytext.setText("No network list is empty");
        }
    }

    @Override
    public Loader<ArrayList<NewsModel>> onCreateLoader(int id, Bundle args) {

        Uri baseUri = Uri.parse(Guardian_Api_query);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameter and its value. For example, the `format=json`
        uriBuilder.appendQueryParameter("api-key", "test");
        uriBuilder.appendQueryParameter("format", "json");
        uriBuilder.appendQueryParameter("order-by", "newest");
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        //log to check the wuery being passed on
        Log.i("query url", uriBuilder.toString(), null);
        return new FeedLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<NewsModel>> loader, ArrayList<NewsModel> data) {
        if (data != null && !data.isEmpty()) {
            adapter = new FeedAdapter(getApplicationContext(), data);
            newsfeedview.setAdapter(adapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<NewsModel>> loader) {

    }

    class theTask extends AsyncTask<String, Void, ArrayList<NewsModel>> {

        @Override
        protected ArrayList<NewsModel> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;

            } else {
                return QueryUtilHelper.getdata(urls[0]);
            }
        }

        @Override
        protected void onPostExecute(ArrayList<NewsModel> newsModels) {
            if (newsModels != null && !newsModels.isEmpty()) {
                adapter = new FeedAdapter(getApplicationContext(), newsModels);
                newsfeedview.setAdapter(adapter);
            } else {
                emptytext.setText("no news available");
            }

        }

        //class end thetask
    }

}
