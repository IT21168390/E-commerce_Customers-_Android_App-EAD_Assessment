package com.example.e_commercecustomers_ead.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.example.e_commercecustomers_ead.api_models.ProductDataModel;
import com.example.e_commercecustomers_ead.models.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//public class ProductsFragment extends Fragment {
//
//    private RecyclerView recyclerView;
//    private ProductAdapter productAdapter;
//    private List<Product> productList;
//
//    public ProductsFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_products, container, false);
//
//        // Initialize the RecyclerView
//        recyclerView = view.findViewById(R.id.recyclerViewProducts);
//
//        // Set the LayoutManager
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // Two columns
//
//        // Load the product data (make sure this is done before setting the adapter)
//        loadProducts();
//
//        // Initialize and set the adapter
//        productAdapter = new ProductAdapter(getContext(), productList);
//        recyclerView.setAdapter(productAdapter);
//
//        // Load the product data from API
//        //new FetchProductsTask().execute("https://yourapiurl.com/api/products");  // Replace with your API URL
//
//
//        return view;
//    }
//
//
//    private void loadProducts() {
//        // Dummy product data
//        productList = new ArrayList<>(); // Make sure the list is initialized
//
//        productList.add(new Product("Product 1", "Electronics", "Best in the market", "John Marston", 4.5, 50.00, R.drawable.ic_star_filled));
//        productList.add(new Product("Product 2", "Electronics", "Best in the market", "John Marston", 4.0, 45.00, R.drawable.ic_star_filled));
//        productList.add(new Product("Product 3", "Electronics", "Best in the market", "John Marston", 3.5, 30.00, R.drawable.ic_star_filled));
//        // Add more products here
//    }
//
//    // AsyncTask to fetch data from the API
//    private class FetchProductsTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... urls) {
//            String apiUrl = urls[0];
//            StringBuilder result = new StringBuilder();
//            try {
//                URL url = new URL(apiUrl);
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("GET");
//                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    result.append(line);
//                }
//                reader.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return result.toString();
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//
//            // Parse the JSON response and update the RecyclerView
//            try {
//                productList = new ArrayList<>(); // Initialize the list
//                JSONArray jsonArray = new JSONArray(result);
//
//                // Loop through each product in the JSON array
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                    // Assuming your API returns these fields for each product
//                    String name = jsonObject.getString("name");
//                    String category = jsonObject.getString("category");
//                    String description = jsonObject.getString("description");
//                    String vendor = jsonObject.getString("vendor");
//                    double rating = jsonObject.getDouble("rating");
//                    double price = jsonObject.getDouble("price");
//                    int imageResource = R.drawable.ic_star_filled; // Placeholder image
//
//                    // Create a new Product object and add it to the list
//                    productList.add(new Product(name, category, description, vendor, rating, price, imageResource));
//                }
//
//                // Set the adapter with the fetched product data
//                productAdapter = new ProductAdapter(getContext(), productList);
//                recyclerView.setAdapter(productAdapter);
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//}
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
        //loadProducts();
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


        // Load the product data from API
        //new FetchProductsTask().execute("http://192.168.56.1:7237/api/Product/GetAllProductList");  // Replace with your API URL
        // Load products from API
        //new LoadProductsTask().execute("https:192.168.56.1:44378/api/product/GetAllProductList"); // Replace with API URL https://localhost:44378/api/product/GetAllProductList
        new LoadProductsTask().execute("https://192.168.86.91:45457/api/product/GetAllProductList");



        return view;
    }

    /*private void loadProducts() {
        productList = new ArrayList<>();
        productList.add(new Product("Product 1", "Electronics", "Best in the market", "John Marston", 4.5, 50.00, R.drawable.ic_star_filled, 1000));
        productList.add(new Product("Product 2", "Clothing", "Stylish and trendy", "Jane Smith", 4.0, 45.00, R.drawable.ic_star_filled, 1000));
        productList.add(new Product("Product 3", "Electronics", "Top quality", "Mike Johnson", 3.5, 30.00, R.drawable.ic_star_filled, 1000));
        // Add more products
    }*/

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
            System.out.println("*********************************************");
            if (result == null) {
                Toast.makeText(getContext(), "Failed to fetch products", Toast.LENGTH_SHORT).show();
                return;
            }
            System.out.println("*********************************************");
//            try {
//                //JSONObject responseObj = new JSONObject(result);
//                //JSONArray resultsArray = responseObj.getJSONArray("results");
//                // Log the entire response for debugging
//                JSONArray resultsArray = new JSONArray(result);
//                System.out.println(resultsArray);
//                Log.d("PRODUCTS FRAGMENT", "API Response: " + resultsArray.toString());
//                //JSONArray jsonArray = new JSONArray(result);
//                /*productList.clear();
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    String name = jsonObject.getString("name");
//                    String category = jsonObject.getString("category");
//                    String description = jsonObject.getString("description");
//                    String manufacturer = jsonObject.getString("manufacturer");
//                    double rating = jsonObject.getDouble("rating");
//                    double price = jsonObject.getDouble("price");
//                    int imageResource = R.drawable.ic_star_filled; // Replace with actual image resource if available
//                    int stock = jsonObject.getInt("stock");
//
//                    Product product = new Product(name, category, description, manufacturer, rating, price, imageResource, stock);
//                    productList.add(product);
//                }*/
//
//                // Copy all products to filtered list initially
//                //filteredProductList.clear();
//                //filteredProductList.addAll(productList);
//
//                // Notify the adapter about data changes
//                //productAdapter.notifyDataSetChanged();
//
//                // Log the response data
//                //Log.d("LoadProductsTask", "Response: " + result);
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
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
                            R.drawable.ic_star_filled, // Placeholder for vendor name, adjust as necessary
                            productJson.getString("status"), // Placeholder rating, adjust as necessary
                            productJson.getString("createdAt"),
                            productJson.getString("createdAt")
                    );
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
    // AsyncTask to fetch data from the API
    /*private class FetchProductsTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String apiUrl = urls[0];
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL(apiUrl);
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
            System.out.println("-------------------------"+result);
            return result.toString();
        }*/

        /*@Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Parse the JSON response and update the RecyclerView
            try {
                productList = new ArrayList<>(); // Initialize the list
                JSONArray jsonArray = new JSONArray(result);

                // Loop through each product in the JSON array
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    // Assuming your API returns these fields for each product
                    String name = jsonObject.getString("name");
                    String category = jsonObject.getString("category");
                    String description = jsonObject.getString("description");
                    String vendor = jsonObject.getString("vendor");
                    double rating = jsonObject.getDouble("rating");
                    double price = jsonObject.getDouble("price");
                    int imageResource = R.drawable.ic_star_filled; // Placeholder image

                    // Create a new Product object and add it to the list
                    productList.add(new Product(name, category, description, vendor, rating, price, imageResource));
                }

                // Set the adapter with the fetched product data
                productAdapter = new ProductAdapter(getContext(), productList);
                recyclerView.setAdapter(productAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }*/

}