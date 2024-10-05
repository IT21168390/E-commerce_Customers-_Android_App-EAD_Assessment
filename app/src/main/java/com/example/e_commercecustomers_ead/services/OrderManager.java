package com.example.e_commercecustomers_ead.services;

import com.example.e_commercecustomers_ead.models.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OrderManager {
    private static OrderManager instance;
    private List<Order> orders;

    private OrderManager() {
        orders = new ArrayList<>();
        // For demonstration, let's add some dummy orders
        populateDummyOrders();
    }

    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    private void populateDummyOrders() {
        orders.add(new Order(UUID.randomUUID().toString(), 1500.00, "Pending", new Date(), "Order details for pending order."));
        orders.add(new Order(UUID.randomUUID().toString(), 2500.50, "Dispatched", new Date(), "Order details for dispatched order."));
        orders.add(new Order(UUID.randomUUID().toString(), 2500.50, "Partially Delivered", new Date(), "Order details for partially delivered order."));
        orders.add(new Order(UUID.randomUUID().toString(), 3500.75, "Delivered", new Date(), "Order details for delivered order."));
        orders.add(new Order(UUID.randomUUID().toString(), 4500.25, "Cancelled", new Date(), "Order details for cancelled order."));
    }

    public List<Order> getOrdersByStatus(String status) {
        List<Order> filteredOrders = new ArrayList<>();
        for (Order order : orders) {
            if (status.equalsIgnoreCase("Dispatched") &&
                    (order.getOrderStatus().equalsIgnoreCase("Dispatched") ||
                            order.getOrderStatus().equalsIgnoreCase("Partially Delivered"))) {
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

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void rateOrder(Order order) {
        order.setRated(true);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }

    // Additional methods like updateOrderStatus can be added as needed
}