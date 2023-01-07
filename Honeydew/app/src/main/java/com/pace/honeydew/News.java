package com.pace.honeydew;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class News extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<NewsItem> newsItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();
        newsItemList = new ArrayList<>();

        fetchNews();
        //Initialize and Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationVIew);

        //Set News Selected
        bottomNavigationView.setSelectedItemId(R.id.News);

        //Perform ItemsSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.News:
                        return true;
                    case R.id.Search:
                        startActivity(new Intent(getApplicationContext(), Search.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    //BETSY"S FETCH NEWS
    private void fetchNews() {
//        String url = "https://newsapi.org/v2/everything?q=charity&sortBy=relevancy&apiKey=00a8b39145f54269a321b78fad3d22c1";
        String url = "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
                try {
                    JSONArray articles = response.getJSONArray("articles");
                    for (int i = 0; i < articles.length(); i++) {
                        JSONObject article = articles.getJSONObject(i);
                        String title = article.getString("title");
                        String description = article.getString("description");
                        String urlToImage = article.getString("urlToImage");

                        NewsItem newsItem = new NewsItem(title, urlToImage, description);
                          newsItemList.add(newsItem);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                NewsItemAdapter adapter = new NewsItemAdapter(News.this, newsItemList);
                recyclerView.setAdapter(adapter);
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(News.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);
    }
}