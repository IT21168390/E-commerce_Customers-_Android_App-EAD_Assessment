package com.example.e_commercecustomers_ead.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commercecustomers_ead.R;
import com.example.e_commercecustomers_ead.adapters.CartAdapter;
import com.example.e_commercecustomers_ead.models.Product;
import com.example.e_commercecustomers_ead.services.CartManager;

import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private List<Product> cartItems;
    private Button checkoutButton;
    private TextView totalCost;
    private CartManager cartManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        // Initialize CartManager
        cartManager = CartManager.getInstance();

        // Setup UI components
        totalCost = view.findViewById(R.id.totalCost);
        checkoutButton = view.findViewById(R.id.checkoutButton);

        cartRecyclerView = view.findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Get cart items from CartManager
        cartItems = cartManager.getCartItems();

        // Set up adapter
        /*cartAdapter = new CartAdapter(cartItems);
        cartRecyclerView.setAdapter(cartAdapter);*/
        // Setup adapter
        cartAdapter = new CartAdapter(getContext(), cartItems);
        cartRecyclerView.setAdapter(cartAdapter);

        // Update total cost initially
        updateTotalCost();
        // Setup adapter
//        cartAdapter = new CartAdapter(cartItems, position -> {
//            cartManager.removeFromCart(cartItems.get(position));
//            cartAdapter.notifyItemRemoved(position);
//            updateTotalCost();
//        });
        cartRecyclerView.setAdapter(cartAdapter);

        updateTotalCost();

        /*checkoutButton.setOnClickListener(v -> {
            // Handle checkout process
            Toast.makeText(getContext(), "Proceeding to Checkout", Toast.LENGTH_SHORT).show();
        });*/
        checkoutButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Order Summary");

            StringBuilder summary = new StringBuilder();
            double totalCost = cartManager.getTotalCost();
            for (Product product : cartManager.getCartItems()) {
                summary.append(product.getName())
                        .append(" - Quantity: ")
                        .append(cartManager.getQuantity(product))
                        .append("\n");
            }
            summary.append("\nTotal Cost: $").append(String.format("%.2f", totalCost));

            builder.setMessage(summary.toString());
            builder.setPositiveButton("Confirm Purchase", (dialog, which) -> {
                // Handle purchase confirmation
                Toast.makeText(getContext(), "Purchase Confirmed!", Toast.LENGTH_SHORT).show();
                cartManager.clearCart();  // Clear the cart after purchase
                // Optionally, navigate to another screen
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            builder.show();
        });


        return view;
    }

    /*private void updateTotalCost() {
        double total = 0;
        for (Product product : cartItems) {
            total += product.getPrice(); // You can also consider quantity here if applicable
        }
        totalCost.setText("Total: $" + String.format("%.2f", total));
    }*/
    private void updateTotalCost() {
        double total = 0;
        for (Product product : cartItems) {
            int quantity = cartManager.getQuantity(product);
            total += product.getPrice() * quantity;
        }
        totalCost.setText("Total: $" + String.format("%.2f", total));
    }

}

