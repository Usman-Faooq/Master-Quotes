package com.ussoft.masterquotes.adapters;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RenderEffect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.ussoft.masterquotes.FullScreenQuoteActivity;
import com.ussoft.masterquotes.R;
import com.ussoft.masterquotes.dataclass.BookMarkData;
import com.ussoft.masterquotes.dataclass.QuotesData;
import com.ussoft.masterquotes.roomdbwork.DAO;
import com.ussoft.masterquotes.roomdbwork.DataBase;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

import jp.wasabeef.blurry.Blurry;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.MyHolder>{


    Context context;
    ArrayList<QuotesData> list;
    boolean check;
    SharedPreferences sharedPreferences;
    static int bgnumber = 1;

    public MainActivityAdapter(Context context, ArrayList<QuotesData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_quotes, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {

        sharedPreferences = context.getSharedPreferences("BG_Selection", context.MODE_PRIVATE);
        String getBG = sharedPreferences.getString("name", "");
        if (getBG.equals("Color")){
            holder.layout.setBackgroundColor(Color.parseColor("#5D71F1"));
        }else{
            if (bgnumber == 0){
                bgnumber = 1;
                Blurry.with(context).radius(25).sampling(25).onto(holder.layout);
                holder.layout.setBackgroundResource(R.drawable.image0);
            }else if (bgnumber == 1){
                bgnumber = 2;
                holder.layout.setBackgroundResource(R.drawable.image1);
            }else if (bgnumber == 2){
                bgnumber = 3;
                holder.layout.setBackgroundResource(R.drawable.image2);
            }else if (bgnumber == 3){
                bgnumber = 4;
                holder.layout.setBackgroundResource(R.drawable.image3);
            }else if (bgnumber == 4){
                bgnumber = 5;
                holder.layout.setBackgroundResource(R.drawable.image4);
            }else if (bgnumber == 5){
                bgnumber = 6;
                holder.layout.setBackgroundResource(R.drawable.image5);
            }else if (bgnumber == 6){
                bgnumber = 7;
                holder.layout.setBackgroundResource(R.drawable.image6);
            }else if (bgnumber == 7){
                bgnumber = 8;
                holder.layout.setBackgroundResource(R.drawable.image7);
            }else {
                bgnumber = 0;
                holder.layout.setBackgroundResource(R.drawable.image8);
            }


        }
        holder.quoteContent.setText(list.get(position).getText());
        holder.quoteAuthor.setText("-" + list.get(position).getAuthor());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FullScreenQuoteActivity.class);
                intent.putExtra("PagerPosition", position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("QuotesList", list);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        //Quotes Text Copy Button
        holder.copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Quote: " , list.get(position).getText());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        //Download Button
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.logoText.setVisibility(View.VISIBLE);
                holder.buttonsLayout.setVisibility(View.INVISIBLE);
                holder.copyBtn.setVisibility(View.INVISIBLE);
                Bitmap bitmap = Bitmap.createBitmap(holder.layout.getWidth(), holder.layout.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                holder.layout.draw(canvas);
                try{
                    File storage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                    File dir = new File(storage.getAbsolutePath() + "/Master Quotes");
                    if (!dir.exists()){
                        dir.mkdirs();
                    }
                    String filename = String.format("%d.jpg", System.currentTimeMillis());
                    File out = new File(dir, filename);
                    FileOutputStream stream = new FileOutputStream(out);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    Toast.makeText(context, "Image Saved", Toast.LENGTH_SHORT).show();
                    stream.flush();
                    stream.close();
                }catch (Exception e){

                }finally {
                    holder.logoText.setVisibility(View.INVISIBLE);
                    holder.buttonsLayout.setVisibility(View.VISIBLE);
                    holder.copyBtn.setVisibility(View.VISIBLE);
                }
            }
        });

        //Share Button
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.logoText.setVisibility(View.VISIBLE);
                holder.buttonsLayout.setVisibility(View.INVISIBLE);
                holder.copyBtn.setVisibility(View.INVISIBLE);
                Bitmap bitmap = Bitmap.createBitmap(holder.layout.getWidth(), holder.layout.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                holder.layout.draw(canvas);
                try{
                    String mediaPath = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Master Quotes", null);
                    Uri uri = Uri.parse(mediaPath);
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                    context.startActivity(Intent.createChooser(intent, "Share Image:"));
                }catch (Exception e){

                }finally {
                    holder.logoText.setVisibility(View.INVISIBLE);
                    holder.buttonsLayout.setVisibility(View.VISIBLE);
                    holder.copyBtn.setVisibility(View.VISIBLE);
                }

            }
        });

        //Bookmark Button
        String quote = list.get(position).getText();
        String author = list.get(position).getAuthor();
        DataBase dataBase = Room.databaseBuilder(context.getApplicationContext(),
                DataBase.class, "BookMark").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        DAO dao = dataBase.dao();
        check = dao.isBookMarkExist(quote);
        if (check){
            holder.bookmark.setImageResource(R.drawable.icon_bookmark_filled);
        }else{
            holder.bookmark.setImageResource(R.drawable.icon_bookmark);
        }
        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check) {
                    dao.removeBookMark(quote);
                    holder.bookmark.setImageResource(R.drawable.icon_bookmark);
                    check = false;
                }else{
                    dao.insertLike(new BookMarkData(quote, author));
                    holder.bookmark.setImageResource(R.drawable.icon_bookmark_filled);
                    check = true;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView quoteContent, quoteAuthor, logoText;
        RelativeLayout layout, buttonsLayout;
        ImageView download, share, bookmark, copyBtn;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            buttonsLayout = itemView.findViewById(R.id.btnlayout);
            quoteContent = itemView.findViewById(R.id.main_quotes_content);
            quoteAuthor = itemView.findViewById(R.id.authorname);
            logoText = itemView.findViewById(R.id.main_logotext);
            download = itemView.findViewById(R.id.download_btn);
            share = itemView.findViewById(R.id.share_btn);
            bookmark = itemView.findViewById(R.id.bookmarkbtn);
            copyBtn = itemView.findViewById(R.id.mainCopybtn);
        }
    }
}
