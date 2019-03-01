package com.example.evaaherne.fypfoodhive;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evaaherne.fypfoodhive.Models.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateProduct extends AppCompatActivity  {
    private String ProdId;
    private String Name;
    private String Category;
    private String BBDate;
    private String ExpDays;

    private FirebaseAuth mAuth;
    DatabaseReference databaseProducts;

    EditText prod_exp_day;
    EditText product_name;
    EditText bestBEntry;
    Spinner prodCat;
    Button buttonUpdate;
    Button buttonDelete;
    Button btnRecipes;
@Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.update_product);

    // Check if user is signed in (non-null) and update UI accordingly.
    mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String userId = currentUser.getUid();

    databaseProducts = FirebaseDatabase.getInstance().getReference("Inventory").child(userId);
     Intent intent = getIntent();

    //View declaration
     product_name = findViewById(R.id.product_name);
     prodCat = findViewById(R.id.prod_cat);
     bestBEntry = findViewById(R.id.bestBEntry);
     prod_exp_day = findViewById(R.id.prod_exp_days);

    /**
     * Bring data from one item to another
     * https://stackoverflow.com/questions/4233873/how-do-i-get-extra-data-from-intent-on-android
     */
    ProdId = intent.getStringExtra("ProdId");
    Name = intent.getStringExtra("Name");
    Category = intent.getStringExtra("Category");
    BBDate = intent.getStringExtra("Date");
    ExpDays = intent.getStringExtra("ExpDays");


    product_name.setText(Name);
    prodCat.setSelection(getIndexSpinnerItem(prodCat, Category ));
    bestBEntry.setText(BBDate);
    prod_exp_day.setText(ExpDays);



    buttonUpdate = findViewById(R.id.buttonUpdateProduct);
    buttonDelete = findViewById(R.id.buttonDeleteProduct);
    btnRecipes = findViewById(R.id.btnRecipes);


    /** Calender date picker
     * https://www.youtube.com/watch?v=TTFfQgikQiU&ab_channel=DebomaDevelopmentStudio**/
    bestBEntry.setOnClickListener(v -> {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dateDialog = new DatePickerDialog(v.getContext(), datePickerListener, mYear, mMonth, mDay);
        dateDialog.getDatePicker().setMinDate(new Date().getTime());
        dateDialog.show();

    });


    buttonUpdate.setOnClickListener(view -> {
        //String pId = ProdId.getText().toString();
        String pName = product_name.getText().toString();
        String pBBdate = bestBEntry.getText().toString();
        String pCategory = prodCat.getSelectedItem().toString();
        String days = String.valueOf(prod_exp_day.getText());
        int finalDays = Integer.parseInt(days);
                if (!TextUtils.isEmpty(Name)  ) {
                    updateProduct(ProdId, pName, pBBdate, pCategory, finalDays);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Product Update Failed. Please enter in all criteria.", Toast.LENGTH_LONG).show();
                }
    });

    buttonDelete.setOnClickListener(view -> {
        String days = String.valueOf(prod_exp_day.getText());
        if (!TextUtils.isEmpty(Name)) {
            deleteProduct(ProdId, Name);
        }
        else {
            Toast.makeText(getApplicationContext(), "Delete failed, please fill in all details.", Toast.LENGTH_LONG).show();
        }
    });


    btnRecipes.setOnClickListener(view -> {
        String pName = product_name.getText().toString();
        Intent recipe = new Intent(this, recipeSuggestion.class);
        recipe.putExtra("ProductName", pName);
        startActivity(recipe);
        setContentView(R.layout.activity_recipe_suggestion);
    });

    }

    /** Get the index of the spinner and return the value.
     * https://stackoverflow.com/questions/2390102/how-to-set-selected-item-of-spinner-by-value-not-by-position
     */
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

    /** Calender date picker
     * https://www.youtube.com/watch?v=TTFfQgikQiU&ab_channel=DebomaDevelopmentStudio**/
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String format = new SimpleDateFormat("dd/MMM/YYYY", Locale.UK ).format(c.getTime());
            bestBEntry.setText(format);

            String todayDate = new SimpleDateFormat("dd/MMM/YYYY", Locale.getDefault()).format(new Date());
            String countDays = getCountOfDays(todayDate, format);
            prod_exp_day.setText(countDays);
        }
    };


    /** Calculation of days in total until expiry
     * https://stackoverflow.com/questions/42553017/android-calculate-days-between-two-dates **/

    public String getCountOfDays(String createdDateString, String expireDateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault());

        Date createdConvertedDate = null, expireCovertedDate = null, todayWithZeroTime = null;
        try {
            createdConvertedDate = dateFormat.parse(createdDateString);
            expireCovertedDate = dateFormat.parse(expireDateString);

            Date today = new Date();

            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int cYear = 0, cMonth = 0, cDay = 0;

        if (createdConvertedDate.after(todayWithZeroTime)) {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(createdConvertedDate);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);

        } else {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(todayWithZeroTime);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);
        }


    /*Calendar todayCal = Calendar.getInstance();
    int todayYear = todayCal.get(Calendar.YEAR);
    int today = todayCal.get(Calendar.MONTH);
    int todayDay = todayCal.get(Calendar.DAY_OF_MONTH);
    */

        Calendar eCal = Calendar.getInstance();
        eCal.setTime(expireCovertedDate);

        int eYear = eCal.get(Calendar.YEAR);
        int eMonth = eCal.get(Calendar.MONTH);
        int eDay = eCal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(cYear, cMonth, cDay);
        date2.clear();
        date2.set(eYear, eMonth, eDay);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);

        return ((int) dayCount + "");
    }


    /** Updating and deleting items
     * https://www.simplifiedcoding.net/firebase-realtime-database-crud/
     */
    private boolean updateProduct(String id, String name, String date, String category, int days) {
        //getting the specified product reference
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userId = currentUser.getUid();

        //updating product
        Product product = new Product(id, name, date,category, days);
        databaseProducts.child(id).setValue(product);
        Toast.makeText(getApplicationContext(), "Product Updated", Toast.LENGTH_LONG).show();
        Log.d("PRODUCT", "updated product: " + name);
        return true;
    }

    private boolean deleteProduct(String id, String name) {
        //getting the specified product reference
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userId = currentUser.getUid();

        //removing product
        databaseProducts.child(id).removeValue();

        Toast.makeText(getApplicationContext(), "Product Deleted", Toast.LENGTH_LONG).show();
        Log.d("PRODUCT", "Deleted product: " + name);
        product_name.getText().clear();
        bestBEntry.getText().clear();
        prod_exp_day.getText().clear();
        return true;
    }

    //Updates the data
    private void updateUI(FirebaseUser user) {


    }
}
