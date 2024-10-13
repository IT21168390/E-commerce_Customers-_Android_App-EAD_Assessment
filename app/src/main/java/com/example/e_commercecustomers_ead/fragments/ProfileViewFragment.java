package com.example.e_commercecustomers_ead.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.e_commercecustomers_ead.api.API;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import com.example.e_commercecustomers_ead.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProfileViewFragment extends Fragment {

    private TextView tvName,tvRole, tvEmailValue, tvAccountStatusValue, tvAddress, notification_count;
    private ImageView profileIcon, ivNotificationIcon, btnEdit;
    private Button btnReview,  btnLogOut;

    private JSONObject user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_view, container, false); // Use your updated XML layout

        // Initialize views using the view object
        tvName = view.findViewById(R.id.tvName);
        tvRole = view.findViewById(R.id.tvRole);
        tvEmailValue = view.findViewById(R.id.tvEmailValue);
        tvAccountStatusValue = view.findViewById(R.id.tvAccountStatusValue);
        tvAddress = view.findViewById(R.id.tvAddress);
        profileIcon = view.findViewById(R.id.profileIcon);
        btnEdit = view.findViewById(R.id.btnEdit);
        btnLogOut = view.findViewById(R.id.btnLogOut);
        btnReview = view.findViewById(R.id.btnReview);
        ivNotificationIcon = view.findViewById(R.id.ivNotificationIcon);
        notification_count = view.findViewById(R.id.notification_count);

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

        ivNotificationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new NotificationFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });


        new LoadProfileData().execute(API.API+"/user/670a81855b5281740d5c6d70");

        return view;
    }

    private void showConfirmationDialog() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout from your account?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    logOut();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void logOut() {
        Toast.makeText(requireContext(), "Logged out Successfully", Toast.LENGTH_SHORT).show();
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
                tvName.setText(user.getString("name"));
                tvRole.setText(user.getString("userType"));
                tvEmailValue.setText(user.getString("email"));
                tvAccountStatusValue.setText(user.getString("status"));
                if (!user.isNull("address")) {
                    JSONObject addressObject = user.getJSONObject("address");
                    String street = addressObject.getString("street");
                    String city = addressObject.getString("city");
                    String zipCode = addressObject.getString("zip_code");
                    String address = street + ", " + city + ", " + zipCode;
                    System.out.println(address);
                    tvAddress.setText(address);
                }
                // Set other fields as needed
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Error parsing users", Toast.LENGTH_SHORT).show();
            }
        }
        private void fetchUnreadNotificationsCount(String userId) {
            new AsyncTask<Void, Void, Integer>() {
                @Override
                protected Integer doInBackground(Void... voids) {
                    int count = 0;
                    try {
                        URL url = new URL(API.API + "/Notification/user/" + userId + "/unread-count");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                        conn.setRequestProperty("Accept","application/json");

                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line = reader.readLine();
                        count = Integer.parseInt(line);

                        reader.close();
                        conn.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return count;
                }

                @Override
                protected void onPostExecute(Integer count) {
                    super.onPostExecute(count);
                    // TODO: Update your TextView with the count
                    // If the count is 0, set the visibility of the TextView to GONE
                    // Otherwise, set the visibility to VISIBLE and set the text to the count
                    notification_count.setText(String.valueOf(count));

                }
            }.execute();
        }
    }

}
