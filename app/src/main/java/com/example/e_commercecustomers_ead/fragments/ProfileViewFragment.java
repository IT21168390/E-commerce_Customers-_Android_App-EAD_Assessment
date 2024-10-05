package com.example.e_commercecustomers_ead.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.e_commercecustomers_ead.R;

public class ProfileViewFragment extends Fragment {

    private TextView tvName, tvUsername, tvGenderValue, tvBirthdayValue, tvEmailValue, tvAccountStatusValue;
    private ImageView profileIcon;
    private Button btnEdit, btnReview,  btnLogOut;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_view, container, false); // Use your updated XML layout

        // Initialize views using the view object
        tvName = view.findViewById(R.id.tvName);
        tvEmailValue = view.findViewById(R.id.tvEmailValue);
        tvAccountStatusValue = view.findViewById(R.id.tvAccountStatusValue);
        profileIcon = view.findViewById(R.id.profileIcon);
        btnEdit = view.findViewById(R.id.btnEdit);
        btnLogOut = view.findViewById(R.id.btnLogOut);
        btnReview = view.findViewById(R.id.btnReview);

        // Set up button click listeners
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                 transaction.replace(R.id.fragment_container, new ProfileEditFragment());
                 transaction.addToBackStack(null);
                 transaction.commit();
            }
        });

        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new VendorReviewFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement log out logic here
                logOut();
            }
        });

        return view;
    }

    private void logOut() {
        // Add your logic to log out the user
    }
}
