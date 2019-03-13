package com.example.evaaherne.fypfoodhive;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.evaaherne.fypfoodhive.Models.Product;
import com.example.evaaherne.fypfoodhive.activities.HowToUse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListings extends AppCompatActivity {

    private FirebaseAuth mAuth;
    //    //GET DB INSTANCE AND REFERENCE DB
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    String useruid = user.getUid();

     DatabaseReference myRef = db.getReference("Inventory").child(useruid);

    @BindView(R.id.listings_view)
    RecyclerView listingsView;
    List<Product> products;
    Context context;




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
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                products.clear(); //Clears before opening

                for (DataSnapshot prodSnapshot : dataSnapshot.getChildren()) {
                    //GATHERS VALUES & ADDS TO LIST
                    Product product = prodSnapshot.getValue(Product.class);
                    products.add(product);
                    Log.d("PRODUCT", "product: " + product);

                }
                //ADAPTER = MIDDLE PERSON BETWEEN THE DB AND THE VIEWS
                sortByDays();
                Objects.requireNonNull(listingsView.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "Failed to read value.", databaseError.toException());
            }
        });

    }



    /** Sort data by days
     * https://www.youtube.com/watch?v=1C83CUjLOu8&ab_channel=MyHexaville
     */
    private void sortByDays(){
        Collections.sort(products, (l1, l2) -> {
            if (l1.getExpDay() > l2.getExpDay()){
                return 1;
            }
            else if (l1.getExpDay() < l2.getExpDay()){
                return -1;
            }
            else {
                return 0;
            }
        });
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
            startActivity(new Intent(getApplicationContext(), ProductListings.class));
            return(true);
        case R.id.home:
            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            return(true);
        case R.id.add:
            startActivity(new Intent(getApplicationContext(), AddFoodItem.class));
            return(true);
        case R.id.account:
            startActivity(new Intent(getApplicationContext(), UserProfile.class));
            return(true);
        case R.id.howToUse:
            startActivity(new Intent(getApplicationContext(), HowToUse.class));
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }
}
