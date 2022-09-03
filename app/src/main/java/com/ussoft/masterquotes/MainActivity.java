package com.ussoft.masterquotes;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.navigation.NavigationView;
import com.ussoft.masterquotes.adapters.MainActivityAdapter;
import com.ussoft.masterquotes.apiwork.Api;
import com.ussoft.masterquotes.dataclass.QuotesData;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    MainActivityAdapter adapter;
    ShimmerFrameLayout shimmerFrameLayout;
    RecyclerView recyclerView;
    String BASE_URL = "https://type.fit/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        shimmerFrameLayout = findViewById(R.id.mainfrag_shimmer_loading_layout);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.mainActivityToolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.fav_act:
                    Intent favIntent = new Intent(MainActivity.this, BookMarkActivity.class);
                    startActivity(favIntent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.accountsetting:
                    Intent setting = new Intent(MainActivity.this, AppSetting.class);
                    startActivity(setting);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.share_tab:
                case R.id.rate_tab:
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.about_tab:
                    Toast.makeText(MainActivity.this, "About Us", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.privacy_tab:
                    Toast.makeText(MainActivity.this, "Privacy And Policy", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
            }
            return true;
        });
        recyclerView = findViewById(R.id.quotesMainRecyclerView);
        fetchQuotes();
    }

    private void fetchQuotes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api myApi = retrofit.create(Api.class);

        Call<ArrayList<QuotesData>> call = myApi.getAllQuotes();
        call.enqueue(new Callback<ArrayList<QuotesData>>() {
            @Override
            public void onResponse(Call<ArrayList<QuotesData>> call, Response<ArrayList<QuotesData>> response) {
                ArrayList<QuotesData> quote = response.body();
                if (!quote.isEmpty()){
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    Collections.shuffle(quote);
                    adapter = new MainActivityAdapter(MainActivity.this, quote);
                    recyclerView.setAdapter(adapter);
                    shimmerFrameLayout.startShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }else{
                    shimmerFrameLayout.setVisibility(View.GONE);
                    ImageView imageView = findViewById(R.id.main_no_data);
                    imageView.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<QuotesData>> call, Throwable t) {
            }
        });
    }

    @Override
    public void onResume() {
        Log.d("UGS LifeCycle: ", "On Resume");
        shimmerFrameLayout.startShimmer();
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("UGS LifeCycle: ", "On Pause");
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }
}