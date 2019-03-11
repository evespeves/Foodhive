package com.example.evaaherne.fypfoodhive;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evaaherne.fypfoodhive.Models.Product;
import com.example.evaaherne.fypfoodhive.activities.HowToUse;
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
    private String Desc;
    private String qrInfo;


    private FirebaseAuth mAuth;
    DatabaseReference databaseProducts;

    int finalDays;

    EditText product_name;
    TextView bestBEntry;
    TextView expDays;
    EditText product_desc;
    TextView QR;
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

    //Declaration of Views
    buttonUpdate = findViewById(R.id.buttonUpdateProduct);
    buttonDelete = findViewById(R.id.buttonDeleteProduct);
    btnRecipes = findViewById(R.id.btnRecipes);

     product_name = findViewById(R.id.product_name);
     prodCat = findViewById(R.id.spinCategory);
     bestBEntry = findViewById(R.id.bestBEntry);
     expDays = findViewById(R.id.expDays);
     product_desc = findViewById(R.id.product_desc);
     QR = findViewById(R.id.QRInfo);


    /**
     * Bring data from one item to another
     * https://stackoverflow.com/questions/4233873/how-do-i-get-extra-data-from-intent-on-android
     */
    //Load data from the product that was clicked
    ProdId = intent.getStringExtra("ProdId");
    Name = intent.getStringExtra("Name");
    Category = intent.getStringExtra("Category");
    BBDate = intent.getStringExtra("Date");
    Desc = intent.getStringExtra("Desc");
    ExpDays = intent.getStringExtra("Days");
    qrInfo = intent.getStringExtra("qrInfo");


    //Set the data from the product to the text views
    product_name.setText(Name);
    prodCat.setSelection(getIndexSpinnerItem(prodCat, Category ));
    bestBEntry.setText(BBDate);
    expDays.setText(ExpDays);
    product_desc.setText(Desc);
    QR.setText(qrInfo);



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
        String pDesc = product_desc.getText().toString();
        String pQRInfo = QR.getText().toString();
        //String days = String.valueOf(expDays.getText());
        //int finalDays = Integer.parseInt(days);

        if (!validateForm()) {
            return;
        }
        updateProduct(ProdId, pName, pBBdate, pCategory, pDesc, finalDays, pQRInfo);

    });

    buttonDelete.setOnClickListener(view -> {
        String pName = product_name.getText().toString();
        if (!validateForm()) {
            return;
        }
        deleteProduct(ProdId, pName);

    });


    btnRecipes.setOnClickListener(view -> {

        String type = product_name.getText().toString();
            if (!TextUtils.isEmpty(type)) {
                Intent recipe = new Intent(this, recipeSuggestion.class);
                recipe.putExtra("ProductName", type);
                startActivity(recipe);
                setContentView(R.layout.activity_recipe_suggestion);
            }
            else if (TextUtils.isEmpty(type)) {
                Toast.makeText(this, "Missing product type, you need this to search recipes for", Toast.LENGTH_LONG).show();
            }

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
            String input = countDays+ " day(s)";

            expDays.setText(input);
            finalDays = Integer.parseInt(countDays);
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
    private boolean updateProduct(String id, String name,  String date, String category, String desc, int days,  String qr) {

        //updating product
        Product product = new Product(id, name, date, category, desc, days, qr);
        databaseProducts.child(id).setValue(product);
        Toast.makeText(getApplicationContext(), "Product Updated", Toast.LENGTH_LONG).show();
        Log.d("PRODUCT", "updated product: " + name);
        return true;
    }

    private boolean deleteProduct(String id, String name) {

        //removing product
        databaseProducts.child(id).removeValue();

        Toast.makeText(getApplicationContext(), "Product Deleted", Toast.LENGTH_LONG).show();
        Log.d("PRODUCT", "Deleted product: " + name);
        clearFields();
        Intent i = new Intent(getApplicationContext(),  ProductListings.class);
        startActivity(i);
        setContentView(R.layout.activity_product_listings);

        return true;
    }

    //
    public  void clearFields(){
        product_name.getText().clear();
        bestBEntry.setText("");
        bestBEntry.setHint(getString(R.string.bestBefore));
        expDays.setText("");
        expDays.setHint(getString(R.string.DaysUntilExpiry));
        QR.setText("");
        QR.setHint(getString(R.string.QR_Info));
        product_desc.setText("");
        product_desc.setHint(getString(R.string.product_desc));


    }


    /**
     * Validates that all fields are filled in
     */
    private boolean validateForm() {
        boolean valid = true;
        //Sets errors if fields are not filled in correctly
        String name = product_name.getText().toString();
        if (TextUtils.isEmpty(name)) {
            product_name.setError("Required.");
            valid = false;
        } else {
            product_name.setError(null);
        }


        String pBBdate = bestBEntry.getText().toString();
        if (TextUtils.isEmpty(pBBdate)) {
            bestBEntry.setError("Required.");
            valid = false;
        } else {
            bestBEntry.setError(null);
        }

        String pDesc = product_desc.getText().toString();
        if (TextUtils.isEmpty(pDesc)) {
            product_desc.setText(" ");
        }

        String pQRInfo = QR.getText().toString();
        if (TextUtils.isEmpty(pQRInfo)) {
            QR.setText(" ");
        }

        String days = String.valueOf(expDays.getText());
        if (TextUtils.isEmpty(days)) {
            expDays.setError("Required.");
            valid = false;
        } else {
            expDays.setError(null);
        }

        return valid;
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
            Intent i = new Intent(getApplicationContext(), ProductListings.class);
            startActivity(i);
            setContentView(R.layout.activity_product_listings);
            return(true);
        case R.id.home:
            Intent b = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(b);
            setContentView(R.layout.activity_menu);
            return(true);
        case R.id.add:
            Intent c = new Intent(getApplicationContext(), AddFoodItem.class);
            startActivity(c);
            setContentView(R.layout.activity_add_food_item);
            return(true);
        case R.id.account:
            Intent a = new Intent(getApplicationContext(), UserProfile.class);
            startActivity(a);
            setContentView(R.layout.activity_user_listings);
            return(true);
        case R.id.howToUse:
            Intent d = new Intent(getApplicationContext(), HowToUse.class);
            startActivity(d);
            setContentView(R.layout.activity_how_to_use);
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }
}
