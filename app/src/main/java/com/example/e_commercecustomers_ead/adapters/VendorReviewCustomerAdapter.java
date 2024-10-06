package com.example.e_commercecustomers_ead.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commercecustomers_ead.R;
import com.example.e_commercecustomers_ead.models.VendorCustomerReview;

import java.util.List;

public class VendorReviewCustomerAdapter extends RecyclerView.Adapter<VendorReviewCustomerAdapter.VendorReviewCustomerViewHolder> {

    private final List<VendorCustomerReview> vendorReviews;

    public VendorReviewCustomerAdapter(List<VendorCustomerReview> vendorReviews) {
        this.vendorReviews = vendorReviews;
    }

    @NonNull
    @Override
    public VendorReviewCustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vendor_review_customer_card, parent, false);
        return new VendorReviewCustomerViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return vendorReviews.size();
    }

    @Override
    public void onBindViewHolder(@NonNull VendorReviewCustomerViewHolder holder, int position) {
        VendorCustomerReview vendorReview = vendorReviews.get(position);
        holder.bind(vendorReview); // Call bind() method to set up the view
    }

    public List<VendorCustomerReview> getVendorReviews() {
        // This returns the updated reviews with ratings and comments
        return vendorReviews;
    }

    class VendorReviewCustomerViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvVendorName;
        private final RatingBar rbVendorRating;
        private final TextView tvVendorReviewComment;

        VendorReviewCustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVendorName = itemView.findViewById(R.id.tvCustomer_Name);
            rbVendorRating = itemView.findViewById(R.id.rbVendor_Rating);
            tvVendorReviewComment = itemView.findViewById(R.id.tvVendorReview_Comment);

        }

        void bind(VendorCustomerReview vendorReview) {
            if (vendorReview.getVendorName() != null) {
                tvVendorName.setText(vendorReview.getVendorName());
            } else {
                tvVendorName.setText("Unknown Vendor");
            }

            // Set RatingBar as read-only and show the rating
            rbVendorRating.setRating(vendorReview.getStarRating());
            rbVendorRating.setIsIndicator(true);  // Make RatingBar read-only

            // Check if the review comment is available
            if (vendorReview.getReviewComment() != null) {
                tvVendorReviewComment.setText(vendorReview.getReviewComment());
            } else {
                tvVendorReviewComment.setText("No review comment available.");
            }
        }
    }
}
