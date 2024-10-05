package com.example.e_commercecustomers_ead.models;

import java.util.List;

public class VendorReview {

    private String vendorId;
    private String vendorName;
    private List<String> productNames;
    private float starRating;
    private String reviewComment;

    private boolean isEditMode;

    public VendorReview(String vendorId, String vendorName, List<String> productNames) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.productNames = productNames;
        this.starRating = 0 ;  // Default to 0 stars initially
        this.reviewComment = "";
        this.isEditMode = true;  // Initialize in view-only mode
    }

    // Getter and setter methods
    public boolean isEditMode() {
        return isEditMode;
    }

    public void setEditMode(boolean isEditMode) {
        this.isEditMode = isEditMode;
    }

    // Getter and Setter for vendorId
    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    // Getter and Setter for vendorName
    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    // Getter and Setter for productNames
    public List<String> getProductNames() {
        return productNames;
    }

    public void setProductNames(List<String> productNames) {
        this.productNames = productNames;
    }

    // Getter and Setter for starRating
    public float getStarRating() {
        return starRating;
    }



    public void setStarRating(float starRating) {
        this.starRating = starRating;
    }

    // Getter and Setter for reviewComment
    public String getReviewComment() {
        return reviewComment;
    }

    public void setReviewComment(String reviewComment) {
        this.reviewComment = reviewComment;
    }
}
