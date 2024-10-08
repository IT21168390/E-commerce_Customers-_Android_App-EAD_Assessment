package com.example.e_commercecustomers_ead;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.e_commercecustomers_ead.fragments.CartFragment;
import com.example.e_commercecustomers_ead.fragments.NotificationFragment;
import com.example.e_commercecustomers_ead.fragments.OrderHistoryFragment;
import com.example.e_commercecustomers_ead.fragments.ProductsFragment;
import com.example.e_commercecustomers_ead.fragments.ProfileViewFragment;
import com.example.e_commercecustomers_ead.fragments.VendorReviewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    // Initialize fragments
    private Fragment productsFragment;
    private Fragment cartFragment;
    private Fragment orderHistoryFragment;
    private Fragment vendorReviewFragment;
    private Fragment profileViewFragment;

    private TextView tvNotificationBadge;
    private FrameLayout flNotificationIcon;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Set the activity's main layout (not the fragment layout)

//       toolbar = findViewById(R.id.toolbar);
//       setSupportActionBar(toolbar);
//
//       // Get references to the notification icon and badge
//       flNotificationIcon = toolbar.findViewById(R.id.ivNotificationIcon);
//       tvNotificationBadge = toolbar.findViewById(R.id.tvNotificationBadge);
//
//       // Set up click listener to navigate to NotificationFragment
//       flNotificationIcon.setOnClickListener(v -> navigateToNotificationFragment());
//
//       // Fetch unread notification count from the database or shared preferences
//       int unreadNotificationCount = getUnreadNotificationCount();
//       updateNotificationBadge(unreadNotificationCount);

        // Initialize fragments
        productsFragment = new ProductsFragment();
        cartFragment = new CartFragment();
        orderHistoryFragment = new OrderHistoryFragment();
        vendorReviewFragment = new VendorReviewFragment();
        profileViewFragment = new ProfileViewFragment();

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
                        selectedFragment = orderHistoryFragment;
                        break;
                    case R.id.exploreNav:
                        selectedFragment = productsFragment;
                        break;
                    case R.id.profileNav:
                        selectedFragment = profileViewFragment;
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
    }

    // Method to load the selected fragment into the container
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    // Handle back button press to navigate between fragments
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }

    private void updateNotificationBadge(int count) {
        if (count > 0) {
            tvNotificationBadge.setVisibility(View.VISIBLE);
            tvNotificationBadge.setText(String.valueOf(count));
        } else {
            tvNotificationBadge.setVisibility(View.GONE);
        }
    }

    private int getUnreadNotificationCount() {
        // Mock function, replace with actual logic to fetch unread notification count
        return 5; // Example unread count
    }

    private void navigateToNotificationFragment() {
        // Clear unread notification count when navigating
        updateNotificationBadge(0);

        // Navigate to the NotificationFragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new NotificationFragment())
                .addToBackStack(null)
                .commit();
    }
}