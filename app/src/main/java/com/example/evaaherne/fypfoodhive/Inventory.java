package com.example.evaaherne.fypfoodhive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Some code gathered from link below to retrieve data from firebase
 * https://www.youtube.com/watch?v=jEmq1B1gveM&t=74s&ab_channel=SimplifiedCoding
 */
public class Inventory extends AppCompatActivity {

    //GET DB INSTANCE AND REFERENCE DB
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = db.getReference("Inventory");

    private NotificationManagerCompat notificationManager;


    private final String CHANNEL_ID = "personal_notifications";
    private final int NOTIFICATION_ID = 001;

    ListView listViewProduct;
    List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        //VIEW
        listViewProduct = findViewById(R.id.listViewProduct);
        //ARRAY LIST
        productList =  new ArrayList<>();

        notificationManager = NotificationManagerCompat.from(this);
    }

    public void displayNotifcation(View view)
    {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        builder.setSmallIcon(R.mipmap.inventory);
        builder.setContentTitle("Food about to expire!!");
        builder.setContentText("Food item will expire soon, check inventory to see what");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    @Override
    protected void onStart() {
        super.onStart();

        //DB REFERENCE ADD EVENT LISTENER
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //userList.clear(); //Clears before opening

                //GETS SNAPSHOT OF DATA IN DB
                for (DataSnapshot prodSnapshot : dataSnapshot.getChildren()) {
                    //GATHERS VALUES & ADDS TO LIST
                    Product product = prodSnapshot.getValue(Product.class);
                    productList.add(product);
                }
                //ADAPTER = MIDDLE PERSON BETWEEN THE DB AND THE VIEWS
                ProductList adapter = new ProductList(Inventory.this,productList);
                //FILLS ADAPTER
                listViewProduct.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
