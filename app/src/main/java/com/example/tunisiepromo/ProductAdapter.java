package com.example.tunisiepromo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Shoe> shoesList;
    private Context context;

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
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Shoe shoe = shoesList.get(position);

        // Bind data to views
        holder.productName.setText(shoe.getName());

        // Format and display the price with currency symbol
        String formattedPrice = formatPrice(shoe.getPriceAP());
        holder.productPrice.setText(formattedPrice);

        holder.productCategory.setText(shoe.getCategory());
        holder.promotion.setText(String.valueOf(shoe.getPromotionPercentage()));

        // Use Glide to load the image from the URL
        Glide.with(context)
                .load(shoe.getImage_url())
                .into(holder.productImage);
    }

    private String formatPrice(double price) {
        // Example: Format the price with currency symbol and two decimal places
        DecimalFormat decimalFormat = new DecimalFormat("$#.00");
        return decimalFormat.format(price);
    }

    @Override
    public int getItemCount() {
        return shoesList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        TextView productCategory;
        TextView promotion;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.imageViewProduct);
            productName = itemView.findViewById(R.id.textViewProductName);
            productPrice = itemView.findViewById(R.id.textViewProductPrice);
            productCategory = itemView.findViewById(R.id.textViewProductCategory);
            promotion = itemView.findViewById(R.id.textViewPromotionPercentage);
        }
    }
}
