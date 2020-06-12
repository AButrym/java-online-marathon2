class Product {
    private static int objectsCreated = 0;

    private String name;
    private double price;

    public Product() {
        objectsCreated++;
    }

    public Product(String name, double price) {
        this();
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static int count() {
        return objectsCreated;
    }
}
