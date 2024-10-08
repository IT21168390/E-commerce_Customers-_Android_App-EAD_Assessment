package com.example.e_commercecustomers_ead.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commercecustomers_ead.R;
import com.example.e_commercecustomers_ead.adapters.CartAdapter;
import com.example.e_commercecustomers_ead.api_models.Address;
import com.example.e_commercecustomers_ead.api_models.ProductDataModel;
import com.example.e_commercecustomers_ead.models.CartItem;
import com.example.e_commercecustomers_ead.services.CartManager;
import com.example.e_commercecustomers_ead.services.OrderManager;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private List<ProductDataModel> cartItems;
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

        // Setup adapter
        cartAdapter = new CartAdapter(getContext(), cartItems);
        cartRecyclerView.setAdapter(cartAdapter);

        // Update total cost initially
        updateTotalCost();

        cartRecyclerView.setAdapter(cartAdapter);

        updateTotalCost();

        // Set up checkout button listener
        checkoutButton.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(getContext(), "Your cart is empty!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Show confirmation dialog
            showOrderSummaryDialog();
        });

        return view;
    }

    private void updateTotalCost() {
        double total = 0;
        for (ProductDataModel product : cartItems) {
            int quantity = cartManager.getQuantity(product);
            total += product.getPrice() * quantity;
        }
        totalCost.setText("Total: $" + String.format("%.2f", total));
    }




    private void showOrderSummaryDialog() {
        getShippingAddress(address -> {
            if (address == null || !isAddressValid(address)) {
                Toast.makeText(getContext(), "Please provide a valid shipping address.", Toast.LENGTH_SHORT).show();
                return;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Order Summary");

            StringBuilder summary = new StringBuilder();
            double totalCost = cartManager.getTotalCost();
            for (ProductDataModel item : cartItems) {
                summary.append(item.getName())
                        .append(" - Quantity: ")
                        .append(cartManager.getQuantity(item))
                        .append("\n");
            }
            summary.append("\nTotal Cost: $").append(String.format("%.2f", totalCost));

            builder.setMessage(summary.toString());

            builder.setPositiveButton("Confirm Purchase", (dialog, which) -> {
                // Collect order details
                String customerId = "67013b5bcd795885ad1163a3"; // Replace with actual customer ID
                List<CartItem> itemsToOrder = new ArrayList<>();
                for (ProductDataModel productDataModel : cartItems) {
                    CartItem cartItem = new CartItem(productDataModel.getId(), cartManager.getQuantity(productDataModel));
                    itemsToOrder.add(cartItem);
                }

                // Disable the checkout button to prevent multiple clicks
                checkoutButton.setEnabled(false);

                // Call the placeOrder method in OrderManager
                OrderManager.getInstance().placeOrder(customerId, itemsToOrder, address, new OrderManager.OnPurchaseOrderListener() {
                    @Override
                    public void onPurchaseSuccess(String orderId) {
                        // Re-enable the checkout button
                        checkoutButton.setEnabled(true);

                        // Clear the cart
                        cartManager.clearCart();
                        cartItems.clear();
                        cartAdapter.notifyDataSetChanged();
                        updateTotalCost();

                        Toast.makeText(getContext(), "Order placed successfully! Order ID: " + orderId, Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onPurchaseFailure(String errorMessage) {
                        // Re-enable the checkout button
                        checkoutButton.setEnabled(true);
                        System.out.println(errorMessage);
                        // Show error message
                        Toast.makeText(getContext(), "Failed to place order: " + errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

            builder.show();
        });
    }

    private void getShippingAddress(OnAddressEnteredListener listener) {
        AlertDialog.Builder addressDialogBuilder = new AlertDialog.Builder(getContext());
        addressDialogBuilder.setTitle("Enter Shipping Address");

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View addressView = inflater.inflate(R.layout.dialog_shipping_address, null);

        EditText streetInput = addressView.findViewById(R.id.streetEditText);
        EditText cityInput = addressView.findViewById(R.id.cityEditText);
        EditText zipCodeInput = addressView.findViewById(R.id.zipCodeEditText);

        addressDialogBuilder.setView(addressView);

        addressDialogBuilder.setPositiveButton("Save", (dialog, which) -> {
            String street = streetInput.getText().toString().trim();
            String city = cityInput.getText().toString().trim();
            String zipCode = zipCodeInput.getText().toString().trim();

            if (street.isEmpty() || city.isEmpty() || zipCode.isEmpty()) {
                Toast.makeText(getContext(), "All fields are required.", Toast.LENGTH_SHORT).show();
                listener.onAddressEntered(null);
            } else {
                listener.onAddressEntered(new Address(street, city, zipCode));
            }
        });

        addressDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> listener.onAddressEntered(null));

        addressDialogBuilder.show();
    }

    interface OnAddressEnteredListener {
        void onAddressEntered(@Nullable Address address);
    }

    private boolean isAddressValid(Address address) {
        // Implement your validation logic
        return address.getStreet() != null && !address.getStreet().isEmpty()
                && address.getCity() != null && !address.getCity().isEmpty()
                && address.getZipCode() != null && !address.getZipCode().isEmpty();
    }

}

