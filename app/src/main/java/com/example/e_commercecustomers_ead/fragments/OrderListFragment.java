package com.example.e_commercecustomers_ead.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.e_commercecustomers_ead.models.Order;
import com.example.e_commercecustomers_ead.services.OrderManager;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OrderListFragment extends Fragment {

    private static final String ARG_STATUS = "status";
    private String status;

    private RecyclerView orderRecyclerView;
    private OrderAdapter orderAdapter;
    private List<Order> orders;

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

        /*orderAdapter = new OrderAdapter(getContext(), orders, order -> {
            // Handle View Details button click
            // Navigate to OrderDetailsFragment
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            OrderDetailsFragment detailsFragment = OrderDetailsFragment.newInstance(order);
            transaction.replace(R.id.fragment_container, detailsFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }, order -> {
            // Handle Rate button click
            if (order.getOrderStatus().equalsIgnoreCase("Delivered") && !order.isRated()) {
                // Open Rating Dialog
                showRatingDialog(order);
            } else {
                Toast.makeText(getContext(), "Cannot rate this order.", Toast.LENGTH_SHORT).show();
            }
        });*/
        orderAdapter = new OrderAdapter(getContext(), orders, new OrderAdapter.OnOrderActionListener() {
            @Override
            public void onViewDetails(Order order) {
                // Handle View Details button click
                // Navigate to OrderDetailsFragment
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                OrderDetailsFragment detailsFragment = OrderDetailsFragment.newInstance(order);
                transaction.replace(R.id.fragment_container, detailsFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }

            @Override
            public void onRateOrder(Order order) {
                // Handle Rate button click
                if (order.getOrderStatus().equalsIgnoreCase("Delivered") && !order.isRated()) {
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

    private void showRatingDialog(Order order) {
        // Implement a simple rating dialog
        // You can use a custom dialog layout with RatingBar and a confirm button
        // For simplicity, let's use AlertDialog with a simple message

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
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.e_commercecustomers_ead.R;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link OrderListFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class OrderListFragment extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public OrderListFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment OrderListFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static OrderListFragment newInstance(String param1, String param2) {
//        OrderListFragment fragment = new OrderListFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_order_list, container, false);
//    }
//}