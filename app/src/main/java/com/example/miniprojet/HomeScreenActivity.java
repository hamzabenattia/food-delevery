package com.example.miniprojet;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import com.example.miniprojet.databinding.ActivityHomeScreenBinding;


public class HomeScreenActivity extends AppCompatActivity {

    ActivityHomeScreenBinding binding;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        replceFragment(new HomeFragment());

        setContentView(binding.getRoot());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {


                    if (item.getItemId() == R.id.accountItem)
                    {
                        replceFragment(new AccountFragment());
                    }
                    if (item.getItemId() == R.id.homeItem)

                    {
                        replceFragment(new HomeFragment());
                        
                    }
                    if (item.getItemId() == R.id.mapItem)
                    {

                        return true;
                    }
                    if (item.getItemId() == R.id.wichlistItem)
                    {

                        return true;
                    }

            return true;
                }
                );
        /*

        bottomNavigationView.setSelectedItemId(R.id.homeItem);
        userInfoTV.setText("Hello "+ " "+ currentUser.getDisplayName());
        nightModeSwitch();



    }


    void initValue() {

        logOutButton = findViewById(R.id.logOutButton);
        userInfoTV = findViewById(R.id.userInfoTV);
        nightModeSwitch = findViewById(R.id.swichmod);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }



void nightModeSwitch(){
        nightModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
    }

*/


    }



    private void replceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentLayout,fragment);
        fragmentTransaction.commit();
    }
}




