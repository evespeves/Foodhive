package com.example.evaaherne.fypfoodhive.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.evaaherne.fypfoodhive.AddFoodItem;
import com.example.evaaherne.fypfoodhive.MenuActivity;
import com.example.evaaherne.fypfoodhive.ProductListings;
import com.example.evaaherne.fypfoodhive.R;
import com.example.evaaherne.fypfoodhive.UserProfile;

public class HowToUse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_use);
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
