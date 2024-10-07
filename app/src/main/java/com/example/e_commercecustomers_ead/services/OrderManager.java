package com.example.e_commercecustomers_ead.services;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.e_commercecustomers_ead.R;
import com.example.e_commercecustomers_ead.api.OrderApiService;
import com.example.e_commercecustomers_ead.api.RetrofitClient;
import com.example.e_commercecustomers_ead.api_models.Address;
import com.example.e_commercecustomers_ead.api_models.OrderItem;
import com.example.e_commercecustomers_ead.api_models.OrderModel;
import com.example.e_commercecustomers_ead.api_models.ProductDataModel;
import com.example.e_commercecustomers_ead.api_models.VendorOrderStatus;
import com.example.e_commercecustomers_ead.fragments.OrderHistoryFragment;
import com.example.e_commercecustomers_ead.models.Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderManager {
    private static OrderManager instance;
    private List<OrderModel> orders;

    private OrderManager() {
        orders = new ArrayList<>();
        //fetchOrdersFromApi();
        // For demonstration, let's add some dummy orders
        //populateDummyOrders();

        new OrderManager.LoadOrdersTask().execute("https://192.168.86.91:45457/api/Orders");
    }

    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    /*private void populateDummyOrders() {
        orders.add(new Order(UUID.randomUUID().toString(), 1500.00, "Pending", new Date(), "Order details for pending order."));
        orders.add(new Order(UUID.randomUUID().toString(), 2500.50, "Dispatched", new Date(), "Order details for dispatched order."));
        orders.add(new Order(UUID.randomUUID().toString(), 2500.50, "Partially Delivered", new Date(), "Order details for partially delivered order."));
        orders.add(new Order(UUID.randomUUID().toString(), 3500.75, "Delivered", new Date(), "Order details for delivered order."));
        orders.add(new Order(UUID.randomUUID().toString(), 4500.25, "Cancelled", new Date(), "Order details for cancelled order."));
    }*/
    private class LoadOrdersTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            try {
                System.out.println("-----------------------"+urls[0]+"--------------------------");
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
            System.out.println("-------------------------------------------");
            if (result == null) {
                return;
            }
            System.out.println("*********************************************");
            try {
                JSONArray resultsArray = new JSONArray(result);
                orders.clear();

                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject orderJson = resultsArray.getJSONObject(i);
                    /*OrderModel order = new OrderModel(
                            orderJson.getString("id"),
                            orderJson.getString("orderID"),
                            orderJson.getString("customerId"),
                            orderJson.getString("customerName"),
                            orderJson.getString("orderStatus"),
                            orderJson.getJSONArray("orderItems"),
                            orderJson.getDouble("totalAmount"),
                            orderJson.getString("note"),
                            orderJson.getJSONObject("shippingAddress"),
                            orderJson.getString("placedAt"),
                            orderJson.getString("updayeddAt"),
                            orderJson.getJSONArray("vendorStatus")
                    );*/
                    // Parse order items
                    JSONArray orderItemsArray = orderJson.getJSONArray("orderItems");
                    List<OrderItem> orderItems = new ArrayList<>();
                    for (int j = 0; j < orderItemsArray.length(); j++) {
                        JSONObject itemJson = orderItemsArray.getJSONObject(j);
                        OrderItem orderItem = new OrderItem();
                        orderItem.setProductId(itemJson.getString("productId"));
                        orderItem.setProductName(itemJson.getString("productName"));
                        orderItem.setQuantity(itemJson.getInt("quantity"));
                        orderItem.setPrice(itemJson.getDouble("price"));
                        orderItems.add(orderItem);
                    }

                    // Parse shipping address
                    JSONObject shippingAddressJson = orderJson.getJSONObject("shippingAddress");
                    Address shippingAddress = new Address();
                    shippingAddress.setStreet(shippingAddressJson.getString("street"));
                    shippingAddress.setCity(shippingAddressJson.getString("city"));
                    shippingAddress.setZipCode(shippingAddressJson.getString("zipCode"));

                    // Parse vendor status
                    JSONArray vendorStatusArray = orderJson.getJSONArray("vendorStatus");
                    List<VendorOrderStatus> vendorStatus = new ArrayList<>();
                    for (int k = 0; k < vendorStatusArray.length(); k++) {
                        JSONObject vendorJson = vendorStatusArray.getJSONObject(k);
                        VendorOrderStatus vendorOrderStatus = new VendorOrderStatus();
                        vendorOrderStatus.setVendorId(vendorJson.getString("vendorId"));
                        vendorOrderStatus.setVendorName(vendorJson.getString("vendorName"));
                        vendorOrderStatus.setStatus(vendorJson.getString("status"));
                        vendorOrderStatus.setRated(vendorJson.getBoolean("rated"));
                        vendorStatus.add(vendorOrderStatus);
                    }

                    // Create OrderModel object
                    OrderModel order = new OrderModel(
                            orderJson.getString("id"),
                            orderJson.getString("orderID"),
                            orderJson.getString("customerId"),
                            orderJson.getString("customerName"),
                            orderJson.getString("orderStatus"),
                            orderItems,
                            orderJson.getDouble("totalAmount"),
                            orderJson.optString("note", null), // Use optString to handle null notes
                            shippingAddress,
                            orderJson.getString("placedAt"),
                            orderJson.getString("updatedAt"),
                            vendorStatus
                    );

                    orders.add(order);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    /*private void fetchOrdersFromApi() {
        OrderApiService apiService = RetrofitClient.getRetrofitInstance().create(OrderApiService.class);
        Call<List<Order>> call = apiService.getAllOrders();

        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    //orders.addAll(response.body());
                    System.out.println("-----------------------------------------------------------"+response.body());
                } else {
                    Log.e("OrderManager", "Failed to fetch orders: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.e("OrderManager", "Error fetching orders", t);
            }
        });
    }*/

    public List<OrderModel> getOrdersByStatus(String status) {
        List<OrderModel> filteredOrders = new ArrayList<>();
        for (OrderModel order : orders) {
            if (status.equalsIgnoreCase("Pending") &&
                    (order.getOrderStatus().equalsIgnoreCase("Pending"))) {
                filteredOrders.add(order);
            } else if (status.equalsIgnoreCase("Dispatched") &&
                    (order.getOrderStatus().equalsIgnoreCase("Dispatched") ||
                            order.getOrderStatus().equalsIgnoreCase("Partially Delivered"))) {
                filteredOrders.add(order);
            } else if (status.equalsIgnoreCase("Cancelled") &&
                    (order.getOrderStatus().equalsIgnoreCase("Cancelled") ||
                            order.getOrderStatus().equalsIgnoreCase("Cancellation Requested"))) {
                filteredOrders.add(order);
            } else if (order.getOrderStatus().equalsIgnoreCase(status)) {
                filteredOrders.add(order);
            }
            /*if (order.getOrderStatus().equalsIgnoreCase(status)) {
                filteredOrders.add(order);
            }*/
        }
        return filteredOrders;
    }

    public void addOrder(OrderModel order) {
        orders.add(order);
    }

    public void rateOrder(OrderModel order) {
        //order.setRated(true);
    }

    public void removeOrder(OrderModel order) {
        orders.remove(order);
    }

    // Additional methods like updateOrderStatus can be added as needed
}