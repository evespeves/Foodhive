package com.example.evaaherne.fypfoodhive;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
public class User_detail extends AppCompatActivity {
    //GET DB INSTANCE AND REFERENCE DB
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = db.getReference("users");

    ListView listViewUsers;
    List<Users> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        //VIEW
        listViewUsers = findViewById(R.id.listViewUsers);
        //ARRAY LIST
        userList =  new ArrayList<>();

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
                for (DataSnapshot usersSnapshot : dataSnapshot.getChildren()) {
                    //GATTHERS VALUES & ADDS TO LIST
                    Users users = usersSnapshot.getValue(Users.class);
                    userList.add(users);
                }
                //ADAPTER = MIDDLE PERSON BETWEEN THE DB AND THE VIEWS
                UsersList adapter = new UsersList(User_detail.this,userList);
                //FILLS ADAPTER
                listViewUsers.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
