package com.example.miniprojet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout email;
    TextInputLayout password;
    Button LoginButton;
    TextView SignUpLink;
    TextView ForgotPasswordLink;

    long back_pressed;

    Toast T;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initValue();
        SingUpClick();
        ForgotPasswordClick();


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getEditText().getText().toString().isEmpty()==true)
                {
                    email.setError("Email is require");
                } else if (password.getEditText().getText().toString().isEmpty()==true) {
                    email.setError(null);
                    password.setError("Password is require");
                } else if (password.getEditText().getText().toString().length()<8) {
                    email.setError(null);
                    password.setError("Password must be at lease 8 character");
                }else {
                    password.setError(null);
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.signInWithEmailAndPassword(email.getEditText().getText().toString(), password.getEditText().getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(LoginActivity.this,HomeScreenActivity.class));
                                    } else {
                                        // Sign in failed
                                        Toast.makeText(LoginActivity.this,"Email or password are invalid",Toast.LENGTH_LONG).show();

                                    }
                                }
                            });

                }

            }
        });
    }


    void initValue() {
        email = findViewById(R.id.emailInput);
        password = findViewById(R.id.passwordInput);
        LoginButton = findViewById(R.id.loginButton);
        SignUpLink = findViewById(R.id.register_link);
        ForgotPasswordLink=findViewById(R.id.forgot_password_link);
    }

    void SingUpClick(){
        SignUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(i);
            }
        });

    }

    void ForgotPasswordClick(){
        ForgotPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 1000 > System.currentTimeMillis()){
            super.onBackPressed();
        }
        else{
         T.makeText(getBaseContext(),"Press once again to exit!",Toast.LENGTH_LONG).show();
        }
        back_pressed = System.currentTimeMillis();
    }



}