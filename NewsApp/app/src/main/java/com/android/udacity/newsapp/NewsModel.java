package com.android.udacity.newsapp;

public class NewsModel {
    String weburl;
    String newstitle;
    String feedtype;
    String sectionName;
    String date;
    String author;

    String pillartype;

    public NewsModel() {

    }

    public NewsModel(String weburl, String newstitle, String feedtype, String sectionName, String date, String pillartype, String author) {
        this.weburl = weburl;
        this.author = author;
        this.newstitle = newstitle;
        this.feedtype = feedtype;
        this.sectionName = sectionName;
        this.date = date;
        this.pillartype = pillartype;
    }

    public String getAuthor() {
        return author;
    }

    public String getWeburl() {
        return weburl;
    }

    public String getNewstitle() {
        return newstitle;
    }

    public String getFeedtype() {
        return feedtype;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getDate() {
        return date;
    }

    public String getPillartype() {
        return pillartype;
    }


}
