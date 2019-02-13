package com.example.evaaherne.fypfoodhive;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.evaaherne.fypfoodhive.app.CHANNEL_ID;

public class AddFoodItem extends BaseActivity {
    ImageButton btnAdd;
    Button btnInventory;
    TextView txtExpiryDay;
    TextView txtExpiryMonth;
    TextView txtExpiryYear;
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
        txtExpiryDay = findViewById(R.id.txtExpiryDay);
        txtExpiryMonth = findViewById(R.id.txtExpiryMonth);
        txtExpiryYear = findViewById(R.id.txtExpiryYear);




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

        //onClick of bestBEntry
        bestBEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dateDialog = new DatePickerDialog(v.getContext(), datePickerListener, mYear, mMonth, mDay);
                dateDialog.getDatePicker().setMinDate(new Date().getTime());
                dateDialog.show();

            }
        });
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
            String format = new SimpleDateFormat("dd/MMM/YYYY").format(c.getTime());
            bestBEntry.setText(format);

            String todayDate = new SimpleDateFormat("dd/MMM/YYYY", Locale.getDefault()).format(new Date());
              String countDays = getCountOfDays(todayDate, format);
              txtExpiryDay.setText(countDays);
        }
    };





    /** https://stackoverflow.com/questions/42553017/android-calculate-days-between-two-dates **/

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

        return ("" + (int) dayCount + " Days");
    }



    private void addProduct() {
        //String userId;
        String ProdName = product_name.getText().toString().trim();
        String spinCat = spinCategory.getSelectedItem().toString();
        String bestBefore = bestBEntry.getText().toString().trim();
        String expDay = txtExpiryDay.getText().toString();

        //IF THE VIEW IS NOT EMPTY FOR NAME
        if (!TextUtils.isEmpty(ProdName)) {

            String prodId = databaseProducts.push().getKey(); //Get a unique key for the id
            Product product = new Product(prodId, ProdName, bestBefore, spinCat, expDay ); //OBJ INSTANSTIATION

            //SETS DB VALUES
            databaseProducts.child(prodId).setValue(product);


            //Toast is a short message that pops up on screen
            Toast.makeText(this, "Product  added", Toast.LENGTH_LONG).show();
            product_name.getText().clear();
            bestBEntry.getText().clear();

        } else if(!TextUtils.isEmpty(ProdName)&&!TextUtils.isEmpty(bestBefore)) {
            Toast.makeText(this, "Missing details! Please fill in all fields.", Toast.LENGTH_LONG).show();
        }
    }
}
