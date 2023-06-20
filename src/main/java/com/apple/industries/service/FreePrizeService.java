package com.apple.industries.service;

import com.apple.industries.data.Order;
import com.apple.industries.data.OrderItem;
import com.apple.industries.data.PackageType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.apple.industries.utils.AppleIndustriesUtils.randomBoolean;

@Service
public class FreePrizeService {

    private LocalDateTime lastWinningTime;

    public FreePrizeService() {
        this.lastWinningTime = LocalDateTime.MIN;
    }

    public Order raffle(final Order order) {
        if (order.isEligible()) {
            final var random = randomBoolean();
            final var noWinnerInPastHour = noWinnerInPastHour();
            final var winner = random && noWinnerInPastHour;
            order.setHasWonFreePrints(winner);
            if (winner) {
                this.lastWinningTime = LocalDateTime.now();
                final var orderItemsWithPrizes = buildOrderItemsWithPrizes(order.getItems().get(0));
                order.setItems(orderItemsWithPrizes);
            }
        }
        return order;
    }

    private List<OrderItem> buildOrderItemsWithPrizes(OrderItem item) {
        return switch (item.getPackageType()) {
            case PRINTS -> List.of(
                    new OrderItem(PackageType.PRINTS, false),
                    new OrderItem(PackageType.PANORAMAS, true),
                    new OrderItem(PackageType.STRIPS, true)
            );
            case STRIPS -> List.of(
                    new OrderItem(PackageType.PRINTS, true),
                    new OrderItem(PackageType.PANORAMAS, true),
                    new OrderItem(PackageType.STRIPS, false)
            );
            case PANORAMAS -> List.of(
                    new OrderItem(PackageType.PRINTS, true),
                    new OrderItem(PackageType.PANORAMAS, false),
                    new OrderItem(PackageType.STRIPS, true)
            );
        };
    }

    private boolean noWinnerInPastHour() {
        return LocalDateTime.now().minusHours(1)
                .isAfter(this.lastWinningTime);
    }

}
