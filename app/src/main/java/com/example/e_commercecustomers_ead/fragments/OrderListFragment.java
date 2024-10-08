package com.example.e_commercecustomers_ead.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.e_commercecustomers_ead.R;
import com.example.e_commercecustomers_ead.adapters.OrderAdapter;
import com.example.e_commercecustomers_ead.api_models.OrderModel;
import com.example.e_commercecustomers_ead.services.OrderManager;

import java.util.List;

public class OrderListFragment extends Fragment {

    private static final String ARG_STATUS = "status";
    private String status;

    private RecyclerView orderRecyclerView;
    private OrderAdapter orderAdapter;
    private List<OrderModel> orders;

    public OrderListFragment() {
        // Required empty public constructor
    }

    public static OrderListFragment newInstance(String status) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STATUS, status);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            status = getArguments().getString(ARG_STATUS);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);

        orderRecyclerView = view.findViewById(R.id.orderRecyclerView);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Fetch orders based on status
        orders = OrderManager.getInstance().getOrdersByStatus(status);

        orderAdapter = new OrderAdapter(getContext(), orders, new OrderAdapter.OnOrderActionListener() {
            @Override
            public void onViewDetails(OrderModel order) {
                // Handle View Details button click
                // Navigate to OrderDetailsFragment
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                OrderDetailsFragment detailsFragment = OrderDetailsFragment.newInstance(order);
                transaction.replace(R.id.fragment_container, detailsFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }

            @Override
            public void onRateOrder(OrderModel order) {
                // Handle Rate button click
                if (order.getOrderStatus().equalsIgnoreCase("Delivered") /*&& !order.isRated()*/) {
                    // Open Rating Dialog
                    showRatingDialog(order);
                } else {
                    Toast.makeText(getContext(), "Cannot rate this order.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        orderRecyclerView.setAdapter(orderAdapter);

        return view;
    }

    private void showRatingDialog(OrderModel order) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        builder.setTitle("Rate Order");
        builder.setMessage("Would you like to rate this order?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            // Handle rating logic here
            OrderManager.getInstance().rateOrder(order);
            Toast.makeText(getContext(), "Thank you for your rating!", Toast.LENGTH_SHORT).show();
            orderAdapter.notifyDataSetChanged(); // Refresh the adapter to update the Rate button
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}