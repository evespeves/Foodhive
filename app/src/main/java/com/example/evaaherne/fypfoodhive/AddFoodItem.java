package com.example.evaaherne.fypfoodhive;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

public class AddFoodItem extends AppCompatActivity {
    String btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_item);

    }

//    private void addItem () {
//        String  = editTextName.getText().toString().trim();
//        String email = editTextEmail.getText().toString().trim();
//
//
//        //IF THE VIEW IS NOT EMPTY FOR NAME
//        if (!TextUtils.isEmpty(name)) {
//
//            String id = databaseUsers.push().getKey(); //Get a unique key for the id
//            Users users = new Users(id, name, email, nutsValue, dairyValue, glutenValue); //OBJ INSTANSTIATION
//
//            //SETS DB VALUES
//            databaseUsers.child(id).setValue(users);
//
//
//            //Toast is a short message that pops up on screen
//            Toast.makeText(this, "User added", Toast.LENGTH_LONG).show();
//
//        } else {
//            Toast.makeText(this, "You should enter a name", Toast.LENGTH_LONG).show();
//        }
//    }
}
