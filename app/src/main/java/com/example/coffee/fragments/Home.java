package com.example.coffee.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.coffee.Adapter.Adapter;
import com.example.coffee.Adapter.Adapter_category;
import com.example.coffee.Models.Category;
import com.example.coffee.Models.Models_1;
import com.example.coffee.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

     RecyclerView recyclerView,recyclerView_category;
     List<Models_1> itemlist;
     List<Category> category;
     ShimmerFrameLayout shimmerFrameLayout;
     Adapter adapter;
    Adapter_category adapterCategory;
    private boolean isLoading = false;
    private int currentPage = 1; // Start offset
    private final int limit = 20;
    SwipeRefreshLayout swipeRefreshLayout;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView=view.findViewById(R.id.recyclerView);
        shimmerFrameLayout = view.findViewById(R.id.shimmer);
        shimmerFrameLayout.startShimmer();
        recyclerView_category=view.findViewById(R.id.recyclerViewCategories);
        category=new ArrayList<>();
        recyclerView_category.setHasFixedSize(false);
        recyclerView_category.setNestedScrollingEnabled(false);
        recyclerView_category.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        adapterCategory = new Adapter_category(category,getActivity());
        recyclerView_category.setAdapter(adapterCategory);

        swipeRefreshLayout=view.findViewById(R.id.refresh);
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        itemlist=new ArrayList<>();
        adapter = new Adapter(getActivity(),itemlist);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                itemlist.clear();
                setupPagination();
                fatechDATA();
                adapter.notifyDataSetChanged();// Load new data here
                swipeRefreshLayout.setRefreshing(false);
                isLoading=false;

            }
        });

        setupPagination();
        fatechDATA();

        fetchCategories();

        return view;

    }

    private void fetchCategories() {

        String URL = "https://api.escuelajs.co/api/v1/categories";
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    List<Category> category = new ArrayList<>();
                    for (int i =0;i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        String image = jsonObject.getString("image");
                        category.add(new Category(id,name,image));

                    }
                    adapterCategory.addProducts(category);
                    adapterCategory.notifyDataSetChanged();


                }catch (JSONException e){
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("erorr",""+error);
            }
        });
        requestQueue.add(jsonArrayRequest);


    }

    public void fatechDATA(){
        //swipeRefreshLayout.setRefreshing(true);

        if (isLoading) return;
        isLoading=true;


    String url = "https://api.escuelajs.co/api/v1/products?offset="+currentPage + "&limit=50";
    RequestQueue requestQueue = Volley.newRequestQueue(requireContext());

    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {

            try {
                   List<Models_1> models1s = new ArrayList<>();
                   for (int i=0;i<response.length();i++){
                       JSONObject jsonObject= response.getJSONObject(i);
                       String title = jsonObject.getString("title");
                       int id = jsonObject.getInt("id");
                       String image = jsonObject.getJSONArray("images").getString(0);
                       double price = jsonObject.getDouble("price");

                       String  description = jsonObject.getString("description");
                       models1s.add(new Models_1(title, image, price,id,description));

                   }

                   adapter.addProducts(models1s);
                   currentPage++;
                   adapter.notifyDataSetChanged();
                   isLoading = false;

               }catch (JSONException e){
                   e.printStackTrace();
               }

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("erorr",""+error);
            isLoading=false;
            swipeRefreshLayout.setRefreshing(false);


        }
    });
    requestQueue.add(jsonArrayRequest);
    }
    public void setupPagination(){


        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                
                if ( layoutManager!=null && !isLoading && layoutManager.findFirstCompletelyVisibleItemPosition()==itemlist.size()-1){
                    isLoading=true;
                    swipeRefreshLayout.setRefreshing(false);
                    fatechDATA();
                }
            }
        });

    }


}