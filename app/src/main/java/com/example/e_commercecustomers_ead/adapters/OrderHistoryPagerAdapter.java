package com.example.e_commercecustomers_ead.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.e_commercecustomers_ead.fragments.OrderListFragment;

public class OrderHistoryPagerAdapter extends FragmentStateAdapter {

    public OrderHistoryPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        String status;
        switch (position) {
            case 0:
                status = "Pending";
                break;
            case 1:
                status = "Dispatched";
                break;
            case 2:
                status = "Delivered";
                break;
            case 3:
                status = "Cancelled";
                break;
            default:
                status = "Pending";
        }
        return OrderListFragment.newInstance(status);
    }

    @Override
    public int getItemCount() {
        return 4; // Four tabs
    }
}