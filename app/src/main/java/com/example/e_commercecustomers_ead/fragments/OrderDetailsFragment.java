package com.example.e_commercecustomers_ead.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.e_commercecustomers_ead.R;
import com.example.e_commercecustomers_ead.api_models.OrderModel;
import com.example.e_commercecustomers_ead.models.Order;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class OrderDetailsFragment extends Fragment {

    private static final String ARG_ORDER = "order";
    private OrderModel order;

    private TextView orderIdTextView, orderCostTextView, orderStatusTextView,
            orderDateTextView, orderDetailsTextView;

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
        orderCostTextView = view.findViewById(R.id.orderCostTextView);
        orderStatusTextView = view.findViewById(R.id.orderStatusTextView);
        orderDateTextView = view.findViewById(R.id.orderDateTextView);
        orderDetailsTextView = view.findViewById(R.id.orderDetailsTextView);

        // Populate data
        if (order != null) {
            orderIdTextView.setText("Order ID: " + order.getOrderID());
            orderCostTextView.setText(String.format("Cost: $%.2f", order.getTotalAmount()));
            orderStatusTextView.setText("Status: " + order.getOrderStatus());

            /*SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            String formattedDate = sdf.format(order.getPlacedAt());*/
            orderDateTextView.setText("Date: " + order.getPlacedAt().substring(0, 10));

            orderDetailsTextView.setText("---");
        }

        return view;
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
// * Use the {@link OrderDetailsFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class OrderDetailsFragment extends Fragment {
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
//    public OrderDetailsFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment OrderDetailsFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static OrderDetailsFragment newInstance(String param1, String param2) {
//        OrderDetailsFragment fragment = new OrderDetailsFragment();
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
//        return inflater.inflate(R.layout.fragment_order_details, container, false);
//    }
//}