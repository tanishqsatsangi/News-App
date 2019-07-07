package com.android.udacity.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.viewHolder> {
    Context context;
    ArrayList<NewsModel> data = new ArrayList<>();

    public FeedAdapter() {
        //empty constructor
    }

    public FeedAdapter(Context context, ArrayList<NewsModel> array) {
        this.context = context;
        data = array;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.news_layout, viewGroup, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {

        final NewsModel currentpos = data.get(i);
        viewHolder.sectionname.setText(currentpos.getSectionName());
        viewHolder.feedtype.setText(currentpos.getFeedtype());
        viewHolder.webtitle.setText(currentpos.getNewstitle());
        viewHolder.pillartype.setText(currentpos.getPillartype());
        viewHolder.date.setText(currentpos.getDate());
        viewHolder.author.setText(currentpos.getAuthor());
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri newsUri = Uri.parse(currentpos.getWeburl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                context.startActivity(websiteIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        TextView webtitle, date, pillartype, feedtype, sectionname,author;
        RelativeLayout layout;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            webtitle = itemView.findViewById(R.id.newstitle);
            date = itemView.findViewById(R.id.Date);
            pillartype = itemView.findViewById(R.id.pillartype);
            feedtype = itemView.findViewById(R.id.feedtype);
            sectionname = itemView.findViewById(R.id.sectionname);
            layout = itemView.findViewById(R.id.layoutview);
            author=itemView.findViewById(R.id.author);
        }
    }
}
