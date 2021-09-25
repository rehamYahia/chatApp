package com.example.ch_at.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.ch_at.R;
import com.example.ch_at.utilities.Constants;
import com.example.ch_at.utilities.PreferanceManager;

public class SplashActivity extends AppCompatActivity {
    //PreferanceManager preferanceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //preferanceManager = new PreferanceManager(getApplicationContext());
        splashTimer();
    }
    private void splashTimer()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),SigninActivity.class));
                finish();
            }
        },3000);
    }
}
