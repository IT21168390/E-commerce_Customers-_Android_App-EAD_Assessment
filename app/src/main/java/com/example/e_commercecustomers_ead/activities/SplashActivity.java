package com.example.e_commercecustomers_ead.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.e_commercecustomers_ead.MainActivity;
import com.example.e_commercecustomers_ead.R;
import android.content.Intent;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Delay for 3 seconds (3000 milliseconds) before navigating to MainActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // After the delay, start the main activity
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close the splash activity so the user can't go back to it
            }
        }, 3000); // 3-second delay
    }
}