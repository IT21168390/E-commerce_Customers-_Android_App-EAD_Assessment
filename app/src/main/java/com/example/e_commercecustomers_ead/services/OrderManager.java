package com.example.e_commercecustomers_ead.services;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.e_commercecustomers_ead.R;
import com.example.e_commercecustomers_ead.api_models.Address;
import com.example.e_commercecustomers_ead.api_models.OrderItem;
import com.example.e_commercecustomers_ead.api_models.OrderModel;
import com.example.e_commercecustomers_ead.api_models.ProductDataModel;
import com.example.e_commercecustomers_ead.api_models.VendorOrderStatus;
import com.example.e_commercecustomers_ead.fragments.OrderHistoryFragment;
import com.example.e_commercecustomers_ead.models.CartItem;
import com.example.e_commercecustomers_ead.models.Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

        new OrderManager.LoadOrdersTask().execute("");
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
                URL url = new URL("https://192.168.86.91:45456/api/Orders");//URL(urls[0]);
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


    // Callback interface for cancellation
    public interface OnCancelOrderListener {
        void onCancelSuccess(String orderId);
        void onCancelFailure(String orderId, String errorMessage);
    }

    public interface OnUpdateOrderListener {
        void onUpdateSuccess(String orderId);
        void onUpdateFailure(String orderId, String errorMessage);
    }

    public interface OnPurchaseOrderListener {
        void onPurchaseSuccess(String orderId);
        void onPurchaseFailure(String errorMessage);
    }


    // Method to cancel an order
    public void cancelOrder(String orderId, OnCancelOrderListener listener) {
        new CancelOrderTask(orderId, listener).execute();
        new OrderManager.LoadOrdersTask().execute("");
    }

    // AsyncTask to handle cancellation
    private class CancelOrderTask extends AsyncTask<Void, Void, Boolean> {
        private String orderId;
        private OnCancelOrderListener listener;
        private String errorMessage = "";

        public CancelOrderTask(String orderId, OnCancelOrderListener listener) {
            this.orderId = orderId;
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            String cancelUrl = "https://192.168.86.91:45456/api/Orders/request-cancel/" + orderId;
            try {
                URL url = new URL(cancelUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PATCH");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // If your API requires a body, you can write it here
                // For example:
                // JSONObject jsonParam = new JSONObject();
                // jsonParam.put("reason", "User requested cancellation");
                // OutputStream os = connection.getOutputStream();
                // os.write(jsonParam.toString().getBytes("UTF-8"));
                // os.close();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK ||
                        responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                    return true;
                } else {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    errorMessage = "Error: " + response.toString();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                errorMessage = "Exception: " + e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                listener.onCancelSuccess(orderId);
            } else {
                listener.onCancelFailure(orderId, errorMessage);
            }
        }
    }




    // Method to update an order's shipping address
    public void updateOrderAddress(String orderId, Address newAddress, OnUpdateOrderListener listener) {
        new UpdateOrderTask(orderId, newAddress, listener).execute();
    }

    // AsyncTask to handle address updates
    private class UpdateOrderTask extends AsyncTask<Void, Void, Boolean> {
        private String orderId;
        private Address newAddress;
        private OnUpdateOrderListener listener;
        private String errorMessage = "";

        public UpdateOrderTask(String orderId, Address newAddress, OnUpdateOrderListener listener) {
            this.orderId = orderId;
            this.newAddress = newAddress;
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            String updateUrl = "https://192.168.86.91:45456/api/Orders/" + orderId;
            try {
                URL url = new URL(updateUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Create JSON body
                JSONObject shippingAddressJson = new JSONObject();
                shippingAddressJson.put("street", newAddress.getStreet());
                shippingAddressJson.put("city", newAddress.getCity());
                shippingAddressJson.put("zipCode", newAddress.getZipCode());

                JSONObject requestBody = new JSONObject();
                requestBody.put("shippingAddress", shippingAddressJson);

                // Write JSON to request body
                OutputStream os = connection.getOutputStream();
                os.write(requestBody.toString().getBytes("UTF-8"));
                os.flush();
                os.close();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK ||
                        responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                    return true;
                } else {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    errorMessage = "Error: " + response.toString();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                errorMessage = "Exception: " + e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                listener.onUpdateSuccess(orderId);
            } else {
                listener.onUpdateFailure(orderId, errorMessage);
            }
        }
    }










    // Method to place an order
    public void placeOrder(String customerId, List<CartItem> cartItems, Address shippingAddress, OnPurchaseOrderListener listener) {
        new PlaceOrderTask(customerId, cartItems, shippingAddress, listener).execute();
    }

    // AsyncTask to handle order placement
    private class PlaceOrderTask extends AsyncTask<Void, Void, Boolean> {
        private String customerId;
        private List<CartItem> cartItems;
        private Address shippingAddress;
        private OnPurchaseOrderListener listener;
        private String errorMessage = "";
        private String orderId = "";

        public PlaceOrderTask(String customerId, List<CartItem> cartItems, Address shippingAddress, OnPurchaseOrderListener listener) {
            this.customerId = customerId;
            this.cartItems = cartItems;
            this.shippingAddress = shippingAddress;
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            String placeOrderUrl = "https://192.168.86.91:45456/api/Orders";
            try {
                URL url = new URL(placeOrderUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST"); // POST method
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Construct JSON body
                JSONObject orderJson = new JSONObject();
                orderJson.put("customerId", customerId);

                // Order Items
                JSONArray orderItemsArray = new JSONArray();
                for (CartItem item : cartItems) {
                    JSONObject itemJson = new JSONObject();
                    itemJson.put("productId", item.getProduct());
                    itemJson.put("quantity", item.getQuantity());
                    orderItemsArray.put(itemJson);
                }
                orderJson.put("orderItems", orderItemsArray);

                // Shipping Address
                JSONObject addressJson = new JSONObject();
                addressJson.put("street", shippingAddress.getStreet());
                addressJson.put("city", shippingAddress.getCity());
                addressJson.put("zipCode", shippingAddress.getZipCode());
                orderJson.put("shippingAddress", addressJson);

                // Write JSON to request body
                OutputStream os = connection.getOutputStream();
                os.write(orderJson.toString().getBytes("UTF-8"));
                os.close();

                // Get response
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_OK) {
                    // Read response
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // Parse response to get orderId if needed
                    JSONObject responseJson = new JSONObject(response.toString());
                    orderId = responseJson.optString("id", ""); // Adjust based on your API's response
                    return true;
                } else {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    errorMessage = "Error: " + response.toString();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                errorMessage = "Exception: " + e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                listener.onPurchaseSuccess(orderId);
            } else {
                listener.onPurchaseFailure(errorMessage);
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