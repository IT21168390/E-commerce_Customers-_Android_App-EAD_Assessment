package com.example.e_commercecustomers_ead.fragments;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import com.example.e_commercecustomers_ead.R;
import com.example.e_commercecustomers_ead.adapters.VendorReviewAdapter;
import com.example.e_commercecustomers_ead.models.VendorReview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VendorReviewFragment extends Fragment {

    private ImageView backButton;

    private RecyclerView rvVendorReviews;
    private VendorReviewAdapter vendorReviewAdapter;
    private Button btnSubmitReviews;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_vendor_review, container, false);

        rvVendorReviews = root.findViewById(R.id.rvVendorReviews);
        btnSubmitReviews = root.findViewById(R.id.btnSubmitReviews);
        backButton = root.findViewById(R.id.backButton);

        // Assume VendorReview class has fields like vendorId, vendorName, productList, etc.
        List<VendorReview> vendorReviews = fetchVendorReviews(); // This should fetch data based on the order

        // Setup RecyclerView with adapter
        rvVendorReviews.setLayoutManager(new LinearLayoutManager(getContext()));
        vendorReviewAdapter = new VendorReviewAdapter(vendorReviews);
        rvVendorReviews.setAdapter(vendorReviewAdapter);

        // Handle submit button click
        btnSubmitReviews.setOnClickListener(v -> submitReviews());

        backButton.setOnClickListener(v -> {
            // Navigate back to the previous fragment
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return root;
    }

    private List<VendorReview> fetchVendorReviews() {
        // This method should return a list of vendor reviews related to the current order
        // Mocked example:
        List<VendorReview> reviews = new ArrayList<>();
        reviews.add(new VendorReview("1", "Vendor A", Arrays.asList("Product 1", "Product 2")));
        reviews.add(new VendorReview("2", "Vendor B", Arrays.asList("Product 3")));
        return reviews;
    }

    private void submitReviews() {
        // Logic for submitting the reviews
        List<VendorReview> reviews = vendorReviewAdapter.getVendorReviews();
        // Send these reviews to the server or process them
    }


}

