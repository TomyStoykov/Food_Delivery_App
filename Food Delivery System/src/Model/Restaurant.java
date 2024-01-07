package Model;

public class Restaurant {
    private int restaurant_id;
    private String restaurantName;
    private String address;
    private String contactNumber;

    public Restaurant(int restaurant_id, String restaurantName, String address, String contactNumber) {
        this.restaurant_id = restaurant_id;
        this.restaurantName = restaurantName;
        this.address = address;
        this.contactNumber = contactNumber;
    }
    public Restaurant(String restaurantName, String address, String contactNumber) {
        this.restaurantName = restaurantName;
        this.address = address;
        this.contactNumber = contactNumber;
    }


    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
