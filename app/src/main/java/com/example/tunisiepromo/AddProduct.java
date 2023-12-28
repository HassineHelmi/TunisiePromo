package com.example.tunisiepromo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
public class AddProduct extends AppCompatActivity {

    private EditText productNameEditText;
    private EditText productDescriptionEditText;
    private EditText priceAPEditText;
    private EditText priceBPEditText;
    private EditText promotionPercentageEditText;
    private CheckBox sizeCheckBox;
    private ImageView imageViewProduct;
    private Button buttonAddProduct;
    private Uri selectedImageUri;
    private Spinner productCategorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product_page);

        // Setting the elements of the page by id
        productNameEditText = findViewById(R.id.productNameEditText);
        productDescriptionEditText = findViewById(R.id.productDescriptionEditText);
        priceAPEditText = findViewById(R.id.priceEditText);
        promotionPercentageEditText = findViewById(R.id.promotionEditText);
        sizeCheckBox = findViewById(R.id.sizeCheckBox);
        imageViewProduct = findViewById(R.id.imageViewProduct);
        buttonAddProduct = findViewById(R.id.buttonAddProduct);
        productCategorySpinner = findViewById(R.id.productcategoryspinner);

        // Set up the category Spinner
        String[] categories = getResources().getStringArray(R.array.category_entries);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        productCategorySpinner.setAdapter(adapter);

        Button selectImageButton = findViewById(R.id.buttonSelectImage);
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                galleryIntent.setType("image/*");
                getContent.launch(galleryIntent);
            }
        });

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProductToFirebase();
            }
        });
    }

    private void uploadProductToFirebase() {
        String productName = productNameEditText.getText().toString().trim();

        if (!productName.isEmpty() && selectedImageUri != null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            StorageReference storageRef = FirebaseStorage.getInstance().getReference("product_images/" + "product_image.jpg");

            UploadTask uploadTask = storageRef.putFile(selectedImageUri);
            uploadTask.addOnSuccessListener(taskSnapshot -> {
                storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();
                    String productDescription = productDescriptionEditText.getText().toString().trim();
                    double priceAP = Double.parseDouble(priceAPEditText.getText().toString().trim());
                    double priceBP = Double.parseDouble(priceBPEditText.getText().toString().trim());
                    int promotionPercentage = Integer.parseInt(promotionPercentageEditText.getText().toString().trim());
                    List<Integer> size = (sizeCheckBox.isChecked()) ? generateSizeList() : new ArrayList<>();
                    String category = productCategorySpinner.getSelectedItem().toString();

                    // Calculate the final price based on the provided values
                    double finalPrice = calculateFinalPrice(priceAP, promotionPercentage);

                    // Create a new Shoe object with all details
                    Shoe product = new Shoe(null, productName, productDescription, imageUrl, priceAP, finalPrice, promotionPercentage, size, category);
                    // Assuming you have a "products" node in your database
                    database.getReference("products").push().setValue(product);

                    finish();
                });
            }).addOnFailureListener(e -> {
                // Handle unsuccessful uploads
                // ...
            });
        }
    }

    private double calculateFinalPrice(double priceAP, int promotionPercentage) {
        // Calculate the final price based on the provided values
        return priceAP - (priceAP * (promotionPercentage / 100.0));
    }

    private List<Integer> generateSizeList() {
        List<Integer> sizes = new ArrayList<>();
        // Add your logic to generate the size list based on your requirements
        sizes.add(38);
        sizes.add(39);
        // Add more sizes as needed
        return sizes;
    }

    private final ActivityResultLauncher<Intent> getContent =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Uri imageUri = data.getData();
                        selectedImageUri = imageUri;
                        Picasso.get().load(imageUri).into(imageViewProduct);
                    }
                }
            });
}