package com.example.tunisiepromo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Shoe> shoesList;
    private Context context;

    // Search view

    // Constructor with search functionality
    public ProductAdapter(List<Shoe> shoesList, Context context) {
        this.shoesList = shoesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Shoe shoe = shoesList.get(position);

        // Bind data to views
        holder.productName.setText(shoe.getName());

        // Format and display the price with currency symbol
        String formattedPrice = formatPrice(shoe.getPriceAP());
        holder.productPrice.setText(formattedPrice);

        // Display priceBP with strikethrough text
        String formattedPriceBP = formatPrice(shoe.getPriceBP());
        holder.productPriceBeforePromo.setText(formattedPriceBP);
        holder.productPriceBeforePromo.setPaintFlags(holder.productPriceBeforePromo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.productCategory.setText(shoe.getCategory());
        String formattedPromotion = formatPromotion(shoe.getPromotionPercentage());
        holder.promotion.setText(formattedPromotion);

        // Use picasso to load the image from the URL
        Picasso.get().load(shoe.getImage_url()).into(holder.productImage);


        // Set up the click listener for the product item to enable navigation to the product details page
        // Set an OnClickListener for the product image
        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected shoe
                Shoe selectedShoe = shoesList.get(position);

                // Create an Intent to start ProductDetailsActivity
                Intent intent = new Intent(context, ProductDetailsActivity.class);

                // Pass the selected shoe to the ProductDetailsActivity using Intent
                intent.putExtra("selectedShoe", selectedShoe);

                // Start the ProductDetailsActivity
                context.startActivity(intent);
            }
        });




    }

    private String formatPrice(double price) {
        // Example: Format the price with currency symbol and two decimal places
        DecimalFormat decimalFormat = new DecimalFormat("$#.00");
        return decimalFormat.format(price);
    }

    private String formatPromotion(double promotion) {
        // Example: Format the promotion as percentage with " OFF"
        return String.format("%%%d OFF", (int) promotion);
    }

    @Override
    public int getItemCount() {
        return shoesList.size();
    }



    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        TextView productPriceBeforePromo; // Added for displaying priceBP with strikethrough
        TextView productCategory;
        TextView promotion;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.imageViewProduct);
            productName = itemView.findViewById(R.id.textViewProductName);
            productPrice = itemView.findViewById(R.id.textViewProductPrice);
            productPriceBeforePromo = itemView.findViewById(R.id.textViewProductPriceBeforePromo);
            productCategory = itemView.findViewById(R.id.textViewProductCategory);
            promotion = itemView.findViewById(R.id.textViewPromotionPercentage);
        }
    }
}
