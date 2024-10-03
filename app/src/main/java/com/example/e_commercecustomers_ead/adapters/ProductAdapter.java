package com.example.e_commercecustomers_ead.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.example.e_commercecustomers_ead.R;
import com.example.e_commercecustomers_ead.fragments.ProductDetailsFragment;
import com.example.e_commercecustomers_ead.models.Product;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productCategory.setText(product.getCategory());
        holder.productRating.setRating((float) product.getRating());
        holder.productPrice.setText("$" + product.getPrice());
        //holder.productPrice.setText(String.format("$%.2f", product.getPrice()));
        //holder.productRating.setText(String.format("%.1f", product.getRating()));
        holder.productImage.setImageResource(product.getImageResource());

        // Handle View button click
        holder.buttonViewProduct.setOnClickListener(v -> {
            FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
            ProductDetailsFragment fragment = ProductDetailsFragment.newInstance(product);
            transaction.replace(R.id.fragment_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName;
        TextView productCategory;
        RatingBar productRating;
        TextView productPrice;
        Button buttonViewProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productCategory = itemView.findViewById(R.id.productCategory);
            productRating = itemView.findViewById(R.id.productRating);
            productPrice = itemView.findViewById(R.id.productPrice);
            buttonViewProduct = itemView.findViewById(R.id.buttonViewProduct);
        }
    }
}
