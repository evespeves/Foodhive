package com.example.evaaherne.fypfoodhive;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.evaaherne.fypfoodhive.Models.Product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.evaaherne.fypfoodhive.app.CHANNEL_ID;

/**
 https://github.com/miketraverso/FaveBakes/blob/feature/app-indexing-finish/app/src/main/res/layout/activity_bakery_details.xml
 */
public class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

  @BindView(R.id.prod_name) TextView productName;
    @BindView(R.id.prod_cat) TextView prodCategory;
    @BindView(R.id.prod_bb_date) TextView prodExpDate;
    @BindView(R.id.prod_exp_days) TextView expDays;

    private Product product;
    private Context context;

    int notificationId = 01;

    public ProductHolder(Context context, View itemView) {

        super(itemView);
        // Set the context
        this.context = context;
        ButterKnife.bind(this,itemView);
        // Set the "onClick" listener of the holder
        itemView.setOnClickListener(this);

    }

    public void bindProducts(Product product) {

       //Bind the data to the ViewHolder
        this.product = product;
        this.productName.setText(product.prodName);
        this.prodCategory.setText(product.prodCategory);
        this.prodExpDate.setText(product.prodBBDate);

        //Todays date and expiry date to get count of days
        String todayDate = new SimpleDateFormat("dd/MMM/YYYY", Locale.getDefault()).format(new Date());
        String expireDays = getCountOfDays(todayDate, product.prodBBDate);
         int countDays = Integer.parseInt(expireDays);
        this.expDays.setText(String.valueOf(countDays));

        //Changes colour of text if item due to expire
        if (countDays <= -1){
            expDays.setTextColor(ContextCompat.getColor(context, R.color.expired));
        }
        if (countDays >= 0){
            expDays.setTextColor(ContextCompat.getColor(context, R.color.gray));
        }

        expiryAlert(countDays);

    }

    @Override
    public void onClick(View v) {
        //  Handle the onClick event for the ViewHolder
        if (this.product!= null) {
            //declarations for text associated with the views
           String prodName = this.product.prodName;
           String id = this.product.prodId;
           String category = this.product.prodCategory;
           String bbDate = this.product.prodBBDate;
           int expDays = this.product.expDay;

            /**
             * Bring data from one item to another
             * https://stackoverflow.com/questions/4233873/how-do-i-get-extra-data-from-intent-on-android
             */
            Intent intent = new Intent(context, UpdateProduct.class);
            intent.putExtra("ProdId", id);
            intent.putExtra("Name", prodName);
            intent.putExtra("Category", category);
            intent.putExtra("Date", bbDate);
            intent.putExtra("ExpDays", expDays);

            context.startActivity(intent);
//
        }

    }



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


    public void expiryAlert(int expiryDays) {

        if (expiryDays == 5 || expiryDays == 4 || expiryDays == 3 || expiryDays == 2 || expiryDays == 1) {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.inventory)
                    .setContentTitle("Expiry Alert")
                    .setContentText("A product will expire in " + expiryDays + " day(s)")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

// notificationId is a unique int for each notification that you must define
            notificationManager.notify(notificationId, mBuilder.build());
        }

    }

    public void onDayExpiryAlert(int expiryDays) {
        if (expiryDays == 0) {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.inventory)
                    .setContentTitle("Expiry Alert")
                    .setContentText("product will expire today!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

// notificationId is a unique int for each notification that you must define
            notificationManager.notify(notificationId, mBuilder.build());
        }
    }

    public void expiredAlert(int expiryDays) {
        if (expiryDays <= -1) {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.inventory)
                    .setContentTitle("Expiry Alert")
                    .setContentText("A product has expired by " + expiryDays + " day(s)")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

// notificationId is a unique int for each notification that you must define
            notificationManager.notify(notificationId, mBuilder.build());
        }
    }
}
