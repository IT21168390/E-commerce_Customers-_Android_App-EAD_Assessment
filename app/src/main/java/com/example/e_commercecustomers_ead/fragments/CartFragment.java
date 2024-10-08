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
import com.example.e_commercecustomers_ead.models.Product;
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
        // Set up checkout button listener
        checkoutButton.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(getContext(), "Your cart is empty!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Show confirmation dialog
            showOrderSummaryDialog();
        });
        /*checkoutButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Order Summary");

            StringBuilder summary = new StringBuilder();
            double totalCost = cartManager.getTotalCost();
            for (ProductDataModel product : cartManager.getCartItems()) {
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

                // Clear the adapter's data and notify it
                cartItems.clear();
                cartAdapter.notifyDataSetChanged();

                // Update total cost display
                updateTotalCost();  // This will now show $0.00

                // Optionally, navigate to another screen
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            builder.show();
        });*/


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

                        // Optionally, navigate to Order History or another screen
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

//    private void showOrderSummaryDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("Order Summary");
//
//        StringBuilder summary = new StringBuilder();
//        double totalCost = cartManager.getTotalCost();
//        for (ProductDataModel item : cartItems) {
//            //ProductDataModel product = item.getProduct();
//            summary.append(item.getName())
//                    .append(" - Quantity: ")
//                    .append(cartManager.getQuantity(item))
//                    .append("\n");
//        }
//        summary.append("\nTotal Cost: $").append(String.format("%.2f", totalCost));
//
//        builder.setMessage(summary.toString());
//
//        builder.setPositiveButton("Confirm Purchase", (dialog, which) -> {
//            // Collect order details
//            String customerId = "your_customer_id"; // Replace with actual customer ID
//            List<CartItem> itemsToOrder = new ArrayList<>();
//            for (ProductDataModel productDataModel: cartItems) {
//                CartItem cartItem = new CartItem(productDataModel, cartManager.getQuantity(productDataModel));
//                itemsToOrder.add(cartItem);
//            }
//            Address shippingAddress = getShippingAddress(); // Implement this method to get user's shipping address
//
//            // Validate shipping address
//            if (shippingAddress == null || !isAddressValid(shippingAddress)) {
//                Toast.makeText(getContext(), "Please provide a valid shipping address.", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            // Disable the checkout button to prevent multiple clicks
//            checkoutButton.setEnabled(false);
//            System.out.println("===================++++++++++++++++++++++"+shippingAddress);
//            // Call the placeOrder method in OrderManager
//            OrderManager.getInstance().placeOrder(customerId, itemsToOrder, shippingAddress, new OrderManager.OnPurchaseOrderListener() {
//                @Override
//                public void onPurchaseSuccess(String orderId) {
//                    // Re-enable the checkout button
//                    checkoutButton.setEnabled(true);
//
//                    // Clear the cart
//                    cartManager.clearCart();
//                    cartItems.clear();
//                    cartAdapter.notifyDataSetChanged();
//                    updateTotalCost();
//
//                    Toast.makeText(getContext(), "Order placed successfully! Order ID: " + orderId, Toast.LENGTH_LONG).show();
//
//                    // Optionally, navigate to Order History or another screen
//                }
//
//                @Override
//                public void onPurchaseFailure(String errorMessage) {
//                    // Re-enable the checkout button
//                    checkoutButton.setEnabled(true);
//
//                    // Show error message
//                    Toast.makeText(getContext(), "Failed to place order: " + errorMessage, Toast.LENGTH_LONG).show();
//                }
//            });
//        });
//
//        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
//
//        builder.show();
//    }
//
//    private Address getShippingAddress() {
//        // Implement a method to retrieve the user's shipping address
//        // This could be from user input, saved preferences, etc.
//        // For demonstration, we'll return a dummy address
//        // In a real app, you should retrieve this from the user's profile or input form
//
//        // Example of retrieving from user input via a dialog
//        AlertDialog.Builder addressDialogBuilder = new AlertDialog.Builder(getContext());
//        addressDialogBuilder.setTitle("Enter Shipping Address");
//
//        LayoutInflater inflater = LayoutInflater.from(getContext());
//        View addressView = inflater.inflate(R.layout.dialog_shipping_address, null);
//
//        EditText streetInput = addressView.findViewById(R.id.streetEditText);
//        EditText cityInput = addressView.findViewById(R.id.cityEditText);
//        EditText zipCodeInput = addressView.findViewById(R.id.zipCodeEditText);
//
//        addressDialogBuilder.setView(addressView);
//
//        final Address[] addressHolder = new Address[1]; // To hold the address outside the dialog
//
//        addressDialogBuilder.setPositiveButton("Save", (dialog, which) -> {
//            String street = streetInput.getText().toString().trim();
//            String city = cityInput.getText().toString().trim();
//            String zipCode = zipCodeInput.getText().toString().trim();
//
//            if (street.isEmpty() || city.isEmpty() || zipCode.isEmpty()) {
//                Toast.makeText(getContext(), "All fields are required.", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            addressHolder[0] = new Address(street, city, zipCode);
//        });
//
//        addressDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> {
//            addressHolder[0] = null;
//            dialog.dismiss();
//        });
//
//        addressDialogBuilder.show();
//
//        // Wait for user input
//        // Note: This is asynchronous, so for proper implementation, consider refactoring
//        // to handle asynchronous address input before placing the order.
//
//        // For simplicity, we'll return a dummy address here
//        //return new Address("123 Main St", "Cityville", "12345");
//        return addressHolder[0];
//    }

    private boolean isAddressValid(Address address) {
        // Implement your validation logic
        return address.getStreet() != null && !address.getStreet().isEmpty()
                && address.getCity() != null && !address.getCity().isEmpty()
                && address.getZipCode() != null && !address.getZipCode().isEmpty();
    }

}

