package com.example.evaaherne.fypfoodhive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AddFoodItem extends BaseActivity {
    ImageButton btnAdd;
    Button btnInventory;

    EditText product_name;
    EditText bestBEntry;
    Spinner spinCategory;

    DatabaseReference databaseProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_item);

        //Database reference
        databaseProducts = FirebaseDatabase.getInstance().getReference("Inventory");

        //Views
        btnAdd = findViewById(R.id.btnAdd);

        product_name = findViewById(R.id.product_name);
        bestBEntry = findViewById(R.id.bestBEntry);
        spinCategory = findViewById(R.id.spinCategory);
        btnInventory = findViewById(R.id.btnInventory);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
                product_name.getText().clear();
                bestBEntry.getText().clear();
            }
        });

        btnInventory.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Inventory.class);
                startActivity(i);
                setContentView(R.layout.activity_inventory);
            }
        });
    }



    private void addProduct() {

        String ProdName = product_name.getText().toString().trim();
        String spinCat = spinCategory.getSelectedItem().toString();
        String bestBefore = bestBEntry.getText().toString().trim();

        //IF THE VIEW IS NOT EMPTY FOR NAME
        if (!TextUtils.isEmpty(ProdName)) {

            String id = databaseProducts.push().getKey(); //Get a unique key for the id
            Product product = new Product(id, ProdName, bestBefore, spinCat); //OBJ INSTANSTIATION

            //SETS DB VALUES
            databaseProducts.child(id).setValue(product);


            //Toast is a short message that pops up on screen
            Toast.makeText(this, "Product  added", Toast.LENGTH_LONG).show();
            product_name.getText().clear();
            bestBEntry.getText().clear();

        } else if(!TextUtils.isEmpty(ProdName)&&!TextUtils.isEmpty(bestBefore)) {
            Toast.makeText(this, "Missing details! Please fill in all fields.", Toast.LENGTH_LONG).show();
        }
    }
}
