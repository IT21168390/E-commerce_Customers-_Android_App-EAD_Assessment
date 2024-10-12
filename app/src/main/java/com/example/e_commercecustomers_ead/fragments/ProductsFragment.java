package com.example.e_commercecustomers_ead.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.e_commercecustomers_ead.R;
import com.example.e_commercecustomers_ead.adapters.ProductAdapter;
import com.example.e_commercecustomers_ead.api.API;
import com.example.e_commercecustomers_ead.api_models.ProductDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<ProductDataModel> productList;
    private List<ProductDataModel> filteredProductList;
    private EditText searchBar;
    private Spinner categoryFilter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Initialize the search bar and category filter
        searchBar = view.findViewById(R.id.searchBar);
        categoryFilter = view.findViewById(R.id.categoryFilter);

        // Initialize product lists
        productList = new ArrayList<>();
        filteredProductList = new ArrayList<>(productList); // Initialize with all products

        // Set up category filter spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryFilter.setAdapter(adapter);

        // Initialize adapter
        productAdapter = new ProductAdapter(getContext(), filteredProductList);
        recyclerView.setAdapter(productAdapter);

        // Add search functionality
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterProducts(charSequence.toString(), categoryFilter.getSelectedItem().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // Add category filter functionality
        categoryFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                filterProducts(searchBar.getText().toString(), categoryFilter.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });


        // Load products from API
        new LoadProductsTask().execute("");//.execute("https://192.xxx.xx.xx:XXXXX/api/product/GetAllProductList");



        return view;
    }

    private void filterProducts(String searchQuery, String selectedCategory) {
        filteredProductList.clear();

        for (ProductDataModel product : productList) {
            // Apply search query and category filter
            if (product.getName().toLowerCase().contains(searchQuery.toLowerCase()) &&
                    (selectedCategory.equals("All") || product.getCategory().equals(selectedCategory))) {
                filteredProductList.add(product);
            }
        }

        // Notify the adapter about data changes
        productAdapter.notifyDataSetChanged();
    }




    private class LoadProductsTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            try {
                System.out.println("*******************"+urls[0]+"**************************");
                URL url = new URL(API.API+"/product/GetAllProductList");//URL(urls[0]);
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
            System.out.println("*********************************************");
            if (result == null) {
                Toast.makeText(getContext(), "Failed to fetch products", Toast.LENGTH_SHORT).show();
                return;
            }
            System.out.println("*********************************************");
            try {
                JSONArray resultsArray = new JSONArray(result);
                productList.clear();

                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject productJson = resultsArray.getJSONObject(i);
                    ProductDataModel product = new ProductDataModel(
                            productJson.getString("id"),
                            productJson.getString("productId"),
                            productJson.getString("name"),
                            productJson.getString("category"),
                            productJson.getString("vendorId"),
                            productJson.getString("vendorName"),
                            productJson.getDouble("price"),
                            productJson.getString("description"),
                            productJson.getInt("stockQuantity"),
                            R.drawable.ic_star_filled, // Placeholder for vendor name
                            productJson.getString("status"), // Placeholder rating
                            productJson.getString("createdAt"),
                            productJson.getString("createdAt")
                    );
                    if(product.getCategory().equals("Electronic")){
                        product.setImageResource(R.drawable.electronics_device);
                    } else if(product.getCategory().equals("Furniture")){
                        product.setImageResource(R.drawable.furniture_armchair);
                    } else if(product.getCategory().equals("Stationery")){
                        product.setImageResource(R.drawable.stationary_notebook);
                    }
                    productList.add(product);
                }

                // Refresh filtered product list and update the adapter
                filteredProductList.clear();
                filteredProductList.addAll(productList);
                productAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Error parsing products", Toast.LENGTH_SHORT).show();
            }
        }
    }
}