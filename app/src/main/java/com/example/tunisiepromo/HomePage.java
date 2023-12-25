package com.example.tunisiepromo;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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
    private ProductAdapter productAdapter; // Use ProductAdapter, not ProductViewHolder
    private androidx.appcompat.widget.SearchView searchView;

    private List<Shoe> shoesList;
    private List<Shoe> originalShoesList;
    private Button athleticCategoryButton;
    private Button casualCategoryButton;
    private Button formalCategoryButton;


    private Button buttonAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        shoesList = new ArrayList<>();
        productAdapter = new ProductAdapter(shoesList, this); // Corrected usage
        recyclerView.setAdapter(productAdapter);

        int hintColor = Color.GRAY;
        String hintText = "What are you looking for ?";
        SpannableString spannableString = new SpannableString(hintText);
        spannableString.setSpan(new ForegroundColorSpan(hintColor), 0, hintText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);



        //setting up filtering products bu categories
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
                originalShoesList =new ArrayList<>();
                originalShoesList.addAll(shoesList);
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //handle databaseError
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
    private void filterByCategory(String category){
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

