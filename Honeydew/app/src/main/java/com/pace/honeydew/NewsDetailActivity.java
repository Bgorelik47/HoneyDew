package com.pace.honeydew;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class NewsDetailActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_item_profile);

        //UI id's
        ImageView imageView = findViewById(R.id.poster_image);
        TextView title_news = findViewById(R.id.mTitle);
        TextView description = findViewById(R.id.description);

        //gets
        Bundle bundle = getIntent().getExtras();
        String mTitle = bundle.getString("title");
        String mDescription = bundle.getString("description");
        String mUrlToImage = bundle.getString("urlToImage");

        //images
        Glide.with(this).load(mUrlToImage).into(imageView);
        title_news.setText(mTitle);
        description.setText(mDescription);

    }

}
