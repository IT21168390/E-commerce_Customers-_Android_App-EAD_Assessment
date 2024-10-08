package com.example.e_commercecustomers_ead.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commercecustomers_ead.R;
import com.example.e_commercecustomers_ead.adapters.VendorReviewCustomerAdapter;
import com.example.e_commercecustomers_ead.api_models.ProductDataModel;
import com.example.e_commercecustomers_ead.models.VendorCustomerReview;
import com.example.e_commercecustomers_ead.services.CartManager;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsFragment extends Fragment {

    private ImageView productImage;
    private TextView productTitle, productCategory, productDescription, productPrice, productRating, vendorName, productStock;
    private Button addToCartButton, backButton;
    private ProductDataModel product;
    // Variable for quantity input
    private NumberPicker quantityPicker;
    private RecyclerView rvVendorReviews;
    private VendorReviewCustomerAdapter vendorReviewCustomerAdapter;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    public static ProductDetailsFragment newInstance(ProductDataModel product) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("product", product);
        fragment.setArguments(args);
        return fragment;
    }

    private CartManager cartManager;  // Field to manage the cart

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            product = (ProductDataModel) getArguments().getSerializable("product");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);

        // Initialize UI components and cart manager
        cartManager = CartManager.getInstance();

        // Initialize other UI components...
        addToCartButton = view.findViewById(R.id.addToCartButton);
        quantityPicker = view.findViewById(R.id.quantityPicker);
        rvVendorReviews = view.findViewById(R.id.rvVendorReviewsCustomer);

        // Initialize UI components
        productImage = view.findViewById(R.id.productImage);
        productTitle = view.findViewById(R.id.productTitle);
        productCategory = view.findViewById(R.id.productCategory);
        productDescription = view.findViewById(R.id.productDescription);
        productPrice = view.findViewById(R.id.productPrice);
        productStock = view.findViewById(R.id.productStock);
        productRating = view.findViewById(R.id.productRating);
        vendorName = view.findViewById(R.id.vendorName);
        addToCartButton = view.findViewById(R.id.addToCartButton);
        backButton = view.findViewById(R.id.backButton);

        List<VendorCustomerReview> vendorReviews = fetchVendorReviews(); // This should fetch data based on the order

        // Setup RecyclerView with adapter
        rvVendorReviews.setLayoutManager(new LinearLayoutManager(getContext()));
        vendorReviewCustomerAdapter = new VendorReviewCustomerAdapter(vendorReviews);
        rvVendorReviews.setAdapter(vendorReviewCustomerAdapter);

        // Set product data to UI
        if (product != null) {
            productImage.setImageResource(product.getImageResource());
            productTitle.setText(product.getName());
            productCategory.setText(product.getCategory());
            productDescription.setText(product.getDescription());
            productPrice.setText(String.format("$%.2f", product.getPrice()));
            productRating.setText(String.format("Rating: %.1f", product.getRating()));
            vendorName.setText(product.getVendorName());
            productStock.setText("Stock: " + product.getStock());
            quantityPicker.setMinValue(1);
            quantityPicker.setMaxValue(15);

            // Set initial button state
            if (cartManager.isInCart(product)) {
                addToCartButton.setText("View Cart");
            } else {
                addToCartButton.setText("Add to Cart");
            }
        }

        // Add to cart button listener
        addToCartButton.setOnClickListener(v -> {
            if (!cartManager.isInCart(product)) {
                int quantity = quantityPicker.getValue();
                cartManager.addToCart(product, quantity);
                addToCartButton.setText("View Cart");
                Toast.makeText(getContext(), "Product added to cart!", Toast.LENGTH_SHORT).show();
            } else {
                // Navigate to CartFragment or CartActivity
                navigateToCart();
            }
        });

        // Back button listener
        backButton.setOnClickListener(v -> {
            // Navigate back to the previous fragment
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }

    private void navigateToCart() {
        // implement this to navigate to your cart fragment or activity
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        CartFragment cartFragment = new CartFragment();
        transaction.replace(R.id.fragment_container, cartFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private List<VendorCustomerReview> fetchVendorReviews() {
        List<VendorCustomerReview> reviews = new ArrayList<>();
        reviews.add(new VendorCustomerReview("1", "Customer A"));
        reviews.add(new VendorCustomerReview("2", "Customer B"));
        return reviews;
    }
}
