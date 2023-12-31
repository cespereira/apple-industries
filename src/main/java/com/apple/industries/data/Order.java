package com.apple.industries.data;

import com.apple.industries.data.converters.JpaJsonConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderId;

    private UUID customerId;

    @Convert(converter = JpaJsonConverter.class)
    private List<OrderItem> items;
    private LocalDate orderDate;
    private Boolean hasWonFreePrints;

    public Order() {
    }

    public Order(UUID customerId, List<OrderItem> items) {
        this.customerId = customerId;
        this.items = items;
        this.orderDate = LocalDate.now();
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
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

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Boolean getHasWonFreePrints() {
        return hasWonFreePrints;
    }

    public void setHasWonFreePrints(Boolean hasWonFreePrints) {
        this.hasWonFreePrints = hasWonFreePrints;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", orderDate=" + orderDate +
                ", hasWonFreePrints=" + hasWonFreePrints +
                ", items=" + items +
                '}';
    }

}


