package com.apple.industries.service;

import com.apple.industries.data.Order;
import com.apple.industries.data.OrderItem;
import com.apple.industries.data.PackageType;
import com.apple.industries.utils.AppleIndustriesUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class FreePrizeServiceTest {

    @Autowired
    private FreePrizeService service;

    @BeforeAll
    public static void init() {
        Mockito.mockStatic(AppleIndustriesUtils.class);
    }

    @Test
    public void shouldNotRaffleWithAnEligibleOrder() {
        // given
        final var orderAlreadyParticipated = new Order(
                UUID.randomUUID(),
                List.of(new OrderItem(PackageType.PANORAMAS, false))
        );
        orderAlreadyParticipated.setHasWonFreePrints(false);

        final var orderWithMoreThanOneItem = new Order(
                UUID.randomUUID(),
                List.of(new OrderItem(PackageType.PANORAMAS, false),
                        new OrderItem(PackageType.PANORAMAS, false))
        );

        // act
        final var actualOrderAlreadyParticipated = this.service.raffle(orderAlreadyParticipated);
        final var actualOrderWithMoreThanOneItem = this.service.raffle(orderWithMoreThanOneItem);

        // assert
        assertNotNull(actualOrderAlreadyParticipated);
        assertFalse(actualOrderAlreadyParticipated.isEligible());
        assertFalse(actualOrderAlreadyParticipated.getHasWonFreePrints());

        assertNotNull(actualOrderWithMoreThanOneItem);
        assertFalse(actualOrderWithMoreThanOneItem.isEligible());

    }

    @Test
    public void shouldRaffleWithAnEligibleOrder() {
        // given
        final var order = new Order(
                UUID.randomUUID(),
                List.of(new OrderItem(PackageType.PANORAMAS, false))
        );

        when(AppleIndustriesUtils.randomBoolean()).thenReturn(false);


        // act
        final var actual = this.service.raffle(order);

        // assert
        assertNotNull(actual);
        assertFalse(actual.getHasWonFreePrints());
    }


    @Test
    @org.junit.jupiter.api.Order(3)
    public void shouldNotGiveFreePrizeWhenWasGivenInThePreviousHour() throws InterruptedException {
        //given
        when(AppleIndustriesUtils.randomBoolean()).thenReturn(true);

        //act
        final var winner = this.service.raffle(new Order(UUID.fromString("2cb9cd8f-6a52-437b-851b-2bdd30656756"),
                List.of(new OrderItem(PackageType.STRIPS, false)))
        );

        // forcing to be not executed at same time
        Thread.sleep(10);

        final var loser = this.service.raffle(new Order(UUID.fromString("554390f0-a446-43de-8f1e-bb9dc9b7e888"),
                List.of(new OrderItem(PackageType.PRINTS, false)))
        );

        //assert
        assertNotNull(winner);
        assertTrue(winner.getHasWonFreePrints());
        assertNotNull(loser);
        assertFalse(loser.getHasWonFreePrints());

    }

}