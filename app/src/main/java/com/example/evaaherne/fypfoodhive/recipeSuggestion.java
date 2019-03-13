package com.example.evaaherne.fypfoodhive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.evaaherne.fypfoodhive.activities.HowToUse;

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
    /** How to add a menu bar
     *  https://www.youtube.com/watch?v=62y6Ad2SJEQ&ab_channel=PRABEESHRK**/
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_bar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.inventory:
            startActivity(new Intent(getApplicationContext(), ProductListings.class));
            return(true);
        case R.id.home:
            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            return(true);
        case R.id.add:
            startActivity(new Intent(getApplicationContext(), AddFoodItem.class));
            return(true);
        case R.id.account:
            startActivity(new Intent(getApplicationContext(), UserProfile.class));
            return(true);
        case R.id.howToUse:
            startActivity(new Intent(getApplicationContext(), HowToUse.class));
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }


}
