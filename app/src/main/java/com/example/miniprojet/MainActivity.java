package com.example.miniprojet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageView logoView = findViewById(R.id.logo);

        // Create an alpha animation for the logo
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(5000);
        alphaAnimation.setInterpolator(new DecelerateInterpolator());

        // Start the animation on the logo
        logoView.startAnimation(alphaAnimation);

        // Start the second activity after the animation is complete
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Do nothing
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                FirebaseAuth auth = FirebaseAuth.getInstance();

                FirebaseUser user = auth.getCurrentUser();
                if (user != null) {
                    startActivity(new Intent(MainActivity.this, HomeScreenActivity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Do nothing
            }
        });
    }



}