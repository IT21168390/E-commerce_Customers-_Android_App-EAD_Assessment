package com.example.e_commercecustomers_ead.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commercecustomers_ead.R;
import com.example.e_commercecustomers_ead.models.VendorReview;

import java.util.List;

public class VendorReviewAdapter extends RecyclerView.Adapter<VendorReviewAdapter.VendorReviewViewHolder> {

    private List<VendorReview> vendorReviews;

    public VendorReviewAdapter(List<VendorReview> vendorReviews) {
        this.vendorReviews = vendorReviews;
    }

    @NonNull
    @Override
    public VendorReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vendor_review_card, parent, false);
        return new VendorReviewViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return vendorReviews.size();
    }

    @Override
    public void onBindViewHolder(@NonNull VendorReviewViewHolder holder, int position) {
        VendorReview vendorReview = vendorReviews.get(position);
        holder.bind(vendorReview, position); // Call bind() method to set up the view
    }

    public List<VendorReview> getVendorReviews() {
        // This returns the updated reviews with ratings and comments
        return vendorReviews;
    }

    class VendorReviewViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvVendorName;
        private final TextView tvProducts;
        private final RatingBar rbVendorRating;
        private final EditText etVendorReviewComment;
        private final TextView tvVendorReviewComment;
        private final Button btnEditSave;
        private final LinearLayout reviewLayout;

        VendorReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVendorName = itemView.findViewById(R.id.tvVendorName);
            tvProducts = itemView.findViewById(R.id.tvProducts);
            rbVendorRating = itemView.findViewById(R.id.rbVendorRating);
            etVendorReviewComment = itemView.findViewById(R.id.etVendorReviewComment);
            tvVendorReviewComment = itemView.findViewById(R.id.tvVendorReviewComment);

            btnEditSave = itemView.findViewById(R.id.btnEditSave);

            reviewLayout = itemView.findViewById(R.id.reviewLayout);
        }

        void bind(VendorReview vendorReview, int position) {
            tvVendorName.setText(vendorReview.getVendorName());
            tvProducts.setText(String.join(", ", vendorReview.getProductNames()));

            // Set RatingBar as read-only
            rbVendorRating.setRating(vendorReview.getStarRating());

            if (vendorReview.getReviewComment() != null && vendorReview.getStarRating() > 0) {
                // Set background color for rated reviews
//                reviewLayout.setBackgroundColor(itemView.getResources().getColor(R.color.ratedReviewBackground));
            } else {
                // Set background color for unrated reviews
//                reviewLayout.setBackgroundColor(itemView.getResources().getColor(R.color.unratedReviewBackground));
            }

            // Handle edit mode for comment
            if (vendorReview.isEditMode()) {
                etVendorReviewComment.setEnabled(true);  // Allow editing
                rbVendorRating.setIsIndicator(false);
                btnEditSave.setText("Submit");
            } else {
                etVendorReviewComment.setEnabled(false);  // Disable editing
                rbVendorRating.setIsIndicator(true);
                btnEditSave.setText("Edit");
            }
            boolean initial = vendorReview.getReviewComment() == null;

            // Set the review comment based on edit mode
            if (vendorReview.isEditMode()) {
                etVendorReviewComment.setText(vendorReview.getReviewComment());
                etVendorReviewComment.setEnabled(true);  // Allow editing
                etVendorReviewComment.setVisibility(View.VISIBLE);
                tvVendorReviewComment.setVisibility(View.GONE); // Hide the TextView
                btnEditSave.setText("Submit");
            } else {
                tvVendorReviewComment.setText(vendorReview.getReviewComment());
                tvVendorReviewComment.setVisibility(View.VISIBLE); // Show the TextView
                etVendorReviewComment.setVisibility(View.GONE);  // Hide the EditText
                btnEditSave.setText("Edit");
            }

            // Edit/Save button listener
            btnEditSave.setOnClickListener(v -> {
                if (vendorReview.isEditMode()) {
                    // Save mode: Save comment and switch to view-only
                    vendorReview.setReviewComment(etVendorReviewComment.getText().toString());
                    vendorReview.setStarRating(rbVendorRating.getRating());
                    vendorReview.setEditMode(false);
                    notifyItemChanged(position);
                    // Save the updated comment to the database here if needed
//                    saveReviewToDatabase(vendorReview.getVendorId(), vendorReview.getReviewComment());
                } else {
                    // Edit mode: Allow editing the comment
                    vendorReview.setEditMode(true);
                    notifyItemChanged(position);
                }
            });
        }
    }
}