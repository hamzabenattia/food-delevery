package com.example.miniprojet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MyAccountActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextInputLayout firstName;
    TextInputLayout lastName;

    TextInputLayout email;
    TextInputLayout password;
    TextInputLayout phoneNumber;

    Button UpdateInformationButton;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        initValue();
       BottonNavigClick();
       /* FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();*/
        UserInfoInit();


    }

    void initValue()
    {
        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        firstName = findViewById(R.id.firstNameUpdateInput);
        lastName = findViewById(R.id.lastNameUpdateInput);
        email = findViewById(R.id.emailUpdateInput);
        phoneNumber = findViewById(R.id.PhoneUpdateInput);
        password = findViewById(R.id.passwordUpdateInput);
        UpdateInformationButton = findViewById(R.id.UpdateAccountButton);
        bottomNavigationView.setSelectedItemId(R.id.accountItem);
    }

    void UserInfoInit(){
        firstName.getEditText().setText(sharedPreferences.getString("firstName",""));
        lastName.getEditText().setText(sharedPreferences.getString("lastName",""));
        email.getEditText().setText(sharedPreferences.getString("email",""));
        phoneNumber.getEditText().setText(sharedPreferences.getString("phone",""));
    }





    void BottonNavigClick(){
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.homeItem)
                {
                    startActivity(new Intent(MyAccountActivity.this, HomeScreenActivity.class));
                    return true;
                }
                if (item.getItemId() == R.id.accountItem)
                {
                    return true;
                }
                if (item.getItemId() == R.id.mapItem)
                {
                    return true;
                }
                if (item.getItemId() == R.id.wichlistItem)
                {
                    return true;
                }
                return false;
            }
        });
    }

}

