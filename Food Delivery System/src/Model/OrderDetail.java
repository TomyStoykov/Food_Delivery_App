package Model;

public class OrderDetail {
    private int orderDetails_id;
    private int order_id;
    private int item_id;
    private int quantity;
    private double price;

    public OrderDetail(int orderDetails_id, int order_id, int item_id, int quantity, double price) {
        this.orderDetails_id = orderDetails_id;
        this.order_id = order_id;
        this.item_id = item_id;
        this.quantity = quantity;
        this.price = price;
    }
    public OrderDetail(int order_id, int item_id, int quantity, double price) {
        this.order_id = order_id;
        this.item_id = item_id;
        this.quantity = quantity;
        this.price = price;
    }

    public int getOrderDetails_id() {
        return orderDetails_id;
    }

    public void setOrderDetails_id(int orderDetails_id) {
        this.orderDetails_id = orderDetails_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}