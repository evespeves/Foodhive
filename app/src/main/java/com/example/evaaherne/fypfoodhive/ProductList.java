package com.example.evaaherne.fypfoodhive;
//
//import android.app.Activity;
//import android.app.Notification;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import org.w3c.dom.Text;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.NotificationCompat;
//import androidx.core.app.NotificationManagerCompat;
//import androidx.recyclerview.widget.RecyclerView;
//
//import static com.example.evaaherne.fypfoodhive.app.CHANNEL_ID;
//
//
//
///** Some code gathered from for this class to retrieve data from
// * https://www.youtube.com/watch?v=jEmq1B1gveM&t=74s&ab_channel=SimplifiedCoding
// */
//
//
public class ProductList  {
//
//    //Adapter class to bridge between database and listview
//    //Defines the way that it will be viewed in the list view
//    private Activity context; //Context allows access to application-specific resources and classes,
//    // as well as calls for application-level operations such as launching activities
//    private List<Product> productList; //List<> list of unknowns
//
//    // For notification
//    int notificationId = 01;
//
//    public ProductList(Activity context, List<Product> productList) {
//        //Constructor
//        //Super class
//        super(context, R.layout.activity_product_list, productList);
//        this.context = context;
//        this.productList = productList;
//    }
//
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        LayoutInflater inflater = context.getLayoutInflater();
//        //Putting items into the listview using layoutInflator - another way to create views.
//        View recycleViewItem = inflater.inflate(R.layout.activity_product_list, null, true);
//        //Instantiates the views
//        TextView textProductName = recycleViewItem.findViewById(R.id.textProductName);
//        TextView textBestBefore = recycleViewItem.findViewById(R.id.textBestBefore);
//        TextView textCategory = recycleViewItem.findViewById(R.id.textCategory);
//        TextView textExpDate = recycleViewItem.findViewById(R.id.textExpDay);
//        TextView textView = recycleViewItem.findViewById(R.id.textView);
//
//
//        Product product = productList.get(position);
//        //gets values for the data
//        textProductName.setText(product.getProdName());
//        textBestBefore.setText(product.getProdBBDate());
//        textCategory.setText(product.getProdCategory());
//        textView.setText(product.getExpDay());
//
//        String expireDate = product.getProdBBDate();
//        String todayDate = new SimpleDateFormat("dd/MMM/YYYY", Locale.getDefault()).format(new Date());
//        String expDay = getCountOfDays(todayDate, expireDate);
//        textExpDate.setText(expDay);
//
//        return recycleViewItem;
//
//    }
//
//
//
//
//    /** Calculate total number of days until expiry of product
//     *  https://stackoverflow.com/questions/42553017/android-calculate-days-between-two-dates **/
//
//    public String getCountOfDays(String createdDateString, String expireDateString) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault());
//
//        Date createdConvertedDate = null, expireCovertedDate = null, todayWithZeroTime = null;
//        try {
//            createdConvertedDate = dateFormat.parse(createdDateString);
//            expireCovertedDate = dateFormat.parse(expireDateString);
//
//            Date today = new Date();
//
//            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        int cYear = 0, cMonth = 0, cDay = 0;
//
//        if (createdConvertedDate.after(todayWithZeroTime)) {
//            Calendar cCal = Calendar.getInstance();
//            cCal.setTime(createdConvertedDate);
//            cYear = cCal.get(Calendar.YEAR);
//            cMonth = cCal.get(Calendar.MONTH);
//            cDay = cCal.get(Calendar.DAY_OF_MONTH);
//
//        } else {
//            Calendar cCal = Calendar.getInstance();
//            cCal.setTime(todayWithZeroTime);
//            cYear = cCal.get(Calendar.YEAR);
//            cMonth = cCal.get(Calendar.MONTH);
//            cDay = cCal.get(Calendar.DAY_OF_MONTH);
//        }
//
//
//    /*Calendar todayCal = Calendar.getInstance();
//    int todayYear = todayCal.get(Calendar.YEAR);
//    int today = todayCal.get(Calendar.MONTH);
//    int todayDay = todayCal.get(Calendar.DAY_OF_MONTH);
//    */
//
//        Calendar eCal = Calendar.getInstance();
//        eCal.setTime(expireCovertedDate);
//
//        int eYear = eCal.get(Calendar.YEAR);
//        int eMonth = eCal.get(Calendar.MONTH);
//        int eDay = eCal.get(Calendar.DAY_OF_MONTH);
//
//        Calendar date1 = Calendar.getInstance();
//        Calendar date2 = Calendar.getInstance();
//
//        date1.clear();
//        date1.set(cYear, cMonth, cDay);
//        date2.clear();
//        date2.set(eYear, eMonth, eDay);
//
//        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();
//
//        float dayCount = (float) diff / (24 * 60 * 60 * 1000);
//        return ((int) dayCount + "");
//    }
//
//
//
//
}
