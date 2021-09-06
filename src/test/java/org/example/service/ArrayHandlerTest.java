package org.example.service;

import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArrayHandlerTest {

    private final ArrayHandler arrayHandler = new ArrayHandler();

    @Test
    void shouldReturnEvenNumber() {
        final List<Integer> input = List.of(
                1,
                2,
                3,
                4,
                5,
                6,
                7,
                8,
                9,
                10
        );
        final List<Integer> expected = List.of(
                2,
                4,
                6,
                8,
                10
        );

        assertEquals(expected, arrayHandler.getEven(input));
    }

    @Test
    void shouldReturnOddNumber() {
        final List<Integer> input = List.of(
                1,
                2,
                3,
                4,
                5,
                6,
                7,
                8,
                9,
                10
        );

        final List<Integer> expected = List.of(
                1,
                3,
                5,
                7,
                9
        );

        assertEquals(expected, arrayHandler.getOdd(input));

    }

    @Test
    void shouldReturnDivisibleBy() {
        final List<Integer> input = List.of(
                1,
                2,
                3,
                4,
                5,
                6,
                7,
                8,
                9,
                10
        );

        final List<Integer> expected = List.of(
                5,
                10
        );

        assertEquals(expected, arrayHandler.getDivisibleBy(input, 5));
    }

    @Test
    void shouldReturnGreatestCommonDivisor() {
        final List<Integer> input = List.of(
                35,
                20,
                10
        );
        final Integer expected = 5;

        assertEquals(expected, arrayHandler.getGreatestCommonDivisor(input));
    }

    @Test
    void shouldReturnLeastCommonMultiple() {
        final List<Integer> input = List.of(
                35,
                20,
                10
        );
        final MutableInt expected = new MutableInt(140);

        assertEquals(expected, arrayHandler.getLeastCommonMultiple(input));
    }

    @Test
    void shouldReturnSimpleNumbers() {
        final List<Integer> input = List.of(
                1,
                2,
                3,
                4,
                5,
                6,
                7,
                8,
                9,
                10
        );

        final List<Integer> expected = List.of(
                2,
                3,
                5,
                7
        );

        assertEquals(expected, arrayHandler.getSimpleNumbers(input));
    }

    @Test
    void shouldReturnHappyNumbers() {
        final List<Integer> input = List.of(
                7,
                28,
                100,
                320,
                4,
                20,
                37,
                58
        );
        final List<Integer> expected = List.of(
                7,
                28,
                100,
                320
        );

        assertEquals(expected, arrayHandler.getHappyNumbers(input));
    }

    @Test
    void shouldReturnFibonacciNumbers() {
        final List<Integer> input = List.of(
                1,
                2,
                3,
                4,
                5,
                6,
                7,
                8,
                9,
                10
        );

        final List<Integer> expected = List.of(
                1,
                1,
                2,
                3,
                5,
                8,
                13,
                21,
                34,
                55
        );

        assertEquals(expected, arrayHandler.getFibonacciNumbers(input));
    }

    @Test
    void shouldReturnPalindromes() {
        final List<Integer> input = List.of(
                1,
                2,
                100,
                101,
                134,
                252
        );

        final List<Integer> expected = List.of(
                1,
                2,
                101,
                252
        );

        assertEquals(expected, arrayHandler.getPalindromes(input));
    }

}