package model;

public abstract class AbstractProduct implements Product {
    private final String name;
    private final double price;
    private int quantity;

    public AbstractProduct(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public void reduceQuantity(int amount) {
        if (amount <= 0 || amount > quantity) {
            throw new IllegalArgumentException("Invalid reduction amount");
        }
        quantity -= amount;
    }
}

