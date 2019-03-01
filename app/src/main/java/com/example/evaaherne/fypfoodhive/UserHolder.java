package com.example.evaaherne.fypfoodhive;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.evaaherne.fypfoodhive.Models.Users;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 https://github.com/miketraverso/FaveBakes/blob/feature/app-indexing-finish/app/src/main/res/layout/activity_bakery_details.xml
 */
public class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = db.getReference("users");

    @BindView(R.id.textName) TextView textName;
    @BindView(R.id.textEmail) TextView textEmail;

    private Users users;
    private Context context;


    public UserHolder(Context context, View itemView) {

        super(itemView);
        // Set the context
        this.context = context;
        ButterKnife.bind(this,itemView);
        // Set the "onClick" listener of the holder
        itemView.setOnClickListener(this);

    }

    public void bindUsers(Users users) {

       //Bind the data to the ViewHolder
        this.users = users;
        this.textName.setText(users.getUserName());
        this.textEmail.setText(users.getUserEmail());



    }

    @Override
    public void onClick(View v) {
        //  Handle the onClick event for the ViewHolder

        }




}
