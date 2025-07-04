package service;
import cart.CartItem;
import cart.ShoppingCart;
import model.Shippable;
import user.Customer;

import java.util.ArrayList;
import java.util.List;

public class CheckoutService {

    // a dummy default value
    private static final double SHIPPING_RATE_PER_KG = 20;
    public static void checkout(Customer customer, ShoppingCart cart) {
        if (cart.isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        double subtotal = 0;
        List<Shippable> toShip = new ArrayList<>();

        for (CartItem item : cart.getItems()) {
            subtotal += item.getTotalPrice();
            item.getProduct().reduceQuantity(item.getQuantity());
            if (item.getProduct() instanceof Shippable) {
                for (int i = 0; i < item.getQuantity(); i++) {
                    toShip.add((Shippable) item.getProduct());
                }
            }
        }

        double shipping = toShip.stream().mapToDouble(Shippable::getWeight).sum() * SHIPPING_RATE_PER_KG;
        double amount = subtotal + shipping;

        if (customer.getBalance() < amount) {
            throw new IllegalStateException("Insufficient balance");
        }

        ShippingService.ship(toShip);

        System.out.println("** Checkout receipt **");
        for (CartItem item : cart.getItems()) {
            System.out.printf("%dx %s %.2f\n", item.getQuantity(), item.getProduct().getName(), item.getTotalPrice());
        }
        System.out.println("----------------------");
        System.out.printf("Subtotal %.2f\n", subtotal);
        System.out.printf("Shipping %.2f\n", shipping);
        System.out.printf("Amount %.2f\n", amount);

        customer.deduct(amount);
        System.out.printf("%s's remaining balance: %.2f\n", customer.getName(), customer.getBalance());
        cart.clear();
    }
}