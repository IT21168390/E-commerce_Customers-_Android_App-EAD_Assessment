package com.example.e_commercecustomers_ead.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.e_commercecustomers_ead.R;

public class ReviewUpdateFragment extends Fragment {

    private EditText etReviewInput;
    private Button btnUpdateReview;
    private ImageView backButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_update, container, false);

        etReviewInput = view.findViewById(R.id.etReviewInput);
        btnUpdateReview = view.findViewById(R.id.btnUpdateReview);
        backButton = view.findViewById(R.id.backButton);

        // Handle back button click
        backButton.setOnClickListener(v -> {
            // Navigate back to the previous fragment
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        // Handle Update Review button click
        btnUpdateReview.setOnClickListener(v -> {
            String updatedReview = etReviewInput.getText().toString().trim();
            if (TextUtils.isEmpty(updatedReview)) {
                Toast.makeText(getActivity(), "Please enter your review", Toast.LENGTH_SHORT).show();
            } else {
                // Call the method to update the review (e.g., via API or database)
                updateReview(updatedReview);
            }
        });

        return view;
    }

    private void updateReview(String review) {
        Toast.makeText(getActivity(), "Review updated successfully!", Toast.LENGTH_SHORT).show();
        requireActivity().getSupportFragmentManager().popBackStack();
    }
}