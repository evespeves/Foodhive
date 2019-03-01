package com.example.evaaherne.fypfoodhive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        btnOcr.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(),  PhotoCaptureActivity.class);
            startActivity(i);
            setContentView(R.layout.activity_photo_capture);
        });
    }
}
