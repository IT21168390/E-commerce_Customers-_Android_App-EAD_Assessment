package com.example.e_commercecustomers_ead.fragments;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.example.e_commercecustomers_ead.R;
import com.example.e_commercecustomers_ead.adapters.VendorReviewAdapter;
import com.example.e_commercecustomers_ead.models.VendorReview;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VendorReviewFragment extends Fragment {

    private ImageView backButton;
    private RecyclerView rvVendorReviews;
    private VendorReviewAdapter vendorReviewAdapter = null;
    private List<VendorReview> reviewList = new ArrayList<>();

    private List<VendorReview> myList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_vendor_review, container, false);

        rvVendorReviews = root.findViewById(R.id.rvVendorReviews);
        backButton = root.findViewById(R.id.backButton);

        myList = fetchVendorReviews(); // This should fetch data based on the order

        // Setup RecyclerView with adapter
        rvVendorReviews.setLayoutManager(new LinearLayoutManager(getContext()));

        // Handle submit button click

        backButton.setOnClickListener(v -> {
            // Navigate back to the previous fragment
            requireActivity().getSupportFragmentManager().popBackStack();
        });

//         new VendorReviewFragment.LoadVendorReviewsTask().execute("https://192.168.8.124:45455/api/VendorRating/670405849849fe66fcfdedc5");

        return root;
    }

    private List<VendorReview> fetchVendorReviews() {
        // This method should return a list of vendor reviews related to the current order
        List<VendorReview> reviews = new ArrayList<>();
        reviews.add(new VendorReview("1", "Vendor A", Arrays.asList("Product 1", "Product 2")));
        reviews.add(new VendorReview("2", "Vendor B", Arrays.asList("Product 3")));
        return reviews;
    }


    public class LoadVendorReviewsTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            System.out.print(result.toString());
            reviewList.clear();
            super.onPostExecute(result);
            if (result == null) {
                Toast.makeText(getContext(), "Failed to fetch reviews", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
            }

                List<VendorReview> reviews = new ArrayList<>();
                reviews.add(new VendorReview("1", "Vendor A", Arrays.asList("Product 1", "Product 2")));
                reviews.add(new VendorReview("2", "Vendor B", Arrays.asList("Product 3")));

                if (vendorReviewAdapter == null) {
                    vendorReviewAdapter = new VendorReviewAdapter(reviews);
                    rvVendorReviews.setAdapter(vendorReviewAdapter);
                } else {
                    vendorReviewAdapter.notifyDataSetChanged();
                }
        }
    }
}