package com.example.evaaherne.fypfoodhive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ValueEventListener;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {

    private  List<Product> products;
    private Context context;
    private int itemResource;

    public ProductAdapter(Context context, int itemResource, List<Product> products) {

        // 1. Initialize our adapter
        this.products = products;
        this.context = context;
        this.itemResource = itemResource;
    }



    // 2. Override the onCreateViewHolder method
    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 3. Inflate the view and return the new ViewHolder
        View view = LayoutInflater.from(parent.getContext())
                .inflate(this.itemResource, parent, false);
        return new ProductHolder(this.context, view);
    }

    // 4. Override the onBindViewHolder method
    @Override
    public void onBindViewHolder(ProductHolder holder, int position) {

        // 5. Use position to access the correct Bakery object
        Product product = this.products.get(position);

        // 6. Bind the bakery object to the holder
        holder.bindProducts(product);
    }

    @Override
    public int getItemCount() {

        return this.products.size();
    }
}

