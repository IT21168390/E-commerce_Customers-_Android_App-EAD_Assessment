package com.example.e_commercecustomers_ead.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.e_commercecustomers_ead.R;
import com.example.e_commercecustomers_ead.adapters.OrderItemsAdapter;
import com.example.e_commercecustomers_ead.adapters.OrderVendorsAdapter;
import com.example.e_commercecustomers_ead.api_models.OrderModel;

public class OrderDetailsFragment extends Fragment {

    private static final String ARG_ORDER = "order";
    private OrderModel order;

    private TextView orderIdTextView, orderCostTextView, orderStatusTextView,
            orderDateTextView, orderModifiedDateTextView, customerNameTextView, addressTextView;

    public OrderDetailsFragment() {
        // Required empty public constructor
    }

    public static OrderDetailsFragment newInstance(OrderModel order) {
        OrderDetailsFragment fragment = new OrderDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ORDER, order);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            order = (OrderModel) getArguments().getSerializable(ARG_ORDER);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);

        // Initialize UI components
        orderIdTextView = view.findViewById(R.id.orderIdTextView);
        orderCostTextView = view.findViewById(R.id.totalAmountTextView);
        orderStatusTextView = view.findViewById(R.id.orderStatusTextView);
        orderDateTextView = view.findViewById(R.id.orderDateTextView);
        orderModifiedDateTextView = view.findViewById(R.id.orderUpdatedAtTextView);
        customerNameTextView = view.findViewById(R.id.customerNameTextView);
        addressTextView = view.findViewById(R.id.shippingAddressTextView);

        RecyclerView orderItemsRecyclerView = view.findViewById(R.id.orderItemsRecyclerView);
        OrderItemsAdapter itemsAdapter = new OrderItemsAdapter(getContext(), order.getOrderItems());
        orderItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderItemsRecyclerView.setAdapter(itemsAdapter);

        RecyclerView orderVendorsRecyclerView = view.findViewById(R.id.orderVendorsRecyclerView);
        OrderVendorsAdapter vendorItemsAdapter = new OrderVendorsAdapter(getContext(), order.getVendorStatus());
        orderVendorsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderVendorsRecyclerView.setAdapter(vendorItemsAdapter);

        // Populate data
        if (order != null) {
            orderIdTextView.setText("Order ID: " + order.getOrderID());
            orderCostTextView.setText(String.format("Cost: $%.2f", order.getTotalAmount()));
            orderStatusTextView.setText("Status: " + order.getOrderStatus());

            customerNameTextView.setText("Customer: " + order.getCustomerName());
            addressTextView.setText(order.getShippingAddress().getStreet()+", "+order.getShippingAddress().getCity()+", "+order.getShippingAddress().getZipCode());

            orderDateTextView.setText("Date: " + order.getPlacedAt().substring(0, 10));
            orderModifiedDateTextView.setText("Date: " + order.getUpdatedAt().substring(0, 10));
        }

        return view;
    }
}