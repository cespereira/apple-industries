package com.apple.industries.service;

import com.apple.industries.data.Order;
import com.apple.industries.data.OrderItem;
import com.apple.industries.data.PackageType;
import com.apple.industries.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderServiceTest {

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Test
    public void shouldReturnZeroWhenNoSalesWasMadeOnInputMonth() {

        // given
        final var start = LocalDate.of(2023, 5, 1);
        final var end = LocalDate.of(2023, 5, 31);
        when(this.orderRepository.findAllByOrderDateBetween(any(), any())).thenReturn(
                Collections.emptyList()
        );

        // act
        final var actual = this.orderService.calculateTaxForMont(5, 2023);

        // assert
        assertNotNull(actual);
        assertEquals(BigDecimal.ZERO, actual);

    }

    @Test
    public void shouldReturnTheTaxValue() {

        // given
        final var start = LocalDate.of(2023, 1, 1);
        final var end = LocalDate.of(2023, 1, 31);
        when(this.orderRepository.findAllByOrderDateBetween(eq(start), eq(end))).thenReturn(
                List.of(
                        new Order(UUID.randomUUID(),
                                List.of(
                                        new OrderItem(PackageType.PRINTS, false),
                                        new OrderItem(PackageType.PANORAMAS, false),
                                        new OrderItem(PackageType.STRIPS, false)
                                )
                        ),
                        new Order(UUID.randomUUID(),
                                List.of(
                                        new OrderItem(PackageType.PRINTS, true),
                                        new OrderItem(PackageType.PANORAMAS, true),
                                        new OrderItem(PackageType.STRIPS, false)
                                )
                        )
                )
        );

        // act
        final var actual = this.orderService.calculateTaxForMont(1, 2023);

        // assert
        assertNotNull(actual);
        assertEquals(new BigDecimal("1.89750"), actual);
    }

}