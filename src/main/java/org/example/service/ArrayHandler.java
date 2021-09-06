package org.example.service;

import org.apache.commons.lang3.mutable.MutableInt;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ArrayHandler {

    public List<Integer> getEven(List<Integer> list) {
        return list.stream()
                .filter(i -> (i & 1) != 1)
                .collect(Collectors.toList());
    }

    public List<Integer> getOdd(List<Integer> list) {
        return list.stream()
                .filter(i -> (i & 1) == 1)
                .collect(Collectors.toList());
    }

    public List<Integer> getDivisibleBy(List<Integer> list, int divider) {
        return list.stream()
                .filter(i -> i % divider == 0)
                .collect(Collectors.toList());
    }

    public Integer getGreatestCommonDivisor(List<Integer> list) {
        Optional<Integer> optionalInteger = list.stream()
                .reduce((accumulator, element) -> accumulator = findGreatestCommonDivisor(accumulator, element));
        return optionalInteger.orElseThrow();
    }

    private Integer findGreatestCommonDivisor(int firstNumber, int secondNumber) {
        return Stream.iterate(new int[]{firstNumber, secondNumber}, vals -> new int[]{vals[1], vals[0] % vals[1]})
                .filter(v -> v[1] == 0).findFirst().orElseThrow()[0];
    }

    public MutableInt getLeastCommonMultiple(List<Integer> list) {
        MutableInt leastCommonMultiple = new MutableInt(1);
        list.forEach((n) -> leastCommonMultiple.setValue(
                leastCommonMultiple.getValue() * n / findGreatestCommonDivisor(leastCommonMultiple.getValue(), n)));
        return leastCommonMultiple;
    }

    public List<Integer> getSimpleNumbers(List<Integer> list) {
        return list.stream()
                .filter(this::isSimpleNumber)
                .collect(Collectors.toList());
    }

    private boolean isSimpleNumber(Integer number) {
        return number != 1 && isSimple(number);
    }

    private boolean isSimple(Integer number) {
        return IntStream.iterate(2, j -> j <= number / 2, j -> j + 1)
                .filter(j -> number % j == 0)
                .findFirst()
                .isEmpty();
    }

    public List<Integer> getHappyNumbers(List<Integer> list) {
        return list.stream().filter(this::isHappyNumber).collect(Collectors.toList());
    }

    private boolean isHappyNumber(Integer number) {
        return 1 == IntStream.iterate(number, this::getSumOfSquaresOfDigits)
                .filter(n -> n == 1 || n == 4)
                .findFirst()
                .orElseThrow();
    }

    private Integer getSumOfSquaresOfDigits(Integer number) {
        int remainder = 0, sum = 0;
        while (number > 0) {
            remainder = number % 10;
            sum = sum + (remainder * remainder);
            number = number / 10;
        }
        return sum;
    }

    public List<Integer> getFibonacciNumbers(List<Integer> list) {
        return list.stream()
                .map(this::getFibonacciNumber)
                .collect(Collectors.toList());
    }

    private Integer getFibonacciNumber(Integer n) {
        List<Integer> list = new ArrayList<>(Arrays.asList(0, 1));
        IntStream.iterate(2, j -> j <= n, j -> j + 1)
                .forEach(j -> list.add(j, list.get(j - 1) + list.get(j - 2)));
        return list.stream().max(Integer::compareTo).orElseThrow();
    }

    public List<Integer> getPalindromes(List<Integer> list) {
        return list.stream().filter(this::isPalindrome)
                .collect(Collectors.toList());
    }

    private boolean isPalindrome(Integer number) {
        String numberString = String.valueOf(number);
        return numberString.equals(new StringBuilder(numberString).reverse().toString());
    }

}