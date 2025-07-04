import cart.ShoppingCart;
import model.Biscuits;
import model.Cheese;
import model.MobileScratchCard;
import model.TV;
import service.CheckoutService;
import user.Customer;
import java.time.LocalDate;

public class TestCornerCases {
    public static void main(String[] args) {

        // Prepare products
        Cheese freshCheese = new Cheese("FreshCheese", 100, 2, LocalDate.now().plusDays(1), 0.5);
        Cheese expiredCheese = new Cheese("OldCheese", 100, 2, LocalDate.now().minusDays(1), 0.5);
        Biscuits biscuits = new Biscuits("Biscuits", 50, 0, LocalDate.now().plusDays(5), 0.3);
        TV tv = new TV("TV", 2000, 1, 10);
        MobileScratchCard scratch = new MobileScratchCard("ScratchCard", 50, 1);

        Customer richCustomer = new Customer("Rich", 10000);
        Customer poorCustomer = new Customer("Poor", 10);

        System.out.println("=== 1. Empty cart checkout ===");
        ShoppingCart cart1 = new ShoppingCart();
        try {
            CheckoutService.checkout(richCustomer, cart1);
        } catch (Exception e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        System.out.println("\n=== 2. Add zero quantity ===");
        ShoppingCart cart2 = new ShoppingCart();
        try {
            cart2.add(freshCheese, 0);
        } catch (Exception e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        System.out.println("\n=== 3. Add negative quantity ===");
        try {
            cart2.add(freshCheese, -1);
        } catch (Exception e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        System.out.println("\n=== 4. Add more than stock ===");
        try {
            cart2.add(freshCheese, 5);
        } catch (Exception e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        System.out.println("\n=== 5. Add expired product ===");
        try {
            cart2.add(expiredCheese, 1);
        } catch (Exception e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        System.out.println("\n=== 6. Add out-of-stock product ===");
        try {
            cart2.add(biscuits, 1);
        } catch (Exception e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        System.out.println("\n=== 7. Insufficient balance at checkout ===");
        ShoppingCart cart3 = new ShoppingCart();
        cart3.add(freshCheese, 1);
        cart3.add(tv, 1);
        try {
            CheckoutService.checkout(poorCustomer, cart3);
        } catch (Exception e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        System.out.println("\n=== 8. Successful checkout ===");
        ShoppingCart cart4 = new ShoppingCart();
        cart4.add(freshCheese, 1);
        cart4.add(scratch, 1);
        System.out.println("Before checkout, balance = " + richCustomer.getBalance());
        CheckoutService.checkout(richCustomer, cart4);
        System.out.println("After checkout, balance = " + richCustomer.getBalance());
    }
}
