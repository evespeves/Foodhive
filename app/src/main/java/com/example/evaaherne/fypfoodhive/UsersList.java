package com.example.evaaherne.fypfoodhive;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.evaaherne.fypfoodhive.Models.Users;

import java.util.List;

/** Some code gathered from for this class to retrieve data from
 * https://www.youtube.com/watch?v=jEmq1B1gveM&t=74s&ab_channel=SimplifiedCoding
 */

//ARRAY ADAPTER FOR THE LIST
public class UsersList extends ArrayAdapter<Users> {

    //Adapter class to bridge between database and listview
    //Defines the way that it will be viewed in the list view
    private Activity context; //Context allows access to application-specific resources and classes,
    // as well as calls for application-level operations such as launching activities
    private List<Users> userList; //List<> list of unknowns


    public UsersList(Activity context, List<Users> userList) {
        //Constructor
        //Super class
        super(context, R.layout.list_layout, userList);
        this.context = context;
        this.userList = userList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        //Putting items into the listview using layoutInflator - another way to create views.
        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);
        //Instantiates the views
        TextView textViewName = listViewItem.findViewById(R.id.textViewName);
        TextView textViewNut = listViewItem.findViewById(R.id.textViewNut);
        TextView textViewDairy = listViewItem.findViewById(R.id.textViewDairy);
        TextView textViewGluten = listViewItem.findViewById(R.id.textViewGluten);

        Users users = userList.get(position);
        //gets values for the data
        textViewName.setText(users.getUserName());


        return listViewItem;

    }

}

