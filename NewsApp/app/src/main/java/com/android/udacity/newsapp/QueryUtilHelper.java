package com.android.udacity.newsapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public final class QueryUtilHelper {

    QueryUtilHelper() {
        //empty constructor
    }

    static URL generateurl(String st_url) {
        URL url = null;
        try {
            url = new URL(st_url);
        } catch (Exception e) {
            Log.e("url error", "error while changing url to URl", e);
        }

        return url;
    }

    static String readdata(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (is != null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String newLine = br.readLine();
            if (!newLine.isEmpty()) {
                sb.append(newLine);
                newLine = br.readLine();
            }
        }
        return sb.toString();
    }

    public static String datarequestHttp(URL urllink) throws IOException {

        String data = "";
        if (urllink == null) {
            return data;
        }
        HttpURLConnection connect = null;
        InputStream is = null;
        try {
            connect = (HttpURLConnection) urllink.openConnection();
            connect.setRequestMethod("GET");
            connect.connect();
            if (connect.getResponseCode() == 200) {
                is = connect.getInputStream();
                data = readdata(is);
            }
        } catch (Exception e) {
            Log.e("connect error", "error in response code", e);

        }

        if (connect != null) {
            connect.disconnect();
            if (is != null) {
                is.close();
            }
        }

        return data;
    }

    public static ArrayList<NewsModel> getdata(String dataurl) {

        URL url = generateurl(dataurl);
        String data = null;
        try {
            data = datarequestHttp(url);

        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<NewsModel> dataarray = getnews(data);

        return dataarray;

    }

    static ArrayList<NewsModel> getnews(String newsdata) {
        if (newsdata.isEmpty()) {
            return null;

        }

        ArrayList<NewsModel> datarray = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(newsdata);
            JSONObject dataobj = obj.getJSONObject("response");
            JSONArray array = dataobj.getJSONArray("results");
            for (int i = 0; i < array.length(); i++) {
                JSONObject current = array.getJSONObject(i);
                JSONArray tags = current.getJSONArray("tags");
                JSONObject currenttag = tags.getJSONObject(0);
                String author = currenttag.getString("webTitle");

                String webtitle = current.getString("webTitle");
                String type = current.getString("type");
                String sectionName = current.getString("sectionName");
                String date = current.getString("webPublicationDate");
                String weburl = current.getString("webUrl");
                String pillarname = current.getString("pillarName");
                datarray.add(new NewsModel(weburl, webtitle, type, sectionName, date, pillarname, author));

            }
        } catch (Exception e) {
            //handle error for exception
        }
        return datarray;
    }

}
