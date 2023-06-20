package com.apple.industries.data;

import java.util.List;
import java.util.UUID;

public class Order {

    private UUID customerId;
    private List<OrderItem> items;
    private Boolean hasWonFreePrints;

    public Order(UUID customerId, List<OrderItem> items) {
        this.customerId = customerId;
        this.items = items;
    }

    /**
     * Check if the order is eligible.
     * <p>
     * The order is eligible when:
     * * have one item in the order
     * * not passed throw raffle (hasWonFreePrints == null)
     *
     * @return @code(boolean)
     */
    public boolean isEligible() {
        return this.items != null
                && this.items.size() == 1
                && this.hasWonFreePrints == null;
    }


    public Boolean isHasWonFreePrints() {
        return hasWonFreePrints;
    }

    public void setHasWonFreePrints(boolean hasWonFreePrints) {
        this.hasWonFreePrints = hasWonFreePrints;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public UUID getCustomerId() {
        return customerId;
    }
}
