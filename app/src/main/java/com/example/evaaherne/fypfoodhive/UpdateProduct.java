package com.example.evaaherne.fypfoodhive;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateProduct extends AppCompatActivity  {
    private String ProdId;
    private String Name;
    private String Category;
    private String BBDate;
    private String ExpDays;

@Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.update_product);

    ProdId = getIntent().getStringExtra("ProdId");
    Name = getIntent().getStringExtra("Name");
    Category = getIntent().getStringExtra("Category");
    BBDate = getIntent().getStringExtra("BBDate");
    ExpDays = getIntent().getStringExtra("ExpDays");

    EditText product_name = findViewById(R.id.product_name);
    Spinner prodCat = findViewById(R.id.prod_cat);
    EditText bestBEntry = findViewById(R.id.bestBEntry);
    EditText prod_exp_days = findViewById(R.id.prod_exp_days);
    prodCat.setSelection(getIndexSpinnerItem(prodCat, Category ));

    final Button buttonUpdate = findViewById(R.id.buttonUpdateProduct);
    final Button buttonDelete = findViewById(R.id.buttonDeleteProduct);

    buttonUpdate.setOnClickListener(new View.OnClickListener(){
    @Override
        public void onClick(View view){
        Product product = new Product();
        product.setProdName(product_name.getText().toString());
        product.setProdCategory(prodCat.getSelectedItem().toString());
        product.setProdBBDate(bestBEntry.getText().toString());
        String stringDays = String.valueOf(prod_exp_days);
        int parsefinalDays = Integer.parseInt(stringDays);
        product.setExpDay(parsefinalDays);

    }


    });
    }

    private int getIndexSpinnerItem(Spinner spinner, String item){
    int index = 0;
    for(int i = 0; i<spinner.getCount(); i++){
        if(spinner.getItemAtPosition(i).equals(item)){
            index = i;
            break;
        }
    }
    return index;
    }



}
