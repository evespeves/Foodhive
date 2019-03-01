package com.example.evaaherne.fypfoodhive;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {


    Button buttonLogin;
    Button buttonRegister;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //BUTTONS
        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonRegister = findViewById(R.id.buttonRegister);

        //WHEN BUTTON CLICKED GOES TO CLASS
        buttonLogin.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), AddFoodItem.class);
            startActivity(i);
            setContentView(R.layout.activity_add_food_item);
        });

        //WHEN BUTTON CLICKED GOES TO CLASS
        buttonRegister.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(),  login.class);
            startActivity(i);
            setContentView(R.layout.activity_login);
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
}

