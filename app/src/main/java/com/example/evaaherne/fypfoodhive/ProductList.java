package com.example.evaaherne.fypfoodhive;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/** Some code gathered from for this class to retrieve data from
 * https://www.youtube.com/watch?v=jEmq1B1gveM&t=74s&ab_channel=SimplifiedCoding
 */

public class ProductList extends ArrayAdapter<Product>{

    //Adapter class to bridge between database and listview
    //Defines the way that it will be viewed in the list view
    private Activity context; //Context allows access to application-specific resources and classes,
    // as well as calls for application-level operations such as launching activities
    private List<Product> productList; //List<> list of unknowns


    public ProductList(Activity context, List<Product> productList) {
        //Constructor
        //Super class
        super(context, R.layout.activity_product_list, productList);
        this.context = context;
        this.productList = productList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        //Putting items into the listview using layoutInflator - another way to create views.
        View listViewItem = inflater.inflate(R.layout.activity_product_list, null, true);
        //Instantiates the views
        TextView textProductName = listViewItem.findViewById(R.id.textProductName);
        TextView textBestBefore = listViewItem.findViewById(R.id.textBestBefore);
        TextView textCategory = listViewItem.findViewById(R.id.textCategory);


        Product product = productList.get(position);
        //gets values for the data
        textProductName.setText(product.getProdName());
        textBestBefore.setText(product.getProdBBDate());
        textCategory.setText(product.getProdCategory());

        return listViewItem;

    }
}
