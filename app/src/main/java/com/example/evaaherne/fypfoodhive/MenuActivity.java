package com.example.evaaherne.fypfoodhive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.evaaherne.fypfoodhive.Models.Product;
import com.example.evaaherne.fypfoodhive.activities.HowToUse;

public class MenuActivity extends AppCompatActivity {

    Button btnAddItem;
    Button btnInventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //BUTTONS
        Button btnAddItem = findViewById(R.id.btnAddItem);
        Button btnInventory = findViewById(R.id.btnInventory);
        Button btnOcr = findViewById(R.id.btnOcr);

        //WHEN BUTTON CLICKED GOES TO CLASS
        btnAddItem.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), AddFoodItem.class);
            startActivity(i);
            setContentView(R.layout.activity_add_food_item);
        });


        //WHEN BUTTON CLICKED GOES TO CLASS
        btnInventory.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(),  ProductListings.class);
            startActivity(i);
            setContentView(R.layout.activity_product_listings);
        });

        //WHEN BUTTON CLICKED GOES TO CLASS
        btnOcr.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(),  TextDetector.class);
            startActivity(i);
            setContentView(R.layout.activity_text_detector);
        });
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
            Intent i = new Intent(getApplicationContext(), ProductListings.class);
            startActivity(i);
            setContentView(R.layout.activity_product_listings);
            return(true);
        case R.id.home:
            Intent b = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(b);
            setContentView(R.layout.activity_menu);
            return(true);
        case R.id.add:
            Intent c = new Intent(getApplicationContext(), AddFoodItem.class);
            startActivity(c);
            setContentView(R.layout.activity_add_food_item);
            return(true);
        case R.id.account:
            Intent a = new Intent(getApplicationContext(), UserProfile.class);
            startActivity(a);
            setContentView(R.layout.activity_user_listings);
            return(true);
        case R.id.howToUse:
            Intent d = new Intent(getApplicationContext(), HowToUse.class);
            startActivity(d);
            setContentView(R.layout.activity_how_to_use);
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }
}
