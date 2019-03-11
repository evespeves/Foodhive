package com.example.evaaherne.fypfoodhive;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.example.evaaherne.fypfoodhive.Models.Product;
import com.example.evaaherne.fypfoodhive.Models.Users;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UserProfile extends AppCompatActivity {

    //    //GET DB INSTANCE AND REFERENCE DB
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = db.getReference("users");

    private FirebaseAuth mAuth  = FirebaseAuth.getInstance();

    @BindView(R.id.listings_view)
    RecyclerView listingsView;
    List<Users> users;
    Context context;
    Button signOut;

    private boolean ascending = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inflating the activity
        setContentView(R.layout.activity_user_listings);
        //Bind resources to respective fields
        ButterKnife.bind(this);
        this.context = this;

       signOut =  findViewById(R.id.signOut);


        loadUser();
        //Intialize product adapter
        UserAdapter adapter = new UserAdapter(this, R.layout.list_layout, users);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
       VerticalSpaceItemDecorator itemDecorator = new VerticalSpaceItemDecorator((int) getResources().getDimension(R.dimen.spacer_20));
       // ShadowVerticalSpaceItemDecorator shadowItemDecorator = new ShadowVerticalSpaceItemDecorator(this, R.drawable.drop_shadow);

        //Initialize layoutmanager
        listingsView.setHasFixedSize(true);
        listingsView.setLayoutManager(layoutManager);
       // listingsView.addItemDecoration(shadowItemDecorator);
        listingsView.addItemDecoration(itemDecorator);
        listingsView.setAdapter(adapter);

        signOut.setOnClickListener(v -> {
            signOut();
            Intent i = new Intent(getApplicationContext(), login.class);
            startActivity(i);
            setContentView(R.layout.activity_login);
        });
    }
    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    // [END on_start_check_user]




    private void loadUser() {

        //DB REFERENCE ADD EVENT LISTENER

        users = new ArrayList<>();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String useruid = user.getUid();
        myRef.child(useruid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear(); //Clears before opening

                    //GATHERS VALUES & ADDS TO LIST
                    Users user= dataSnapshot.getValue(Users.class);
                    users.add(user);
                    Log.d("User", "user: " + users);

                //ADAPTER = MIDDLE PERSON BETWEEN THE DB AND THE VIEWS
                listingsView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "Failed to read value.", databaseError.toException());
            }
        });

    }

    //Updates the data
    private void updateUI(FirebaseUser user) {


    }


    private void signOut() {
        mAuth.signOut();
        updateUI(null);

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


