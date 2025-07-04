# Fawry Rise Journey

## Overview

This Java-based solution implements a minimal, object-oriented e-commerce system for the Fawry Rise Full Stack Development Internship Challenge. Key features include:

* **Product Definition**: Base `Product` interface with implementations for expirable and/or shippable items.
* **Cart Management**: A `ShoppingCart` class enforces stock, expiration, and quantity constraints.
* **Checkout Process**: `CheckoutService` computes subtotals, shipping fees, total amount, updates stock, validates customer balance, and prints a detailed receipt.
* **Shipping**: `ShippingService` handles all shippable items through a simple `Shippable` interface.
* **Corner-Case Handling**: Validates empty cart, insufficient balance, out-of-stock, expired products, zero/negative quantities.

## Directory Structure

```
src/
└── com/fawryrise/
    ├── Main.java                # Demo entry point
    ├── model/                   # Domain objects
    │   ├── Product.java         # Core interface
    │   ├── Expirable.java       # Expiry behavior
    │   ├── Shippable.java       # Shipping behavior
    │   ├── AbstractProduct.java # Base implementation
    │   ├── Cheese.java          # Expirable + shippable
    │   ├── Biscuits.java        # Expirable + shippable
    │   ├── TV.java              # Shippable
    │   └── MobileScratchCard.java # Standard product
    ├── cart/                    # Cart-related classes
    │   ├── CartItem.java        # Holds product + requested qty
    │   └── ShoppingCart.java    # Manages additions and validations
    ├── user/                    # User domain
    │   └── Customer.java        # Holds name + balance
    └── service/                 # Business services
        ├── ShippingService.java # Prints shipment notice
        └── CheckoutService.java # Orchestrates checkout logic
```

## Object‑Oriented Design

### Key Interfaces and Classes

* **`Product` (interface)** defines `getName()`, `getPrice()`, `getQuantity()`, and `reduceQuantity(int)`.
* **`Expirable` (interface)** adds `isExpired()` and `getExpiryDate()` for products with lifetimes.
* **`Shippable` (interface)** defines `getWeight()` for any item requiring delivery.
* **`AbstractProduct` (abstract)** implements common fields (`name`, `price`, `quantity`) and methods, reducing duplication.
* **Concrete products**:

  * `Cheese`, `Biscuits` extend `AbstractProduct`, implement both `Expirable` and `Shippable`.
  * `TV` extends `AbstractProduct`, implements `Shippable` only.
  * `MobileScratchCard` extends `AbstractProduct` only.
* **`CartItem`** holds a `Product` reference and a requested quantity.
* **`ShoppingCart`** enforces business rules on add: positive quantity, stock availability, and non-expired state.
* **`Customer`** tracks `name` and `balance`, with `deduct(double)` enforcing non-negative balance.

## Business Logic

1. **Add to Cart** (`cart.add(product, quantity)`):

   * Reject zero or negative quantities.
   * Reject quantities exceeding stock.
   * Reject expired items for expirable products.
   * On success, wraps into `CartItem` and stores in a list.

2. **Checkout** (`CheckoutService.checkout(customer, cart)`):

   1. **Validate**: cart is non-empty, customer has sufficient funds.
   2. **Compute subtotal**: sum of each `item.getTotalPrice()`.
   3. **Gather shippables**: flatten each shippable product per unit into a list.
   4. **Compute shipping fee**: `sum(weights) * SHIPPING_RATE_PER_KG`.
   5. **Print shipment notice**: list each item name and weight, plus total weight.
   6. **Print receipt**: list each line (`quantity x name + line price`), subtotal, shipping, total.
   7. **Deduct** total from the customer’s balance.
   8. **Reduce stock**: call `reduceQuantity` on each product.
   9. **Clear** the cart.

## Design Patterns Used

* **Factory Method (implicit)**: Constructors for concrete `Product` types encapsulate creation logic, though a dedicated factory could be added for extensibility.
* **Strategy**: `Expirable` and `Shippable` interfaces let each product class choose relevant behaviors without branching logic in services.
* **Template Method (via `AbstractProduct`)**: centralizes common field handling and validation.
* **Single Responsibility Principle**: each class has one clear responsibility (e.g., `ShoppingCart` only manages items; `CheckoutService` only orchestrates checkout).

## Assumptions

* All dates use `LocalDate.now()` as a reference.
* Shipping rate is a constant (20 per kg), but could be externalized.
* No persistent storage: stock and balances reset on each run.

## Running the Demo

```bash
# Compile
javac -d out src/com/fawryrise/**/*.java
# Run
java -cp out com.fawryrise.Main
```

## Tests (JUnit 5)

* Located under `src/test/java/com/fawryrise/...`
* Cover: empty cart, invalid quantities, expiration, stock depletion, insufficient balance, and successful checkout.

---

*End of README*

