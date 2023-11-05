public class Meal {
    private int itemId;
    private int sectionId;
    private double price;
    private String description;

    public Meal(int itemId, int sectionId, double price, String description) {
        this.itemId = itemId;
        this.sectionId = sectionId;
        this.price = price;
        this.description = description;
    }
    public Meal(int sectionId, double price, String description) {
        this.sectionId = sectionId;
        this.price = price;
        this.description = description;
    }

    public static class Builder {
        private final int sectionId;
        private double price = 0.0;
        private String description = "";

        public Builder(int sectionId) {
            this.sectionId = sectionId;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Meal build() {
            return new Meal(this);
        }
    }

    private Meal(Builder builder) {
        this.sectionId = builder.sectionId;
        this.price = builder.price;
        this.description = builder.description;
    }


    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}