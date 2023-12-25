package com.example.tunisiepromo;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
    private List<Shoe> shoesList;
    private Button buttonAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        shoesList = new ArrayList<>();
        productAdapter = new ProductAdapter(shoesList, this);
        recyclerView.setAdapter(productAdapter);


        int hintColor = Color.GRAY;
        String hintText = "What are you looking for ?";
        SpannableString spannableString = new SpannableString(hintText);
        spannableString.setSpan(new ForegroundColorSpan(hintColor), 0, hintText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //searchView.setQueryHint(spannableString);

        // Set up the SearchView
        //setupSearchView();

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
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //handle databaseError
            }


        });
    }

    }




