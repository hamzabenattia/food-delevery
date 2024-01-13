package com.example.miniprojet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class DetailleActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    private Food product;

    ImageView imageView,backbtn;
    TextView titleTextView,priceTextView;

    Button addToCart,deleteBtn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaille);
        databaseReference = FirebaseDatabase.getInstance().getReference("products");

        Intent intent = getIntent();
        if (intent != null) {
            product = (Food) intent.getSerializableExtra("object");

            initV();

            Picasso.get()
                    .load(product.getImageUrl())
                    .into(imageView);
            titleTextView.setText(product.getTitle());
            priceTextView.setText(String.valueOf(product.getPrice()));
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteProduct();

                }
            });
        }

        backbtn.setOnClickListener(v -> startActivity(new Intent(DetailleActivity.this,HomeScreenActivity.class)) );


    }

    void initV(){
        imageView = findViewById(R.id.itemPic);
        titleTextView = findViewById(R.id.titleTxt);
        priceTextView = findViewById(R.id.priceTxt);
        deleteBtn = findViewById(R.id.deletebtn);
        backbtn = findViewById(R.id.imageView5);
    }


    private void deleteProduct() {
        // Check if the product is not null
        if (product != null) {
            databaseReference.child(product.getId()).removeValue()
                    .addOnSuccessListener(aVoid  -> {
                        // Deletion successful
                        Toast.makeText(DetailleActivity.this, "Product deleted successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DetailleActivity.this,HomeScreenActivity.class));

                    })
                    .addOnFailureListener(e -> {
                        // Handle deletion failure
                        Toast.makeText(DetailleActivity.this, "Failed to delete product", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}