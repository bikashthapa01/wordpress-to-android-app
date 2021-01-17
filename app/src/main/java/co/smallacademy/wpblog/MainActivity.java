package co.smallacademy.wpblog;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    RecyclerView post_list;
    List<Post> posts;
    PostsAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        posts = new ArrayList<>();

        drawer = findViewById(R.id.drawer);
        post_list = findViewById(R.id.post_list_);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        navigationView = findViewById(R.id.nav_view);
        toggle.setDrawerIndicatorEnabled(true);//enable hamburger sign
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        extractPosts(getResources().getString(R.string.url));
        GridLayoutManager manager = new GridLayoutManager(this,2);
        post_list.setLayoutManager(manager);
        adapter = new PostsAdapter(posts);
        post_list.setAdapter(adapter);




    }

    public void extractPosts(String URL){
        // use volley to extract the data

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "onResponse: " + response.toString());
                for(int i = 0; i < response.length();i++){

                    try {
                        Post p = new Post();

                        JSONObject jsonObjectData = response.getJSONObject(i);

                        // extract the date
                        p.setDate(jsonObjectData.getString("date"));

                        // extract the title
                        JSONObject titleObject = jsonObjectData.getJSONObject("title");
                        p.setTitle(titleObject.getString("rendered"));

                        // extract the content
                        JSONObject contentObject = jsonObjectData.getJSONObject("content");
                        p.setContent(contentObject.getString("rendered"));

                        //extract the excerpt
                        JSONObject excerptObject = jsonObjectData.getJSONObject("excerpt");
                        p.setExcerpt(excerptObject.getString("rendered"));

                        // extract feature image
                        p.setFeature_image(jsonObjectData.getString("jetpack_featured_media_url"));

                        posts.add(p);
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);




    }
}