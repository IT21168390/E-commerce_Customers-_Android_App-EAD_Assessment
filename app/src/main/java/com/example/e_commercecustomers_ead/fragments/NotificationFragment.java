package com.example.e_commercecustomers_ead.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.e_commercecustomers_ead.R;
import com.example.e_commercecustomers_ead.adapters.NotificationAdapter;
import com.example.e_commercecustomers_ead.models.Notification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationFragment extends Fragment {
    private ImageView backButton;
    private RecyclerView rvNotifications;
    private NotificationAdapter notificationAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        // Initialize RecyclerView
        rvNotifications = view.findViewById(R.id.rvNotifications);
        backButton = view.findViewById(R.id.backButton);
        rvNotifications.setLayoutManager(new LinearLayoutManager(getContext()));


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvNotifications.getContext(), DividerItemDecoration.VERTICAL);
        rvNotifications.addItemDecoration(dividerItemDecoration);

        // Fetch notifications (mock data in this case)
        List<Notification> notifications = fetchNotifications();

        // Set up the adapter
        notificationAdapter = new NotificationAdapter(notifications);
        rvNotifications.setAdapter(notificationAdapter);

        backButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }

    // Mock function to fetch notifications from the database
    private List<Notification> fetchNotifications() {
        List<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification("Your order has been shipped!", new Date()));
        notifications.add(new Notification("Your payment was successful!", new Date()));
        notifications.add(new Notification("New product launch coming soon!", new Date()));
        return notifications;
    }
}