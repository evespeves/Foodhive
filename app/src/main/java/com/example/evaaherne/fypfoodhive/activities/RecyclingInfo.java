package com.example.evaaherne.fypfoodhive.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.evaaherne.fypfoodhive.R;

public class RecyclingInfo extends AppCompatActivity {


    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycling_info);
        /** Webview in android
         * https://developer.android.com/guide/webapps/webview
         */

            Intent recipe = getIntent();
            String prodName = recipe.getStringExtra("ProductName");


            webView = findViewById(R.id.webView);
            WebSettings webSettings = webView.getSettings();
            // webSettings.setJavaScriptEnabled(true);

            webView.loadUrl("https://www.allrecipes.com/search/results/?wt="+prodName);

        }


    }

