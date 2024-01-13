package com.example.miniprojet;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class AddProductFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView imageView;
    private EditText editTextProductName;
    private EditText editTextProductPrice;
    private Uri imageUri;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;




    public AddProductFragment() {
    }


    public static AddProductFragment newInstance(String param1, String param2) {
        AddProductFragment fragment = new AddProductFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("products");

        storageReference = FirebaseStorage.getInstance().getReference("product_images");


        imageView = view.findViewById(R.id.imageView);
        editTextProductName = view.findViewById(R.id.editTextProductName);
        editTextProductPrice = view.findViewById(R.id.editTextProductPrice);

        Button btnChooseImage = view.findViewById(R.id.btnChooseImage);
        btnChooseImage.setOnClickListener(v -> chooseImage());

        Button btnCreateProduct = view.findViewById(R.id.btnCreateProduct);
        btnCreateProduct.setOnClickListener(v -> createProduct());

        return view;
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void createProduct() {
        String productName = editTextProductName.getText().toString().trim();
        String productPriceStr = editTextProductPrice.getText().toString().trim();

        if (TextUtils.isEmpty(productName) || TextUtils.isEmpty(productPriceStr) || imageUri == null) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double productPrice = Double.parseDouble(productPriceStr);

        // Upload the image to Firebase Storage
        uploadImageToFirebase(productName, productPrice);
    }


    private void uploadImageToFirebase(final String productName, final double productPrice) {
        // Create a unique name for the image file
        String imageFileName = "product_image_" + System.currentTimeMillis() + ".jpg";
        final StorageReference imageRef = storageReference.child(imageFileName);

        // Upload the image to Firebase Storage
        imageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Image upload success, get the download URL
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        // After successful image upload, create a Product object
                        // with the image URL, name, and price. Then, add the product to Firebase Realtime Database.
                        Food product = new Food(uri.toString(), productName, productPrice);

                        // Add the product to Firebase Realtime Database
                        addProductToRealtimeDatabase(product);
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(requireContext(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                });
    }

    private void addProductToRealtimeDatabase(Food product) {
        // Generate a unique key for the product
        String productId = databaseReference.push().getKey();
        product.setId(productId);

        // Add the product to Firebase Realtime Database under "products" node
        databaseReference.child(productId).setValue(product)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(requireContext(), "Product created successfully", Toast.LENGTH_SHORT).show();
                    clearFields();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(requireContext(), "Failed to create product", Toast.LENGTH_SHORT).show();
                });
    }

    private void clearFields() {
        imageView.setImageResource(android.R.color.transparent);
        editTextProductName.getText().clear();
        editTextProductPrice.getText().clear();
        imageUri = null;
    }



}