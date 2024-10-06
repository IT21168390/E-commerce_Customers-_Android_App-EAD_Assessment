package com.example.e_commercecustomers_ead.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
// import android.widget.ImageView; // Not used if you set orderIcon as a common icon
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Import the Order model
import com.example.e_commercecustomers_ead.R;
import com.example.e_commercecustomers_ead.models.Order;
import com.example.e_commercecustomers_ead.services.OrderManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    public interface OnOrderActionListener {
        void onViewDetails(Order order);
        void onRateOrder(Order order);
    }

    private Context context;
    private List<Order> orders;
    private OnOrderActionListener actionListener;

    public OrderAdapter(Context context, List<Order> orders,
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
        Order order = orders.get(position);

        // Set order details
        holder.orderId.setText("Order ID: " + order.getOrderId());
        holder.orderCost.setText(String.format("Cost: $%.2f", order.getOrderCost()));
        holder.orderStatus.setText("Status: " + order.getOrderStatus());

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String formattedDate = sdf.format(order.getOrderDate());
        holder.orderDate.setText("Date: " + formattedDate);

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
        if (order.getOrderStatus().equalsIgnoreCase("Delivered") && !order.isRated()) {
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


    private void showConfirmationDialog(Order order) {
        new AlertDialog.Builder(context)
                .setTitle("Cancel Order")
                .setMessage("Are you sure you want to cancel this order?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Cancel order logic
                    OrderManager.getInstance().removeOrder(order);
                    orders.remove(order);
                    notifyDataSetChanged(); // Refresh the list
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void showUpdateAddressDialog(Order order) {
        // Create a dialog to input the updated address
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_update_order_address, null);

        EditText streetInput = dialogView.findViewById(R.id.streetEditText);
        EditText cityInput = dialogView.findViewById(R.id.cityEditText);
        EditText zipCodeInput = dialogView.findViewById(R.id.zipCodeEditText);

        builder.setView(dialogView)
                .setTitle("Update Address")
                .setPositiveButton("Submit", (dialog, which) -> {
                    // Handle the address update logic
                    String street = streetInput.getText().toString();
                    String city = cityInput.getText().toString();
                    String zipCode = zipCodeInput.getText().toString();
                    // Update the order with new address information
                    // Implement your update logic here
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
    public void updateOrders(List<Order> newOrders) {
        this.orders = newOrders;
        notifyDataSetChanged();
    }
}