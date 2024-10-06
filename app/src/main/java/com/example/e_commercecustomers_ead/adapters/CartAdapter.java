package com.example.e_commercecustomers_ead.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commercecustomers_ead.R;
import com.example.e_commercecustomers_ead.api_models.ProductDataModel;
import com.example.e_commercecustomers_ead.models.Product;
import com.example.e_commercecustomers_ead.services.CartManager;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

//    private List<Product> cartItems;
//    private OnItemRemovedListener onItemRemovedListener;
//
//    public interface OnItemRemovedListener {
//        void onItemRemoved(int position);
//    }
//
//    /*public CartAdapter(List<Product> cartItems) {
//        this.cartItems = cartItems;
//    }*/
//    public CartAdapter(List<Product> cartItems, OnItemRemovedListener listener) {
//        this.cartItems = cartItems;
//        this.onItemRemovedListener = listener;
//    }
    private List<ProductDataModel> cartItems;
    private Context context;
    private CartManager cartManager;

    public CartAdapter(Context context, List<ProductDataModel> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
        this.cartManager = CartManager.getInstance();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate cart item layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        // Get the product at the current position
        ProductDataModel product = cartItems.get(position);

        // Bind the product data to the UI elements in the cart item layout
        holder.productName.setText(product.getName());
        holder.productPrice.setText("Rs." + product.getPrice());
        holder.productRating.setText("Rating: " + product.getRating());
        holder.cartProductQuantity.setText("Quantity: " + cartManager.getQuantity(product));

        // Optionally, you can set the product image
        holder.productImage.setImageResource(product.getImageResource());

        // Set delete button click listener
        holder.deleteButton.setOnClickListener(v -> {
            // Remove item from CartManager
            cartManager.removeFromCart(product);
            // Remove item from adapter data source and notify adapter
            cartItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size());
            updateTotalCost();  // Update the total cost
        });
        /*holder.deleteButton.setOnClickListener(v -> {
            onItemRemovedListener.onItemRemoved(position);
        });*/
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    private void updateTotalCost() {
        double totalCost = cartManager.getTotalCost();
        // Assuming you have a reference to your total cost TextView:
        ((Activity) context).runOnUiThread(() -> {
            TextView totalCostTextView = ((Activity) context).findViewById(R.id.totalCost);
            totalCostTextView.setText("Total: $" + String.format("%.2f", totalCost));
        });
    }

    // ViewHolder class for cart items
    public static class CartViewHolder extends RecyclerView.ViewHolder {

        public TextView productName, productPrice, productRating, cartProductQuantity;
        public ImageView productImage;
        public ImageView deleteButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.cartProductName);
            productPrice = itemView.findViewById(R.id.cartProductPrice);
            productRating = itemView.findViewById(R.id.cartProductRating);
            cartProductQuantity = itemView.findViewById(R.id.cartProductQuantity);
            productImage = itemView.findViewById(R.id.cartProductImage);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
