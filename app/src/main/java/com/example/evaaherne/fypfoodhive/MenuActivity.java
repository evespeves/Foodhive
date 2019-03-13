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
    Button btnOcr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //BUTTONS
        btnAddItem = findViewById(R.id.btnAddItem);
        btnInventory = findViewById(R.id.btnInventory);
        btnOcr = findViewById(R.id.btnOcr);

        //WHEN BUTTON CLICKED GOES TO CLASS
        btnAddItem.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), AddFoodItem.class)));

        //WHEN BUTTON CLICKED GOES TO CLASS
        btnInventory.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ProductListings.class)));

        //WHEN BUTTON CLICKED GOES TO CLASS
        btnOcr.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), TextDetector.class)));
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
