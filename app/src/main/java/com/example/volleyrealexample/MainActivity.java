package com.example.volleyrealexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String url = "https://api.github.com/users/mralexgray/repos";
    RecyclerView recyclerView;
    RequestQueue requestQueue;
    List<GitObject> list;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = Singleton.getInstance(this).getRequestQueue();
        list = new ArrayList<>();
        adapter = new Adapter(list,this);
        recyclerView.setAdapter(adapter);


        extractUrl();

    }

    private void extractUrl() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i=0;i<response.length();i++) {

                JSONObject jsonObject = response.getJSONObject(i);
                GitObject gitObject = new GitObject();
                gitObject.setFull_name(jsonObject.getString("full_name"));
                gitObject.setLicense(jsonObject.getString("license"));
                gitObject.setAvatar_url(jsonObject.getJSONObject("owner").getString("avatar_url"));
                gitObject.setSize(jsonObject.getString("size"));
                gitObject.setId(jsonObject.getString("id"));
                list.add(gitObject);
            }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter = new Adapter(list,MainActivity.this);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error:",error.getMessage());

            }
        });


        requestQueue.add(jsonArrayRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;

            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //on item selected - switch
        switch (item.getItemId()){
            case R.id.sortaz:
                Collections.sort(list,GitObject.comparatorAz);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.sortza:
                Collections.sort(list,GitObject.comparatorZa);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.sortSize:
                Collections.sort(list,GitObject.comparatorSize);
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}