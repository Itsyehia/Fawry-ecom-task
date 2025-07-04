package cart;
import model.Expirable;
import model.Product;

import java.util.ArrayList;
import java.util.List;


public class ShoppingCart {
    private final List<CartItem> items = new ArrayList<>();

    public void add(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (quantity > product.getQuantity()) {
            throw new IllegalArgumentException("Not enough stock for " + product.getName());
        }
        if (product instanceof Expirable && ((Expirable) product).isExpired()) {
            throw new IllegalArgumentException(product.getName() + " is expired");
        }
        items.add(new CartItem(product, quantity));
    }

    public List<CartItem> getItems() {
        return List.copyOf(items);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }
}
