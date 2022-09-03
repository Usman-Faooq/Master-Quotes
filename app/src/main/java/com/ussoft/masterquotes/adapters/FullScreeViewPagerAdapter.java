package com.ussoft.masterquotes.adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
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

import com.ussoft.masterquotes.R;
import com.ussoft.masterquotes.dataclass.QuotesData;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class FullScreeViewPagerAdapter extends RecyclerView.Adapter<FullScreeViewPagerAdapter.MyHolder>{

    Context context;
    ArrayList<QuotesData> list;

    public FullScreeViewPagerAdapter(Context context, ArrayList<QuotesData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.cardview_fullscreen, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.quoteContent.setText(list.get(position).getText());
        holder.quoteAuthor.setText("-" + list.get(position).getAuthor());

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
                holder.layout.setBackgroundColor(Color.parseColor("#5D71F1"));
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
                holder.layout.setBackgroundColor(Color.parseColor("#5D71F1"));
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
            layout = itemView.findViewById(R.id.fullscreen_layout);
            buttonsLayout = itemView.findViewById(R.id.fullscreen_btnlayout);
            quoteContent = itemView.findViewById(R.id.fullscreen_quotes_content);
            quoteAuthor = itemView.findViewById(R.id.fullscreen_authorname);
            logoText = itemView.findViewById(R.id.fullscreen_logotext);
            download = itemView.findViewById(R.id.fullscreen_download_btn);
            share = itemView.findViewById(R.id.fullscreen_share_btn);
            bookmark = itemView.findViewById(R.id.fullscreen_bookmarkbtn);
            copyBtn = itemView.findViewById(R.id.fullscreen_Copybtn);

        }
    }
}
