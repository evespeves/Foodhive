package com.example.evaaherne.fypfoodhive;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    Button buttonLogin;
    Button buttonRegister;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //BUTTONS
          buttonLogin = findViewById(R.id.buttonLogin);
          buttonRegister = findViewById(R.id.buttonRegister);

        //WHEN BUTTON CLICKED GOES TO CLASS
        buttonLogin.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), login.class)));

        //WHEN BUTTON CLICKED GOES TO CLASS
        buttonRegister.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RegisterActivity.class)));


    }

}


