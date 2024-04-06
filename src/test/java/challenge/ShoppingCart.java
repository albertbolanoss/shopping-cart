package challenge;

import com.ecommerce.shoppingcart.domain.valueobjects.CustomerIdDomain;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ShoppingCart {

    // Why an ArrayList? Is there a better option?
    private final ArrayList<Item> items = new ArrayList<>();

    // synchronized? Why? What that means?
    public synchronized final void addItem(int quantity, String product, BigDecimal unitPrice) {
        if (quantity <= 0) {
            throw new QuantityLessThanZeroException("Quantity must be greater than zero");
        } else if (unitPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new QuantityLessThanZeroException("Unit Price must be greater or equals zero");
        } else {
            this.items.add(new Item(quantity, product, unitPrice));
        }
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    // Write total of the ShoppingCart using Streams and Lambda Functions.
    // Why BigDecimal?
    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Item item : items) {
            total = total.add(item.getSubtotal());
        }
        return total;
    }

    public BigDecimal getTotalWithFee(String paymentMethod, BigDecimal total) {
        return switch (paymentMethod) {
            case "Visa":
                yield total.subtract(total.sqrt(new MathContext(10))).divide(new BigDecimal(2), RoundingMode.HALF_UP);
            case "MasterCard":
                yield total.add(total.multiply(new BigDecimal("0.04"))).add(new BigDecimal(800));
            case "Cash":
                yield total.multiply(new BigDecimal("1.2"));
            default:
                throw new IllegalArgumentException("Payment method is not supported");
        };
    }

    public record Item(int quantity, String product, BigDecimal unitPrice) {
        public BigDecimal getSubtotal() {
            return unitPrice.multiply(new BigDecimal(quantity));
        }
    }

    // Checked / Unchecked
    public static class QuantityLessThanZeroException extends RuntimeException {
        public QuantityLessThanZeroException(String message) {
            super(message);
        }
    }

    @SessionScope
    public static class Cart {
        private CustomerIdDomain customerIdDomain;

        public record Item(int quantity, String product, BigDecimal unitPrice) {}

        private final ConcurrentLinkedDeque<Item> items = new ConcurrentLinkedDeque<>();

        public void addItem(Item item) {
            items.add(item);
        }

        public void removeItem(int index) {
            if (index < 0 || index >= items.size()) {
                throw new IllegalArgumentException("Invalid index for item removal");
            }

            items.stream().skip(index).findFirst().ifPresent(items::remove);
        }

        public void removeProduct(Item item) {

        }

        public ConcurrentLinkedDeque<Item> getItemsInCart() {
            return items;
        }


        public List<Item> getProducts() {
            return Collections.emptyList();
        }

    }
}
