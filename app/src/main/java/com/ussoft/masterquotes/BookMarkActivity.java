package com.ussoft.masterquotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.ussoft.masterquotes.adapters.BookMarkAdapter;
import com.ussoft.masterquotes.dataclass.BookMarkData;
import com.ussoft.masterquotes.roomdbwork.DAO;
import com.ussoft.masterquotes.roomdbwork.DataBase;

import java.util.List;

public class BookMarkActivity extends AppCompatActivity {

    BookMarkAdapter adapter;
    ShimmerFrameLayout shimmerFrameLayout;
    RecyclerView recyclerView;
    List<BookMarkData> quote;
    ImageView imageView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        toolbar = findViewById(R.id.bookmarkToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DataBase dataBase = Room.databaseBuilder(getApplicationContext(),
                DataBase.class, "BookMark").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        DAO dao = dataBase.dao();
        quote = dao.retriveBookMarks();


        shimmerFrameLayout = findViewById(R.id.bookmark_shimmer_loading_layout);
        recyclerView = findViewById(R.id.bookmarkRecyclerview);
        imageView = findViewById(R.id.empty_bookmark);

        if (quote.isEmpty()){
            imageView.setVisibility(View.VISIBLE);
            shimmerFrameLayout.setVisibility(View.GONE);
        }else{
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new BookMarkAdapter(this, quote);
            recyclerView.setAdapter(adapter);
            shimmerFrameLayout.startShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
        }
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