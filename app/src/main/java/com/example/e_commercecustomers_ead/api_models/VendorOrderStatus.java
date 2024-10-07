package com.example.e_commercecustomers_ead.api_models;

public class VendorOrderStatus {
    public String VendorId;

    public String VendorName;

    public String Status;

    public boolean Rated;

    public String getVendorId() {
        return VendorId;
    }

    public void setVendorId(String vendorId) {
        VendorId = vendorId;
    }

    public String getVendorName() {
        return VendorName;
    }

    public void setVendorName(String vendorName) {
        VendorName = vendorName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public boolean isRated() {
        return Rated;
    }

    public void setRated(boolean rated) {
        Rated = rated;
    }
}
