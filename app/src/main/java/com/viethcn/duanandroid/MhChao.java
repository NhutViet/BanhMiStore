package com.viethcn.duanandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MhChao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mh_chao);

        // Load logo using Glide
        ImageView ivLogo = findViewById(R.id.ivlogo);
        Glide.with(this)
                .load(R.drawable.logo)
                .into(ivLogo);

        // Delay navigation to Login screen
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MhChao.this, Login.class);
            startActivity(intent);
            finish(); // Close splash screen
        }, 5000);
    }
}
