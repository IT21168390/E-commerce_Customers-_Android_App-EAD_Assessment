package com.example.e_commercecustomers_ead.models;

import java.util.List;

public class VendorCustomerReview {

    private String vendorId;
    private String vendorName;
    private float starRating;
    private String reviewComment;

    public VendorCustomerReview(String vendorId, String vendorName) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.starRating = 0 ;  // Default to 0 stars initially
        this.reviewComment = "sdcas";
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


    // Getter and Setter for starRating
    public float getStarRating() {
        return starRating;
    }

    // Getter and Setter for reviewComment
    public String getReviewComment() {
        return reviewComment;
    }

   }
