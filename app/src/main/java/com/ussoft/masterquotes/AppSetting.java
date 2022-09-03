package com.ussoft.masterquotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AppSetting extends AppCompatActivity {

    TextView shareApp, rateApp, aboutUs, privacyApp;
    RadioGroup radioGroup;
    RadioButton selectedradio;
    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_setting);

        toolbar = findViewById(R.id.settingsToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences("BG_Selection", MODE_PRIVATE);
        myEdit = sharedPreferences.edit();
        String getBG = sharedPreferences.getString("name", "");
        if (getBG.equals("Color")){
            selectedradio = findViewById(R.id.bgColor);
            selectedradio.setChecked(true);
        }else if (getBG.equals("Image")){
            selectedradio = findViewById(R.id.bgImage);
            selectedradio.setChecked(true);
        }

        shareApp = findViewById(R.id.share_friend);
        rateApp = findViewById(R.id.rateapp);
        aboutUs = findViewById(R.id.aboutus);
        privacyApp = findViewById(R.id.privacyapp);
        radioGroup = findViewById(R.id.radiogroup);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.bgColor:
                        Toast.makeText(AppSetting.this, "Color Selected", Toast.LENGTH_SHORT).show();
                        myEdit.putString("name", "Color");
                        myEdit.apply();
                        break;

                    case R.id.bgImage:
                        Toast.makeText(AppSetting.this, "Images Selected", Toast.LENGTH_SHORT).show();
                        myEdit.putString("name", "Image");
                        myEdit.apply();
                        break;
                }
            }
        });

        shareApp.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName()))));

        rateApp.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName()))));

        aboutUs.setOnClickListener(view -> {
            Toast.makeText(this, "About Us", Toast.LENGTH_SHORT).show();
        });

        privacyApp.setOnClickListener(view -> {
            Toast.makeText(this, "Privacy And Policy", Toast.LENGTH_SHORT).show();
        });

    }
}