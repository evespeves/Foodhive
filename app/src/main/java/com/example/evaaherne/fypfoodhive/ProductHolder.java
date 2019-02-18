package com.example.evaaherne.fypfoodhive;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

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

    public ProductHolder(Context context, View itemView) {

        super(itemView);
        // 1. Set the context
        this.context = context;
        ButterKnife.bind(this,itemView);
        // 3. Set the "onClick" listener of the holder
        itemView.setOnClickListener(this);

    }

    public void bindProducts(Product product) {

        // 4. Bind the data to the ViewHolder
        this.product = product;
        this.productName.setText(product.prodName);
        this.prodCategory.setText(product.prodCategory);
        this.prodExpDate.setText(product.prodBBDate);

        String todayDate = new SimpleDateFormat("dd/MMM/YYYY", Locale.getDefault()).format(new Date());
        String expireDays = getCountOfDays(todayDate, product.prodBBDate);
         int countDays = Integer.parseInt(expireDays);
        this.expDays.setText(String.valueOf(countDays));

        if (countDays <= -1){

            expDays.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
    }

    @Override
    public void onClick(View v) {
        // 5. Handle the onClick event for the ViewHolder
        if (this.product!= null) {
           // Toast.makeText(this.context, "Clicked on " + this.product.prodName, Toast.LENGTH_SHORT ).show();
            Intent intent = new Intent(context, UpdateProduct.class);

            intent.putExtra("Name", this.product.prodName);
            intent.putExtra("Category", this.product.prodCategory);
            intent.putExtra("BBDate", this.product.prodBBDate);
            intent.putExtra("ExpDays", this.product.expDay);
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


}
