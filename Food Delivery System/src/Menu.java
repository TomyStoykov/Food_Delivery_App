public class Menu {
    private int menu_id;
    private int restaurant_id;
    private String menuName;

    public Menu(int menu_id, int restaurant_id, String menuName) {
        this.menu_id = menu_id;
        this.restaurant_id = restaurant_id;
        this.menuName = menuName;
    }
    public Menu(int restaurant_id, String menuName) {
        this.restaurant_id = restaurant_id;
        this.menuName = menuName;
    }

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
}
