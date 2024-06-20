package com.example.fitforce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class strengthSessionActivity extends AppCompatActivity {
    WebView wvAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strength_session);
        wvAnimation = findViewById(R.id.wvAnimation);
        animationSettings(wvAnimation);

    }


    public void animationSettings(WebView animation){
        animation = findViewById(R.id.wvAnimation);
        animation.getSettings().setLoadsImagesAutomatically(true);
        animation.getSettings().setLoadWithOverviewMode(true);
        animation.getSettings().setUseWideViewPort(true);
        animation.getSettings().setBuiltInZoomControls(false);
        animation.getSettings().setDisplayZoomControls(false);
        animation.getSettings().setSupportZoom(false);
        animation.getSettings().setAllowFileAccess(true);
        animation.getSettings().setAllowContentAccess(true);
        animation.getSettings().setAllowFileAccessFromFileURLs(true);
    }
}