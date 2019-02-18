package com.example.evaaherne.fypfoodhive;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.evaaherne.fypfoodhive.app.CHANNEL_ID;

public class ProductListings extends AppCompatActivity {

    //    //GET DB INSTANCE AND REFERENCE DB
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = db.getReference("Inventory");

    @BindView(R.id.listings_view)
    RecyclerView listingsView;
    List<Product> products;
    Context context;

    int notificationId = 01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inflating the activity
        setContentView(R.layout.activity_product_listings);
        //Bind resources to respective fields
        ButterKnife.bind(this);
        this.context = this;

        loadProducts();
        //Intialize product adapter
        ProductAdapter adapter = new ProductAdapter(this, R.layout.list_item_product, products);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        VerticalSpaceItemDecorator itemDecorator = new VerticalSpaceItemDecorator((int) getResources().getDimension(R.dimen.spacer_20));
        ShadowVerticalSpaceItemDecorator shadowItemDecorator = new ShadowVerticalSpaceItemDecorator(this, R.drawable.drop_shadow);

        //Initialize layoutmanager
        listingsView.setHasFixedSize(true);
        listingsView.setLayoutManager(layoutManager);
        listingsView.addItemDecoration(shadowItemDecorator);
        listingsView.addItemDecoration(itemDecorator);
        listingsView.setAdapter(adapter);




    }
    public void expiryAlert(int expiryDays) {
        if (expiryDays == 5 || expiryDays == 4 || expiryDays == 3 || expiryDays == 2 || expiryDays == 1) {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.inventory)
                    .setContentTitle("Expiry Alert")
                    .setContentText("A product will expire in " + expiryDays + " day(s)")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
            notificationManager.notify(notificationId, mBuilder.build());
        }

    }

    public void onDayExpiryAlert(int expiryDays) {
        if (expiryDays == 0) {
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

    public void expiredAlert(int expiryDays) {
        if (expiryDays <= -1) {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.inventory)
                    .setContentTitle("Expiry Alert")
                    .setContentText("A product has expired by " + expiryDays + " day(s)")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
            notificationManager.notify(notificationId, mBuilder.build());
        }
    }

    private void loadProducts() {

        //DB REFERENCE ADD EVENT LISTENER
        products = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                products.clear(); //Clears before opening

                for (DataSnapshot prodSnapshot : dataSnapshot.getChildren()) {
                    //GATHERS VALUES & ADDS TO LIST
                    Product product = prodSnapshot.getValue(Product.class);
                    products.add(product);
                    Log.d("PRODUCT", "product: " + product);

                }
                //ADAPTER = MIDDLE PERSON BETWEEN THE DB AND THE VIEWS
                // ProductAdapter adapter = new ProductAdapter(ProductListings.this, products);
                //FILLS ADAPTER
                listingsView.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "Failed to read value.", databaseError.toException());
            }
        });

    }

    private boolean updateProduct(String id, String name, String category, String bbdate, int days) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Inventory").child(id);

        //updating artist
        Product product = new Product(id, name, category, bbdate, days);
        dR.setValue(product);
        Toast.makeText(getApplicationContext(), "Product Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    public void showUpdateDeleteDialog(final String prodId, String prodName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_product, null );
        dialogBuilder.setView(dialogView);

        //onClick of bestBEntry

         EditText product_name = dialogView.findViewById(R.id.product_name);
        Spinner prodCat = dialogView.findViewById(R.id.prod_cat);
         EditText bestBEntry = dialogView.findViewById(R.id.bestBEntry);
         EditText prod_exp_days = dialogView.findViewById(R.id.prod_exp_days);


        final Button buttonUpdate = dialogView.findViewById(R.id.buttonUpdateProduct);
        final Button buttonDelete = dialogView.findViewById(R.id.buttonDeleteProduct);


        bestBEntry.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dateDialog = new DatePickerDialog(v.getContext(), datePickerListener, mYear, mMonth, mDay);
            dateDialog.getDatePicker().setMinDate(new Date().getTime());
            dateDialog.show();

        });

        dialogBuilder.setTitle("Updating product : " + prodName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ProdName = product_name.getText().toString().trim();
                String spinCat = prodCat.getSelectedItem().toString();
                String bestBefore = bestBEntry.getText().toString().trim();
                String expireDays = prod_exp_days.getText().toString().trim();
                int finalDays = Integer.parseInt(expireDays);
                if (!TextUtils.isEmpty(ProdName)) {
                    updateProduct(prodId,ProdName , spinCat,bestBefore, finalDays);
                    b.dismiss();
                }
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
            LayoutInflater inflater = getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.update_product, null);
            EditText bestBEntry = dialogView.findViewById(R.id.bestBEntry);
            EditText prod_exp_days = dialogView.findViewById(R.id.prod_exp_days);
            bestBEntry.setText(format);

            String todayDate = new SimpleDateFormat("dd/MMM/YYYY", Locale.getDefault()).format(new Date());
            String countDays = getCountOfDays(todayDate, format);
            prod_exp_days.setText(countDays);
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


}
