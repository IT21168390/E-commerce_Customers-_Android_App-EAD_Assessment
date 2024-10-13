package com.example.e_commercecustomers_ead.fragments;

import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.e_commercecustomers_ead.R;
import com.example.e_commercecustomers_ead.adapters.NotificationAdapter;
import com.example.e_commercecustomers_ead.api.API;
import com.example.e_commercecustomers_ead.models.Notification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationFragment extends Fragment {
    private ImageView backButton;
    private RecyclerView rvNotifications;
    private NotificationAdapter notificationAdapter;

    private List<Notification> notificationsList = new ArrayList<>();

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


        backButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        new NotificationFragment.LoadNotificationData().execute(API.API+"/Notification/user/66f2da668b3a0ec04d2011c6");


        return view;
    }

    public class LoadNotificationData extends AsyncTask<String, Void, String> {

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
            notificationsList.clear();
            super.onPostExecute(result);
            if (result == null) {
                Toast.makeText(getContext(), "Failed to fetch notifications", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
            }
            try {
                JSONArray jsonArray = new JSONArray(result);

                System.out.println(jsonArray);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String id = jsonObject.getString("_id");
                    String Msg = jsonObject.getString("message");
                    Boolean isRead = jsonObject.getBoolean("is_read");
                    String dateString = jsonObject.getString("createdAt");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    Date date = sdf.parse(dateString);
                    Notification notification = new Notification(id, Msg, date, isRead);
                    notificationsList.add(notification);
                }

                if (notificationAdapter == null) {
                    notificationAdapter = new NotificationAdapter(notificationsList);
                    rvNotifications.setAdapter(notificationAdapter);
                } else {
                    notificationAdapter.notifyDataSetChanged();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Error parsing notifications", Toast.LENGTH_SHORT).show();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

}