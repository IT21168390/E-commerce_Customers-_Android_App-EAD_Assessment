package com.example.e_commercecustomers_ead;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.e_commercecustomers_ead.fragments.CartFragment;
import com.example.e_commercecustomers_ead.fragments.ProductsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    // Initialize fragments
    private Fragment productsFragment;
    private Fragment cartFragment;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_products);
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Set the activity's main layout (not the fragment layout)

        // Initialize fragments
        productsFragment = new ProductsFragment();
        cartFragment = new CartFragment();

        // Initialize BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Load the default fragment (ProductsFragment) on app launch
        if (savedInstanceState == null) {
            loadFragment(productsFragment);
            bottomNavigationView.setSelectedItemId(R.id.exploreNav); // Highlight Explore as selected
        }

        // Set up navigation item selection listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;

                switch (menuItem.getItemId()) {
                    case R.id.homeNav:
                        selectedFragment = productsFragment;
                        break;
                    case R.id.exploreNav:
                        selectedFragment = productsFragment;
                        break;
                    case R.id.profileNav:
                        selectedFragment = productsFragment;
                        break;
                    case R.id.cartNav:
                        selectedFragment = cartFragment;
                        break;
                }

                if (selectedFragment != null) {
                    loadFragment(selectedFragment);
                    return true;
                }

                return false;
            }
        });
        // Check if savedInstanceState is null to avoid overlapping fragments on orientation change
        /*if (savedInstanceState == null) {
            // Add the ProductsFragment to the container
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Create an instance of ProductsFragment
            ProductsFragment productsFragment = new ProductsFragment();

            // Replace the container with the fragment
            fragmentTransaction.replace(R.id.fragment_container, productsFragment);
            fragmentTransaction.commit();
        }*/
    }

    // Method to load the selected fragment into the container
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    // Optional: Handle back button press to navigate between fragments
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }
}