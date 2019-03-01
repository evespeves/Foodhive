package com.example.evaaherne.fypfoodhive;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.evaaherne.fypfoodhive.Models.Product;
import com.example.evaaherne.fypfoodhive.Models.Users;
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


    private boolean ascending = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inflating the activity
        setContentView(R.layout.activity_user_listings);
        //Bind resources to respective fields
        ButterKnife.bind(this);
        this.context = this;

        loadUser();
        //Intialize product adapter
        UserAdapter adapter = new UserAdapter(this, R.layout.list_layout, users);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
       // VerticalSpaceItemDecorator itemDecorator = new VerticalSpaceItemDecorator((int) getResources().getDimension(R.dimen.spacer_20));
       // ShadowVerticalSpaceItemDecorator shadowItemDecorator = new ShadowVerticalSpaceItemDecorator(this, R.drawable.drop_shadow);

        //Initialize layoutmanager
        listingsView.setHasFixedSize(true);
        listingsView.setLayoutManager(layoutManager);
       // listingsView.addItemDecoration(shadowItemDecorator);
       // listingsView.addItemDecoration(itemDecorator);
        listingsView.setAdapter(adapter);
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


//    /** Sort data by days
//     * https://www.youtube.com/watch?v=1C83CUjLOu8&ab_channel=MyHexaville
//     */
//    private void sortByDays(){
//        Collections.sort(products, (l1, l2) -> {
//            if (l1.getExpDay() > l2.getExpDay()){
//                return 1;
//            }
//            else if (l1.getExpDay() < l2.getExpDay()){
//                return -1;
//            }
//            else {
//                return 0;
//            }
//        });
    }


