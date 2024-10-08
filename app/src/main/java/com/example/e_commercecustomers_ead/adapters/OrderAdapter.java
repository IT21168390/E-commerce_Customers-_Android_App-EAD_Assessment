package com.example.e_commercecustomers_ead.adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
// import android.widget.ImageView; // Not used if you set orderIcon as a common icon
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Import the Order model
import com.example.e_commercecustomers_ead.R;
import com.example.e_commercecustomers_ead.api_models.Address;
import com.example.e_commercecustomers_ead.api_models.OrderModel;
import com.example.e_commercecustomers_ead.models.Order;
import com.example.e_commercecustomers_ead.services.OrderManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    public interface OnOrderActionListener {
        void onViewDetails(OrderModel order);
        void onRateOrder(OrderModel order);
    }

    private Context context;
    private List<OrderModel> orders;
    private OnOrderActionListener actionListener;

    public OrderAdapter(Context context, List<OrderModel> orders,
                        OnOrderActionListener actionListener) {
        this.context = context;
        this.orders = orders;
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderModel order = orders.get(position);

        // Set order details
        holder.orderId.setText("Order ID: " + order.getOrderID());
        holder.orderCost.setText(String.format("Cost: $%.2f", order.getTotalAmount()));
        holder.orderStatus.setText("Status: " + order.getOrderStatus());

        /*SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String formattedDate = sdf.format(order.getPlacedAt());*/
        holder.orderDate.setText("Date: " + order.getPlacedAt());

        // Set order icon/image if needed
        holder.orderIcon.setImageResource(R.drawable.baseline_sell_24); // Replace with actual image

        // Show or hide buttons based on order status
        if (order.getOrderStatus().equalsIgnoreCase("Pending")) {
            holder.cancelButton.setVisibility(View.VISIBLE);
            holder.updateButton.setVisibility(View.VISIBLE);

            holder.cancelButton.setOnClickListener(v -> {
                // Show confirmation dialog for cancellation
                showConfirmationDialog(order);
            });

            holder.updateButton.setOnClickListener(v -> {
                // Show dialog for updating address
                showUpdateAddressDialog(order);
            });
        } else {
            holder.cancelButton.setVisibility(View.GONE);
            holder.updateButton.setVisibility(View.GONE);
        }

        // Enable or disable Rate button based on order status and rating
        if (order.getOrderStatus().equalsIgnoreCase("Delivered") /*&& !order.isRated()*/) {
            holder.rateButton.setEnabled(true);
        } else {
            holder.rateButton.setEnabled(false);
        }

        // Set click listeners
        holder.viewDetailsButton.setOnClickListener(v -> {
            if (actionListener != null) {
                actionListener.onViewDetails(order);
            }
        });

        holder.rateButton.setOnClickListener(v -> {
            if (actionListener != null) {
                actionListener.onRateOrder(order);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


    private void showConfirmationDialog(OrderModel order) {
        new AlertDialog.Builder(context)
                .setTitle("Cancel Order")
                .setMessage("Are you sure you want to cancel this order?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Cancel order logic
                    /*OrderManager.getInstance().removeOrder(order);
                    orders.remove(order);
                    notifyDataSetChanged(); // Refresh the list*/

                    // Call the cancelOrder method in OrderManager
                    OrderManager.getInstance().cancelOrder(order.getId(), new OrderManager.OnCancelOrderListener() {
                        @Override
                        public void onCancelSuccess(String orderId) {
                            // Remove the order from the local list
                            int position = orders.indexOf(order);
                            if (position != -1) {
                                orders.remove(position);
                                notifyItemRemoved(position);
                                Toast.makeText(context, "Order cancelled successfully.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelFailure(String orderId, String errorMessage) {
                            // Handle the failure
                            Toast.makeText(context, "Failed to cancel order: " + errorMessage, Toast.LENGTH_LONG).show();
                        }
                    });
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void showUpdateAddressDialog(OrderModel order) {
        // Create a dialog to input the updated address
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_update_order_address, null);

        EditText streetInput = dialogView.findViewById(R.id.streetEditText);
        EditText cityInput = dialogView.findViewById(R.id.cityEditText);
        EditText zipCodeInput = dialogView.findViewById(R.id.zipCodeEditText);

        // Pre-fill existing address if available
        if (order.getShippingAddress() != null) {
            streetInput.setText(order.getShippingAddress().getStreet());
            cityInput.setText(order.getShippingAddress().getCity());
            zipCodeInput.setText(order.getShippingAddress().getZipCode());
        }

        builder.setView(dialogView)
                .setTitle("Update Address")
                .setPositiveButton("Submit", (dialog, which) -> {
                    // Optionally, show a progress dialog
                    ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage("Updating address...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    // Handle the address update logic
                    String street = streetInput.getText().toString().trim();
                    String city = cityInput.getText().toString().trim();
                    String zipCode = zipCodeInput.getText().toString().trim();
                    // Update the order with new address information

                    // Validate inputs
                    if (street.isEmpty() || city.isEmpty() || zipCode.isEmpty()) {
                        Toast.makeText(context, "All fields are required.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        return;
                    }

                    // Create a new Address object
                    Address updatedAddress = new Address();
                    updatedAddress.setStreet(street);
                    updatedAddress.setCity(city);
                    updatedAddress.setZipCode(zipCode);

                    // Call the updateOrderAddress method in OrderManager
                    OrderManager.getInstance().updateOrderAddress(order.getId(), updatedAddress, new OrderManager.OnUpdateOrderListener() {
                        @Override
                        public void onUpdateSuccess(String orderId) {
                            // Update the local order object
                            int position = orders.indexOf(order);
                            if (position != -1) {
                                OrderModel currentOrder = orders.get(position);
                                currentOrder.setShippingAddress(updatedAddress);
                                notifyItemChanged(position);
                                Toast.makeText(context, "Address updated successfully.", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onUpdateFailure(String orderId, String errorMessage) {
                            // Handle the failure
                            Toast.makeText(context, "Failed to update address: " + errorMessage, Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();
    }



    // ViewHolder class
    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        ImageView orderIcon;
        TextView orderId, orderCost, orderStatus, orderDate;
        Button viewDetailsButton, rateButton, cancelButton, updateButton;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIcon = itemView.findViewById(R.id.orderIcon);
            orderId = itemView.findViewById(R.id.orderId);
            orderCost = itemView.findViewById(R.id.orderCost);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            orderDate = itemView.findViewById(R.id.orderDate);
            viewDetailsButton = itemView.findViewById(R.id.viewDetailsButton);
            rateButton = itemView.findViewById(R.id.rateButton);

            cancelButton = itemView.findViewById(R.id.cancelButton);
            updateButton = itemView.findViewById(R.id.updateButton);
        }
    }

    // Method to update orders list
    public void updateOrders(List<OrderModel> newOrders) {
        this.orders = newOrders;
        notifyDataSetChanged();
    }
}