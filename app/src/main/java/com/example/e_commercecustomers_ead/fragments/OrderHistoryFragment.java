package com.example.e_commercecustomers_ead.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_commercecustomers_ead.R;
import com.example.e_commercecustomers_ead.adapters.OrderHistoryPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class OrderHistoryFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private OrderHistoryPagerAdapter pagerAdapter;

    private final String[] tabTitles = new String[]{"Pending", "Dispatched", "Delivered", "Cancelled"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_history, container, false);

        tabLayout = view.findViewById(R.id.orderStatusTabLayout);
        viewPager = view.findViewById(R.id.orderViewPager);

        pagerAdapter = new OrderHistoryPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        // Re-navigating Crash - Bug Fix (for ViewPager2)
        viewPager.setSaveEnabled(false);

        // Attach TabLayout with ViewPager2
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabTitles[position])
        ).attach();


        return view;
    }
}