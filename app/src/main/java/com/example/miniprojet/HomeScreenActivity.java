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
                        replceFragment(new AddProductFragment());

                    }
                    if (item.getItemId() == R.id.wichlistItem)
                    {

                        return true;
                    }

            return true;
                }
                );


    }



    private void replceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentLayout,fragment);
        fragmentTransaction.commit();
    }
}




