package com.example.evaaherne.fypfoodhive;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent i = new Intent(getApplicationContext(), TextDetector.class);
                startActivity(i);
                setContentView(R.layout.activity_text_detector);
            }
        });

        //WHEN BUTTON CLICKED GOES TO CLASS
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent i = new Intent(getApplicationContext(),  RegisterActivity.class);
                startActivity(i);
                setContentView(R.layout.activity_register);
            }
        });






    }
}

