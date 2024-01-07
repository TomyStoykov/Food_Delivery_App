package Model;

import java.util.Date;

public class Order {
    private int order_id;
    private int user_id;
    private int restaurant_id;
    private Date orderDate;
    private String orderStatus;
    private double orderTotal;

    public Order(int order_id, int user_id, int restaurant_id, Date orderDate, String orderStatus, double orderTotal) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.restaurant_id = restaurant_id;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.orderTotal = orderTotal;
    }
    public Order(int user_id, int restaurant_id, Date orderDate, String orderStatus, double orderTotal) {
        this.user_id = user_id;
        this.restaurant_id = restaurant_id;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.orderTotal = orderTotal;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }
}