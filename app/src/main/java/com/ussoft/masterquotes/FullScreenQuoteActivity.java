package com.ussoft.masterquotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.ussoft.masterquotes.adapters.FullScreeViewPagerAdapter;
import com.ussoft.masterquotes.adapters.MainActivityAdapter;
import com.ussoft.masterquotes.adapters.RecyclerViewAdapter;
import com.ussoft.masterquotes.dataclass.QuotesData;

import java.util.ArrayList;

public class FullScreenQuoteActivity extends AppCompatActivity {

    ImageView backButton;
    ViewPager2 viewPager2;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<QuotesData> quotesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_quote);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        recyclerView = findViewById(R.id.sliderrecyclerview);
        viewPager2 = findViewById(R.id.fullscreen_viewpager);
        backButton = findViewById(R.id.fullscreen_backbutton);
        backButton.setOnClickListener(view -> finish());

        Bundle bundle = getIntent().getExtras();
        quotesList = (ArrayList<QuotesData>) bundle.getSerializable("QuotesList");
        int position = getIntent().getIntExtra("PagerPosition", 0);
        viewPager2.setAdapter(new FullScreeViewPagerAdapter(this, quotesList));
        viewPager2.setCurrentItem(position);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewAdapter = new RecyclerViewAdapter(this, quotesList, viewPager2);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}