package com.example.e_commercecustomers_ead.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commercecustomers_ead.R;
import com.example.e_commercecustomers_ead.api_models.OrderItem;

import java.util.List;

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.ViewHolder> {

    private Context context;
    private List<OrderItem> orderItems;

    public OrderItemsAdapter(Context context, List<OrderItem> orderItems) {
        this.context = context;
        this.orderItems = orderItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderItem item = orderItems.get(position);
        holder.productNameTextView.setText(item.getProductName());
        holder.quantityTextView.setText("Quantity: " + item.getQuantity());
        holder.priceTextView.setText(String.format("Price: $%.2f", item.getPrice()));
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView, quantityTextView, priceTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
        }
    }
}