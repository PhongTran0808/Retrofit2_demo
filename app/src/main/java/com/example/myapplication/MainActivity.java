package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;


import com.example.myapplication.R;

import com.example.myapplication.CategoryAdapter;
import com.example.myapplication.APIService;
import com.example.myapplication.RetrofitClient;
import com.example.myapplication.Category;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView rcCate;
    CategoryAdapter categoryAdapter;
    APIService apiService;

    List<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        GetCategory();
    }

    private void AnhXa() {
        rcCate = (RecyclerView) findViewById(R.id.rc_category);
        categoryList = new ArrayList<>();
    }

    private void GetCategory() {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);

        apiService.getCategoriesAll().enqueue(new Callback<List<Category>>() {

            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {

                if (response.isSuccessful()) {
                    categoryList = response.body();

                    categoryAdapter = new CategoryAdapter(MainActivity.this, categoryList);

                    rcCate.setHasFixedSize(true);
                    RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    rcCate.setLayoutManager(LayoutManager);

                    rcCate.setAdapter(categoryAdapter);
                    categoryAdapter.notifyDataSetChanged();

                } else {
                    int statusCode = response.code();
                    Log.e("RetrofitError", "Response not successful, Status: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e("logg", "Request Failed: " + t.getMessage());
            }
        });
    }
}