package com.example.coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.coffee.Adapter.Adapter;
import com.example.coffee.Adapter.Viewpager_2;
import com.example.coffee.Models.Models_1;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import com.example.coffee.R;

public class MainActivity extends AppCompatActivity {

//    private boolean isLoading = false;
//    private int currentPage = 1; // Start offset
//    private final int limit = 59; // Number of items per page
//
//    List<Models_1> itemlist;
//
//    RequestQueue requestQueue;
//
//    TextView text1,text2;
//    RecyclerView recyclerView;
//    Adapter adapter;
//
//    SwipeRefreshLayout swipeRefreshLayout;


    ViewPager2 viewPager2;
    BottomNavigationView bottomNavigationView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirstimeStart();
//        text1 = findViewById(R.id.text1);
//        text2= findViewById(R.id.text3);
//        swipeRefreshLayout =findViewById(R.id.refresh);
//
//
//
//
//        int textColor = getResources().getColor(R.color.text_color, getTheme());
//        text1.setTextColor(textColor);
//        text2.setTextColor(textColor);
//        itemlist=new ArrayList<>();
//
//        recyclerView=findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(false);
//        recyclerView.setNestedScrollingEnabled(false);
//        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
//
//         adapter = new Adapter(this,itemlist);
//         recyclerView.setAdapter(adapter);
//        //
//         requestQueue = Volley.newRequestQueue(this);
//
//        setupPagination();
//        fetchProducts();

//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//
//                isLoading=false;
//
//                itemlist.clear();
//                setupPagination();
//                fetchProducts();
//                adapter.notifyDataSetChanged();
//                swipeRefreshLayout.setRefreshing(false);
//
//            }
//        });

        viewPager2=findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Set up the adapter for ViewPager
        setupViewPager();
        navigationView();

        // Handle BottomNavigationView item selection



    }

    private void setupViewPager() {
        Viewpager_2 adapter = new Viewpager_2(getSupportFragmentManager(),getLifecycle());
        viewPager2.setAdapter(adapter);


        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.home);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.home1);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.home2);
                        break;
                }
            }
        });
    }

//    private void fetchProducts() {
//        swipeRefreshLayout.setRefreshing(true);
//        if (isLoading) return;
//
//        isLoading = true;
//
//        String url = "https://api.escuelajs.co/api/v1/products?page=" + currentPage + "&limit=10";
//
//       JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//           @Override
//           public void onResponse(JSONArray response) {
//
//               try {
//
//                   List<Models_1> models1s = new ArrayList<>();
//
//
//                   for (int i=0;i<response.length();i++){
//                       JSONObject jsonObject= response.getJSONObject(i);
//                       String title = jsonObject.getString("title");
//                       int id = jsonObject.getInt("id");
//                       String image = jsonObject.getJSONArray("images").getString(0);
//                       double price = jsonObject.getDouble("price");
//                       models1s.add(new Models_1(title, image, price,id));
//
//                   }
//
//
//                   adapter.addProducts(models1s);
//                   currentPage++;
//                   adapter.notifyDataSetChanged();
//                   swipeRefreshLayout.setRefreshing(false);
//                   isLoading = false;
//
//
//               }catch (JSONException e){
//                   e.printStackTrace();
//               }
//
//           }
//       }, new Response.ErrorListener() {
//           @Override
//           public void onErrorResponse(VolleyError error) {
//
//               Log.e("Volley", "Error: " + error.getMessage());
//               isLoading = false;
//           }
//       });
//       requestQueue.add(jsonArrayRequest);
//
//    }
//
//    private void setupPagination() {
//
//        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
//                if (gridLayoutManager !=null && ! isLoading && gridLayoutManager.findLastVisibleItemPosition() == itemlist.size()-20){
//                    isLoading=true;
//                    swipeRefreshLayout.setRefreshing(false);
//                    fetchProducts();
//
//                }
//            }
//        });
//    }
//

    private void FirstimeStart(){
        SharedPreferences sharedPreferences = getSharedPreferences("Rosdeb",MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean("isFirstTime", true);

        if (isFirstTime) {
            // Navigate to Get Started screen
            Intent intent = new Intent(this, SplashScreen.class);
            startActivity(intent);
            finish();

            // Update SharedPreferences
        } else {
            // Navigate to main screen
            setContentView(R.layout.activity_main);
        }
    }
//public  void appRefresh(){
//
//    new Handler().postDelayed(new Runnable() {
//        @Override
//        public void run() {
//            // Populate productList with data
//            itemlist.clear();
//            // Fetch fresh data
//            setupPagination();
//            fetchProducts();
//            adapter.notifyDataSetChanged();
//            // Stop the refresh animation
//            swipeRefreshLayout.setRefreshing(false);
//        }
//    }, 500);
//}
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        setupPagination();
//        fetchProducts();
//    }

    private void navigationView(){
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.home) {
                        viewPager2.setCurrentItem(0);
                        return true;
                    } else if (id == R.id.home1) {
                        viewPager2.setCurrentItem(1);
                        return true;
                    }else if (id == R.id.home2) {
                        viewPager2.setCurrentItem(2);
                        return true;
                    }
                return false;
            }
        });

    }
}