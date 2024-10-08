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
import com.example.e_commercecustomers_ead.api_models.VendorOrderStatus;

import java.util.List;

public class OrderVendorsAdapter extends RecyclerView.Adapter<OrderVendorsAdapter.ViewHolder> {

    private Context context;
    private List<VendorOrderStatus> orderVendors;

    public OrderVendorsAdapter(Context context, List<VendorOrderStatus> orderVendors) {
        this.context = context;
        this.orderVendors = orderVendors;
    }

    @NonNull
    @Override
    public OrderVendorsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_vendor, parent, false);
        return new OrderVendorsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderVendorsAdapter.ViewHolder holder, int position) {
        VendorOrderStatus item = orderVendors.get(position);
        holder.vendorNameTextView.setText(item.getVendorName()+" : ");
        holder.vendorStatusTextView.setText(item.getStatus());
    }

    @Override
    public int getItemCount() {
        return orderVendors.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView vendorNameTextView, vendorStatusTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vendorNameTextView = itemView.findViewById(R.id.orderVendorNameTextView);
            vendorStatusTextView = itemView.findViewById(R.id.orderVendorStatusTextView);
        }
    }
}