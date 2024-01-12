package com.example.miniprojet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountFragment extends Fragment {

    TextInputLayout firstName,lastName,email,phoneNumber;
    Button UpdateInformationButton,logOutButton;

    Switch darkmod;
    SharedPreferences sharedPreferences;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    private DatabaseReference mDatabase;


    public AccountFragment() {
        // Required empty public constructor
    }


    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        initValue(view);
        LogOutClick();
        displayUserInfo();

        UpdateInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserInfo();

            }
        });


        return view;
    }


    void initValue(View view)
    {
        sharedPreferences = view.getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        firstName = view.findViewById(R.id.firstNameUpdateInput);
        lastName = view.findViewById(R.id.lastNameUpdateInput);
        email = view.findViewById(R.id.emailUpdateInput);
        phoneNumber = view.findViewById(R.id.PhoneUpdateInput);
        UpdateInformationButton = view.findViewById(R.id.UpdateAccountButton);
        logOutButton = view.findViewById(R.id.logOutButton);
    }

    void LogOutClick() {
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }

    private void displayUserInfo()
    {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userRef = mDatabase.child(userId);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        User userData = snapshot.getValue(User.class);
                        if (userData != null) {
                          firstName.getEditText().setText(userData.getFirstName());
                            lastName.getEditText().setText(userData.getLastname());
                            email.getEditText().setText(userData.getEmail());
                            phoneNumber.getEditText().setText(userData.getPhone());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {



                }
            });

        }

    }



    private void updateUserInfo() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String newFirstName = firstName.getEditText().getText().toString().trim();
            String newLastName = lastName.getEditText().getText().toString().trim();
            String newEmail = email.getEditText().getText().toString().trim();
            String newPhone = phoneNumber.getEditText().getText().toString().trim();

            user.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        String userId = user.getUid();
                        User updatedUser = new User(newFirstName, newLastName, newEmail, newPhone);
                        mDatabase.child(userId).setValue(updatedUser);

                        Toast.makeText(getActivity(), "User information updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Failed to update user information", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

























}

