package com.example.miniprojet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpActivity extends AppCompatActivity {

    TextInputLayout firstName;
    TextInputLayout lastName;

    TextInputLayout email;
    TextInputLayout password;
    TextInputLayout phoneNumber;
    Button SignUpButton;
    TextView LoginInLink;
    TextView ForgotPasswordLink;
    private DatabaseReference mDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        initValue();
        ForgotPasswordClick();
        LogInLinkClick();

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String emails = email.getEditText().getText().toString();
                String passwordS = password.getEditText().getText().toString();
                String fname = firstName.getEditText().getText().toString();
                String lname = lastName.getEditText().getText().toString();
                String phone = phoneNumber.getEditText().getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("firstName",fname);
                editor.putString("lastName",lname);
                editor.putString("email",emails);
                editor.putString("phone",phone);
                editor.apply();


                if (firstName.getEditText().getText().toString().isEmpty()==true)
                {
                    firstName.setError("First Name is require");
                } else if (lastName.getEditText().getText().toString().isEmpty()==true) {
                    firstName.setError(null);
                    lastName.setError("Last Name is require");
                } else if (email.getEditText().getText().toString().isEmpty()==true) {
                    lastName.setError(null);
                    email.setError("Email is require");
                } else if (password.getEditText().getText().toString().length()<8) {
                    email.setError(null);
                    password.setError("Password must be at lease 8 character");
                }else {
                    password.setError(null);
                    mAuth.createUserWithEmailAndPassword(emails, passwordS)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(fname + " " + lname)
                                                .build();
                                        user.updateProfile(profileUpdates);
                                        String userId = mAuth.getCurrentUser().getUid();
                                        saveUserToDatabase(userId, fname, lname, emails, phone);
                                        Toast.makeText(SignUpActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));

                                    } else {
                                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                }
            }
        });

    }


    void initValue(){
        firstName = findViewById(R.id.firstNameInput);
        lastName = findViewById(R.id.lastNameInput);
        email = findViewById(R.id.emailSignUpInput);
        phoneNumber = findViewById(R.id.PhoneInput);
        password = findViewById(R.id.passwordSignupInput);
        SignUpButton = findViewById(R.id.SignUpButton);
        LoginInLink = findViewById(R.id.loginLink);
        ForgotPasswordLink=findViewById(R.id.forgot_password_link);
    }

    private void saveUserToDatabase(String userId, String firstName, String lastName, String email, String phone) {
        User user = new User(firstName, lastName, email, phone);
        mDatabase.child(userId).setValue(user);
    }

    void ForgotPasswordClick(){
        ForgotPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this, ForgotPasswordActivity.class);
                startActivity(i);
            }
        });

    }

    void LogInLinkClick(){

        LoginInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

    }


}