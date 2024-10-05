package com.example.e_commercecustomers_ead.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
// import android.widget.ImageView; // Not used if you set orderIcon as a common icon
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Import the Order model
import com.example.e_commercecustomers_ead.R;
import com.example.e_commercecustomers_ead.models.Order;

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

    // ViewHolder class
    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        ImageView orderIcon;
        TextView orderId, orderCost, orderStatus, orderDate;
        Button viewDetailsButton, rateButton;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIcon = itemView.findViewById(R.id.orderIcon);
            orderId = itemView.findViewById(R.id.orderId);
            orderCost = itemView.findViewById(R.id.orderCost);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            orderDate = itemView.findViewById(R.id.orderDate);
            viewDetailsButton = itemView.findViewById(R.id.viewDetailsButton);
            rateButton = itemView.findViewById(R.id.rateButton);
        }
    }

    // Method to update orders list
    public void updateOrders(List<Order> newOrders) {
        this.orders = newOrders;
        notifyDataSetChanged();
    }
}