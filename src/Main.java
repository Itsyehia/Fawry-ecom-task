//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import cart.ShoppingCart;
import model.Biscuits;
import model.Cheese;
import model.MobileScratchCard;
import model.TV;
import service.CheckoutService;
import user.Customer;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        // create products
        Cheese cheese = new Cheese("Cheese", 100, 5, LocalDate.now().plusDays(2), 0.4);
        Biscuits biscuits = new Biscuits("Biscuits", 150, 3, LocalDate.now().plusDays(5), 0.7);
        TV tv = new TV("TV", 2000, 2, 10);
        MobileScratchCard scratch = new MobileScratchCard("ScratchCard", 50, 10);

        // setup
        Customer customer = new Customer("Alice", 5000);
        ShoppingCart cart = new ShoppingCart();

        // add products
        cart.add(cheese, 2);
        cart.add(biscuits, 1);
        cart.add(tv, 1);
        cart.add(scratch, 2);

        // perform checkout
        CheckoutService.checkout(customer, cart);
    }
}