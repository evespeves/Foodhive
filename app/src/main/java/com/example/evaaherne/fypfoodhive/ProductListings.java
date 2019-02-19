package com.example.evaaherne.fypfoodhive;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.evaaherne.fypfoodhive.Models.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListings extends AppCompatActivity {

    //    //GET DB INSTANCE AND REFERENCE DB
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = db.getReference("Inventory");

    @BindView(R.id.listings_view)
    RecyclerView listingsView;
    List<Product> products;
    Context context;

    private boolean ascending = true;

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
                listingsView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "Failed to read value.", databaseError.toException());
            }
        });

    }










}
