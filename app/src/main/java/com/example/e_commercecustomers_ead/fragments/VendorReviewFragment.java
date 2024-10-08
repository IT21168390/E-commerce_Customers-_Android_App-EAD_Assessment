package com.example.e_commercecustomers_ead.fragments;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.example.e_commercecustomers_ead.R;
import com.example.e_commercecustomers_ead.adapters.VendorReviewAdapter;
import com.example.e_commercecustomers_ead.models.VendorReview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class VendorReviewFragment extends Fragment {

    private ImageView backButton;
    private RecyclerView rvVendorReviews;
    private VendorReviewAdapter vendorReviewAdapter;
    private Button btnSubmitReviews;
    private List<VendorReview> reviewList = new ArrayList<>();

    private List<VendorReview> myList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_vendor_review, container, false);

        rvVendorReviews = root.findViewById(R.id.rvVendorReviews);
        btnSubmitReviews = root.findViewById(R.id.btnSubmitReviews);
        backButton = root.findViewById(R.id.backButton);

        // Assume VendorReview class has fields like vendorId, vendorName, productList, etc.
        myList = fetchVendorReviews(); // This should fetch data based on the order

        // Setup RecyclerView with adapter
        rvVendorReviews.setLayoutManager(new LinearLayoutManager(getContext()));
        vendorReviewAdapter = new VendorReviewAdapter(reviewList);
        rvVendorReviews.setAdapter(vendorReviewAdapter);

        // Handle submit button click
        btnSubmitReviews.setOnClickListener(v -> {
            new postVendorReviewTask().execute("https://192.168.8.124:45455/api/VendorRating/6702343c691f8023a70cbf0d");
        });

        backButton.setOnClickListener(v -> {
            // Navigate back to the previous fragment
            requireActivity().getSupportFragmentManager().popBackStack();
        });

         new LoadVendorReviewsTask().execute("https://192.168.8.124:45455/api/VendorRating/670405849849fe66fcfdedc5");

        return root;
    }

    private List<VendorReview> fetchVendorReviews() {
        // This method should return a list of vendor reviews related to the current order
        // Mocked example:
        List<VendorReview> reviews = new ArrayList<>();
        reviews.add(new VendorReview("1", "Vendor A", Arrays.asList("Product 1", "Product 2")));
        reviews.add(new VendorReview("2", "Vendor B", Arrays.asList("Product 3")));
        return reviews;
    }

    public class postVendorReviewTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestProperty("Content-Type", "application/json");

                    // Create a JSON array for the reviews
                    JSONArray reviewsArray = new JSONArray();
                    for (VendorReview review : reviewList) {
                        JSONObject reviewJson = new JSONObject();
                        reviewJson.put("rating", review.getStarRating());
                        reviewJson.put("review", review.getReviewComment());
                        reviewJson.put("vendorId", review.getVendorId());
                        reviewsArray.put(reviewJson);
                    }

                    // Send the JSON array to the server
                    OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                    writer.write(reviewsArray.toString());
                    writer.flush();

                    InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());
                    int data = reader.read();
                    while (data != -1) {
                        result.append((char) data);
                        data = reader.read();
                    }
                    return result.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == null) {
                Toast.makeText(getContext(), "Failed to submit reviews", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Reviews submitted successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }


//   public class LoadVendorReviewsTask extends AsyncTask<String, Void, String> {
//
//       @Override
//       protected String doInBackground(String... urls) {
//           StringBuilder result = new StringBuilder();
//           try {
//               URL url = new URL(urls[0]);
//               HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//               try {
//                   InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());
//                   int data = reader.read();
//                   while (data != -1) {
//                       result.append((char) data);
//                       data = reader.read();
//                   }
//                   return result.toString();
//               } finally {
//                   urlConnection.disconnect();
//               }
//           } catch (Exception e) {
//               return "Error: " + e.getMessage();
//           }
//       }
//
//       @Override
//       protected void onPostExecute(String result) {
//           reviewList.clear();
//           super.onPostExecute(result);
//           if (result == null) {
//               Toast.makeText(getContext(), "Failed to fetch reviews", Toast.LENGTH_SHORT).show();
//               return;
//           } else {
//               Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
//           }
//           try {
//               JSONArray jsonArray;
//               Object json = new JSONTokener(result).nextValue();
//               if (json instanceof JSONObject) {
//                   JSONObject jsonObject = new JSONObject(result);
//                   jsonArray = jsonObject.getJSONArray("yourArrayKey"); // replace "yourArrayKey" with the actual key
//               } else {
//                   jsonArray = new JSONArray(result);
//               }
//
//               for (int i = 0; i < jsonArray.length(); i++) {
//                   JSONObject jsonObject = jsonArray.getJSONObject(i);
//                   Double rating = jsonObject.getDouble("rating");
//                   String review = jsonObject.getString("review");
//                   String vendorId = jsonObject.getString("vendorId");
//
//                   VendorReview vendorReview = new VendorReview(vendorId, "", Arrays.asList("sdcsdc", "dcd", "dsc"), rating, review, vendorId);
//                   reviewList.add(vendorReview);
//               }
//               if (vendorReviewAdapter == null) {
//                   vendorReviewAdapter = new VendorReviewAdapter(reviewList);
//                   rvVendorReviews.setAdapter(vendorReviewAdapter);
//               } else {
//                   vendorReviewAdapter.notifyDataSetChanged();
//               }
//
//           } catch (JSONException e) {
//               throw new RuntimeException(e);
//           }
//       }

       public class LoadVendorReviewsTask extends AsyncTask<String, Void, String> {

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
               System.out.print(result.toString());
               reviewList.clear();
               super.onPostExecute(result);
               if (result == null) {
                   Toast.makeText(getContext(), "Failed to fetch reviews", Toast.LENGTH_SHORT).show();
                   return;
               } else {
                   Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
               }
               try {
                   JSONArray jsonArray = new JSONArray(result);

                   System.out.println(jsonArray.toString());
                   for (int i = 0; i < jsonArray.length(); i++) {
                       JSONObject jsonObject = jsonArray.getJSONObject(i);
//                       Double rating = jsonObject.getDouble("rating");
//                       String review = jsonObject.getString("comment");
//                       String vendorId = jsonObject.getString("vendorId");
                       VendorReview reviews = new VendorReview("vendorId", "dscds", Arrays.asList("sdcsdc", "dcd", "dsc"));
                       reviewList.add(reviews);
                   }

                   List<VendorReview> reviews = new ArrayList<>();
                   reviews.add(new VendorReview("1", "Vendor A", Arrays.asList("Product 1", "Product 2")));
                   reviews.add(new VendorReview("2", "Vendor B", Arrays.asList("Product 3")));

//                   if (vendorReviewAdapter == null) {
//                       vendorReviewAdapter = new VendorReviewAdapter(reviews);
//                       rvVendorReviews.setAdapter(vendorReviewAdapter);
//                   } else {
//                       vendorReviewAdapter.notifyDataSetChanged();
//                   }

               } catch (JSONException e) {
                   e.printStackTrace();
                   Toast.makeText(getContext(), "Error parsing reviews", Toast.LENGTH_SHORT).show();
               }
           }
       }
}