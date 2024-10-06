package com.example.e_commercecustomers_ead.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.e_commercecustomers_ead.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ProfileEditFragment extends Fragment {

    private ImageView backButton;

    private Button deactivateButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_edit, container, false);

        // Find views
        deactivateButton = view.findViewById(R.id.deactivate_button);
        backButton = view.findViewById(R.id.backButton);

        // Set up the deactivate button click listener
        deactivateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeactivateConfirmationDialog();
            }
        });

        backButton.setOnClickListener(v -> {
            // Navigate back to the previous fragment
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }

    // Method to show confirmation dialog for account deactivation
    private void showDeactivateConfirmationDialog() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Deactivate Account")
                .setMessage("Are you sure you want to deactivate your account?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Call method to handle account deactivation
                    deactivateAccount();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    // Method to handle account deactivation
    private void deactivateAccount() {
        // Logic for deactivating the account
        Toast.makeText(requireContext(), "Account deactivated", Toast.LENGTH_SHORT).show();
    }
}