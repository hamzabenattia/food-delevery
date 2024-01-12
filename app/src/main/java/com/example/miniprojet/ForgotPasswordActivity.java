package com.example.miniprojet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    Button ForgotButton;
    TextView SignUpLink;
    TextInputLayout forgoetemail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initValue();
        SingUpClick();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        ForgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = forgoetemail.getEditText().getText().toString();

                auth.sendPasswordResetEmail(forgoetemail.getEditText().getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgotPasswordActivity.this,"Reset Password Successfully send to "+emailAddress,Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(ForgotPasswordActivity.this,LoginActivity.class));
                                }
                                else
                                {
                                    Toast.makeText(ForgotPasswordActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                }


                            }
                        });
            }
        });


    }


    void initValue() {
        ForgotButton = findViewById(R.id.resetPasswordButton);
        SignUpLink = findViewById(R.id.register_link);
        forgoetemail = findViewById(R.id.forgotPasswordEmail);
    }

    void SingUpClick(){
        SignUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ForgotPasswordActivity.this,SignUpActivity.class);
                startActivity(i);
            }
        });

    }


}