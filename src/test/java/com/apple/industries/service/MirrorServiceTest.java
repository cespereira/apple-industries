package com.apple.industries.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class MirrorServiceTest {

    @Autowired
    private MirrorService service;

    @Test
    public void shouldThrowIllegalExceptionWhenInputIsNull() {
        // given
        // act
        // assert
        assertThrows(IllegalArgumentException.class, () -> this.service.reflectiveText(null));

    }

    @Test
    public void shouldThrowIllegalExceptionWhenInputIsEmpty() {
        // given
        // act
        // assert
        assertThrows(IllegalArgumentException.class, () -> this.service.reflectiveText(null));

    }

    @Test
    public void shouldReturnSameTextWhenInputContainsOnlyOneWord() {
        // given
        final var input = "apple";

        // act
        final var actual = this.service.reflectiveText(input);

        // assert
        assertEquals(input, actual);
    }

    @Test
    public void shouldReturnReversedTextWhenInputContainsTwoWords() {
        // given
        final var input = "apple industries";
        final var expected = "industries apple";

        // act
        final var actual = this.service.reflectiveText(input);

        // assert
        assertEquals(expected, actual);

    }

    @Test
    public void shouldReturnReversedTextWhenInputContainsMultipleWords() {
        // given
        final var input = "Lorem ipsum dolor sit amet consectetur adipiscing elit";
        final var expected = "elit adipiscing consectetur amet sit dolor ipsum Lorem";

        // act
        final var actual = this.service.reflectiveText(input);

        // assert
        assertEquals(expected, actual);

    }

}