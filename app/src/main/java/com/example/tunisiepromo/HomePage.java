package com.example.tunisiepromo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private androidx.appcompat.widget.SearchView searchView;

    private List<Shoe> shoesList;
    private List<Shoe> originalShoesList;
    private Button athleticCategoryButton;
    private Button casualCategoryButton;
    private Button formalCategoryButton;
    private Button CartButton;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        shoesList = new ArrayList<>();
        productAdapter = new ProductAdapter(shoesList, this);
        recyclerView.setAdapter(productAdapter);
        //set the add product button
        // Set up the "Add" button click listener
        addButton = findViewById(R.id.buttonAddProduct);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to AddProduct activity
                startActivity(new Intent(HomePage.this, AddProduct.class));
            }
        });
        // Set up the SearchView
        int hintColor = Color.GRAY;
        String hintText = "What are you looking for ?";
        SpannableString spannableString = new SpannableString(hintText);
        spannableString.setSpan(new ForegroundColorSpan(hintColor), 0, hintText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set an OnClickListener for the RecyclerView
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // Handle click on the entire item if needed
                        // This can be used in case the image click is not sufficient
                        Shoe selectedShoe = shoesList.get(position);

                        // Create an Intent to start ProductDetailsActivity
                        Intent intent = new Intent(HomePage.this, ProductDetailsActivity.class);

                        // Pass the selected shoe to the ProductDetailsActivity
                        intent.putExtra("shoe", selectedShoe);

                        // Start the ProductDetailsActivity
                        startActivity(intent);
                    }


                })
        );
        // Set up the "Cart" button click listener
        CartButton = findViewById(R.id.cartButton);
        CartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to AddToCartActivity
                startActivity(new Intent(HomePage.this, AddToCartActivity.class));
            }
        });
        // Setting up filtering products by categories
        athleticCategoryButton = findViewById(R.id.AthleticCategoryButton);
        casualCategoryButton = findViewById(R.id.CasualCategoryButton);
        formalCategoryButton = findViewById(R.id.FormalCategoryButton);

        athleticCategoryButton.setOnClickListener(view -> filterByCategory("Athletic"));
        casualCategoryButton.setOnClickListener(view -> filterByCategory("Casual"));
        formalCategoryButton.setOnClickListener(view -> filterByCategory("Formal"));

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("/products");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shoesList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    try {
                        Shoe shoe = dataSnapshot.getValue(Shoe.class);
                        shoesList.add(shoe);
                    } catch (Exception e) {
                        e.printStackTrace();
                        // Log or display an error message for better debugging
                    }
                }
                originalShoesList = new ArrayList<>();
                originalShoesList.addAll(shoesList);
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle databaseError
            }
        });

        // Set up the SearchView
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Clear the current list before adding filtered items
                shoesList.clear();
                shoesList.addAll(filterByName(query));
                productAdapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Clear the current list before adding filtered items
                shoesList.clear();
                shoesList.addAll(filterByName(newText));
                productAdapter.notifyDataSetChanged();
                return true;
            }

            public List<Shoe> filterByName(String newText) {
                List<Shoe> filteredList = new ArrayList<>();

                // If the search query is empty, display the original list
                if (newText.isEmpty()) {
                    filteredList.addAll(originalShoesList);
                } else {
                    // If the search query is not empty, display the filtered list
                    for (Shoe shoe : originalShoesList) {
                        if (shoe.getName().toLowerCase().contains(newText.toLowerCase())) {
                            filteredList.add(shoe);
                        }
                    }
                }

                return filteredList;
            }
        });



    }

    // Filter the list by category
    private void filterByCategory(String category) {
        // Clear the current list before adding filtered items
        shoesList.clear();

        // If the selected category is "All," display the original list
        if (category.equals("All")) {
            shoesList.addAll(originalShoesList);
        } else {
            // If a specific category is selected, display the filtered list
            for (Shoe shoe : originalShoesList) {
                if (shoe.getCategory().equalsIgnoreCase(category)) {
                    shoesList.add(shoe);
                }
            }
        }

        productAdapter.notifyDataSetChanged();
    }
}