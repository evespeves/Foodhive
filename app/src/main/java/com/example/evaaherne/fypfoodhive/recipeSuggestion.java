package com.example.evaaherne.fypfoodhive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public class recipeSuggestion extends AppCompatActivity {

    /** Webview in android
     * https://developer.android.com/guide/webapps/webview
     */

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_suggestion);

        Intent recipe = getIntent();
        String prodName = recipe.getStringExtra("ProductName");


        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
       // webSettings.setJavaScriptEnabled(true);

        webView.loadUrl("https://www.allrecipes.com/search/results/?wt="+prodName);

    }


}
