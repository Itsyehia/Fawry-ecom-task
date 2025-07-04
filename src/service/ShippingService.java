package service;
import java.util.List;

import model.Shippable;

public class ShippingService {
    public static void ship(List<Shippable> items) {
        System.out.println("** Shipment notice **");
        double totalWeight = 0;
        for (Shippable s : items) {
            System.out.printf("%s: %.2fkg\n", ((model.Product) s).getName(), s.getWeight());
            totalWeight += s.getWeight();
        }
        System.out.printf("Total package weight %.2fkg\n", totalWeight);
    }
}
