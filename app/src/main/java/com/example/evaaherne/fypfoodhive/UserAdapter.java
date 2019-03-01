package com.example.evaaherne.fypfoodhive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.evaaherne.fypfoodhive.Models.Users;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends RecyclerView.Adapter<UserHolder> {

    private  List<Users> users;
    private Context context;
    private int itemResource;

    public UserAdapter(Context context, int itemResource, List<Users> users) {

        // 1. Initialize our adapter
        this.users = users;
        this.context = context;
        this.itemResource = itemResource;
    }



    // 2. Override the onCreateViewHolder method
    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 3. Inflate the view and return the new ViewHolder
        View view = LayoutInflater.from(parent.getContext())
                .inflate(this.itemResource, parent, false);
        return new UserHolder(this.context, view);
    }

    // 4. Override the onBindViewHolder method
    @Override
    public void onBindViewHolder(UserHolder holder, int position) {

        // 5. Use position to access the correct Bakery object
        Users user = this.users.get(position);

        // 6. Bind the bakery object to the holder
        holder.bindUsers(user);
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }
}

