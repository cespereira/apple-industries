package com.apple.industries.service;

import com.apple.industries.data.Order;
import com.apple.industries.data.OrderItem;
import com.apple.industries.data.PackageType;
import com.apple.industries.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final BigDecimal SALES_TAX = new BigDecimal("0.08625");

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public BigDecimal calculateTaxForMont(final int month) {
        final var allOrderByMonth = this.orderRepository.findAllByOrderDate_Month(month);
        if (allOrderByMonth.size() == 0)
            return BigDecimal.ZERO;
        final var allOrdersByPackageType = allOrderByMonth.stream()
                .map(Order::getItems)
                .flatMap(List::stream)
                .filter(orderItem -> !orderItem.isGift())
                .collect(Collectors.groupingBy(
                        OrderItem::getPackageType,
                        Collectors.counting()
                ));

        final var sumSales = (allOrdersByPackageType.getOrDefault(PackageType.PRINTS, 0L) * 5)
                + (allOrdersByPackageType.getOrDefault(PackageType.PANORAMAS, 0L) * 7)
                + (allOrdersByPackageType.getOrDefault(PackageType.STRIPS, 0L) * 5);

        return new BigDecimal(sumSales).multiply(SALES_TAX);

    }
}
