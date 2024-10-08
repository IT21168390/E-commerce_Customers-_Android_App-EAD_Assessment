package com.example.e_commercecustomers_ead.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.e_commercecustomers_ead.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ProfileEditFragment extends Fragment {
    private TextView name_input, street_input, city_input, zip_input;
    private ImageView backButton;
    private Button deactivateButton, saveButton;

    private JSONObject user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_edit, container, false);

        // Find views
        name_input = view.findViewById(R.id.name_input);
        street_input = view.findViewById(R.id.street_input);
        city_input = view.findViewById(R.id.city_input);
        zip_input = view.findViewById(R.id.zip_input);
        deactivateButton = view.findViewById(R.id.deactivate_button);
        backButton = view.findViewById(R.id.backButton);
        saveButton = view.findViewById(R.id.save_button);

        // Set up the deactivate button click listener
        deactivateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeactivateConfirmationDialog();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewm) {
                // Call method to update profile data
                new updateProfileData().execute("https://192.168.8.124:45455/api/user/6702343c691f8023a70cbf0d");
            }
        });

        backButton.setOnClickListener(v -> {
            // Navigate back to the previous fragment
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        new LoadProfileData().execute("https://192.168.8.124:45455/api/user/6702343c691f8023a70cbf0d");

        return view;
    }

    // Method to show confirmation dialog for account deactivation
    private void showDeactivateConfirmationDialog() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Deactivate Account")
                .setMessage("Are you sure you want to deactivate your account?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Call method to handle account deactivation
                    new deactivateAccount().execute("https://192.168.8.124:45455/api/User/activeUser/6702343c691f8023a70cbf0d");

                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    public class LoadProfileData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();

            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == null) {
                Toast.makeText(getContext(), "Failed to fetch Users", Toast.LENGTH_SHORT).show();
                return;
            }else{
                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
            }
            
            try{
                JSONObject jsonObject = new JSONObject(result);
                user = jsonObject;
                System.out.println(user);
                name_input.setText(user.getString("name"));
                if (!user.isNull("address")) {
                    JSONObject address = user.getJSONObject("address");
                    street_input.setText(address.getString("street"));
                    city_input.setText(address.getString("city"));
                    zip_input.setText(address.getString("zipCode"));
                }
                // Set other fields as needed
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Error parsing users", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class updateProfileData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                JSONObject user = new JSONObject();
                JSONObject address = new JSONObject();

                address.put("street", street_input.getText().toString());
                address.put("city", city_input.getText().toString());
                address.put("zipCode", zip_input.getText().toString());
                user.put("name", name_input.getText().toString());
                user.put("address", address);

                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(user.toString());
                writer.flush();
                writer.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == null) {
                Toast.makeText(getContext(), "Failed to update Users", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public class deactivateAccount extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();

            try {
                String status = "Deactivated"; // replace with your actual status value
                String encodedStatus = URLEncoder.encode(status, "UTF-8");
                URL url = new URL(urls[0] + "?status=" + encodedStatus);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(user.toString());
                writer.flush();
                writer.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == null) {
                Toast.makeText(getContext(), "Failed to deactivate account", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Account deactivated", Toast.LENGTH_SHORT).show();
            }
        }
    }
}