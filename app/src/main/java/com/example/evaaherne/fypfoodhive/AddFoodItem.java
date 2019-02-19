package com.example.evaaherne.fypfoodhive;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evaaherne.fypfoodhive.Models.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.evaaherne.fypfoodhive.app.CHANNEL_ID;

public class AddFoodItem extends BaseActivity {
    ImageButton btnAdd;
    Button btnInventory;
    TextView expDays;
    EditText product_name;
    EditText bestBEntry;
    Spinner spinCategory;

    DatabaseReference databaseProducts;

    int notificationId = 01;

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
        expDays = findViewById(R.id.expDays);
        btnInventory = findViewById(R.id.btnInventory);


        btnAdd.setOnClickListener(v -> {
            if (!validateForm()) {
                return;
            }

            addProduct();
        });

        btnInventory.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), ProductListings.class);
            startActivity(i);
            setContentView(R.layout.activity_product_listings);
        });

        //onClick of bestBEntry
        bestBEntry.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dateDialog = new DatePickerDialog(v.getContext(), datePickerListener, mYear, mMonth, mDay);
            dateDialog.getDatePicker().setMinDate(new Date().getTime());
            dateDialog.show();

        });
    }



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

        String date = bestBEntry.getText().toString();
        if (TextUtils.isEmpty(date)) {
            bestBEntry.setError("Required.");
            valid = false;
        } else {
            bestBEntry.setError(null);
        }

        return valid;
    }
    public void expiryAlert(int expiryDays){
        if (expiryDays == 5 || expiryDays == 4 || expiryDays == 3 || expiryDays == 2 || expiryDays == 1 ) {
            showNotification(expiryDays);
        }

    }

    public void onDayExpiryAlert(int expiryDays){
        if (expiryDays == 0){
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.inventory)
                    .setContentTitle("Expiry Alert")
                    .setContentText("A product will expire today!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
            notificationManager.notify(notificationId, mBuilder.build());
        }
    }

    public void expiredAlert(int expiryDays){
        if (expiryDays <= -1){
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.inventory)
                    .setContentTitle("Expiry Alert")
                    .setContentText("A product has expired" + expiryDays)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
            notificationManager.notify(notificationId, mBuilder.build());
        }
    }


    /**
     * Notification alert for expiry
     * https://developer.android.com/training/notify-user/build-notification
     */
    public void showNotification(int days){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.inventory)
                .setContentTitle("Expiry Alert")
                .setContentText("A product is about to expire in " + days+ " day(s) !")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, mBuilder.build());
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
              expDays.setText(countDays);


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


    /**
     * Add add product to inventory object
     */

    private void addProduct() {
        //String userId;

        String ProdName = product_name.getText().toString().trim();
        String spinCat = spinCategory.getSelectedItem().toString();
        String bestBefore = bestBEntry.getText().toString().trim();
        String expireDays = expDays.getText().toString().trim();
        int finalDays = Integer.parseInt(expireDays);


        //IF THE VIEW IS NOT EMPTY FOR NAME
        if (!TextUtils.isEmpty(ProdName)) {

            String prodId = databaseProducts.push().getKey(); //Get a unique key for the id
            Product product = new Product(prodId, ProdName, bestBefore, spinCat, finalDays ); //OBJ INSTANSTIATION

            //SETS DB VALUES
            databaseProducts.child(prodId).setValue(product);

            expiryAlert(finalDays);
            expiredAlert(finalDays);
           onDayExpiryAlert(finalDays);


            //Toast is a short message that pops up on screen
            Toast.makeText(this, "Product  added", Toast.LENGTH_LONG).show();
            product_name.getText().clear();
            bestBEntry.getText().clear();

        } else if(!TextUtils.isEmpty(ProdName)&&!TextUtils.isEmpty(bestBefore)) {
            Toast.makeText(this, "Missing details! Please fill in all fields.", Toast.LENGTH_LONG).show();
        }
    }


}
