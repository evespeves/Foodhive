package com.example.evaaherne.fypfoodhive;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.evaaherne.fypfoodhive.activities.HowToUse;
import com.example.evaaherne.fypfoodhive.activities.QRScanner;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;


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
            Intent i = new Intent(getApplicationContext(), login.class);
            startActivity(i);
            setContentView(R.layout.activity_login);
        });

        //WHEN BUTTON CLICKED GOES TO CLASS
        buttonRegister.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(),  RegisterActivity.class);
            startActivity(i);
            setContentView(R.layout.activity_register);
        });


    }

}


