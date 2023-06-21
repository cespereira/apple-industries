package com.apple.industries.service;

import com.apple.industries.data.Order;
import com.apple.industries.data.OrderItem;
import com.apple.industries.data.PackageType;
import com.apple.industries.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class OrderServiceTest {

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Test
    public void shouldReturnZeroWhenNoSalesWasMadeOnInputMonth() {

        // given
        Mockito.when(this.orderRepository.findAllByOrderDate_Month(Mockito.eq(6))).thenReturn(
                Collections.emptyList()
        );

        // act
        final var actual = this.orderService.calculateTaxForMont(6);

        // assert
        assertNotNull(actual);
        assertEquals(BigDecimal.ZERO, actual);

    }

    @Test
    public void shouldReturnTheTaxValue() {

        // given
        Mockito.when(this.orderRepository.findAllByOrderDate_Month(Mockito.eq(6))).thenReturn(
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
        final var actual = this.orderService.calculateTaxForMont(6);

        // assert
        assertNotNull(actual);
        assertEquals(new BigDecimal("1.89750"), actual);
    }

}