package com.pace.honeydew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

public class Search extends AppCompatActivity {

    private ListView charity_list_example;
    private ArrayAdapter<String> adapter;
    private SearchView search;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Initialize and Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationVIew);

        //Set Search Selected
        bottomNavigationView.setSelectedItemId(R.id.Search);

        //search
        charity_list_example = (ListView) findViewById(R.id.results);
        search = findViewById(R.id.search_bar);
        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

//        //example array
//        ArrayList<String> charity_names = new ArrayList<>();
//        charity_names.addAll(Arrays.asList(getResources().getStringArray(R.array.charity_list_ex)));

        //news array
        ArrayList<String> charities = new ArrayList<>();
        fetchFromJSON(charities);

        adapter = new ArrayAdapter<String>(
                Search.this,
                android.R.layout.simple_list_item_1,
                charities
        ){
            public View getView(int position, View converterView, ViewGroup parent)
            {
                TextView item = (TextView) super.getView(position,converterView,parent);
                item.setTextColor(Color.parseColor("#1D423B"));
                return item;
            }
        };
        charity_list_example.setAdapter(adapter);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                search.clearFocus();
                if(charities.contains(s))
                {
                    adapter.getFilter().filter(s);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
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
                        startActivity(new Intent(getApplicationContext(), News.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Search:
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

    private void fetchFromJSON(ArrayList<String> charities)
    {
        String url = "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray articles = response.getJSONArray("articles");
                for (int i = 0; i < articles.length(); i++) {
                    JSONObject article = articles.getJSONObject(i);

                    charities.add(article.getString("title"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Search.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }
    }