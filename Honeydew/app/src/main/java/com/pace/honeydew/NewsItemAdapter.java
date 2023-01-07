package com.pace.honeydew;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.time.Instant;
import java.util.List;

public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.NewsHolder> {

    private Context context;
    private List<NewsItem> newsItemList;

    public NewsItemAdapter(Context context, List<NewsItem> newsItemList){
        this.context = context;
        this.newsItemList = newsItemList;
    }

    //layout
    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false );
        return new NewsHolder(view);


    }

    //makes each "item"
    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        NewsItem news = newsItemList.get(position);
        holder.title.setText(news.getTitle());
        holder.description.setText(news.getDescription());
        Glide.with(context).load(news.getUrlToImage()).into(holder.imageView);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , NewsDetailActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("title" , news.getTitle());
                bundle.putString("description" , news.getDescription());
                bundle.putString("urlToImage" , news.getUrlToImage());


                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return newsItemList.size();
    }

    //makes recycler view
    public class NewsHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title, description;
        ConstraintLayout constraintLayout;

        public NewsHolder(@NonNull View itemView) {

            super(itemView);

            imageView = itemView.findViewById(R.id.imageview);
            title = itemView.findViewById(R.id.title_news);
            description = itemView.findViewById(R.id.description);
            constraintLayout = itemView.findViewById(R.id.news_item);

        }
    }
}
