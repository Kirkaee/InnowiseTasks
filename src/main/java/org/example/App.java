package org.example;

import org.example.service.ArrayHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class App
{
    public static void main( String[] args ) {
        ArrayHandler arrayHandler = new ArrayHandler();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String numbersStr = null;
        try {
            numbersStr = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Integer> list = Arrays.stream(numbersStr.split(" "))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        System.out.println("Even:" + arrayHandler.getEven(list));
        System.out.println("Odd:" + arrayHandler.getOdd(list));
        System.out.println("Divisible by three or nine:" + arrayHandler.getDivisibleBy(list, 3));
        System.out.println("Divisible by five or ten:" + arrayHandler.getDivisibleBy(list, 5));
        System.out.println("GCD:" + arrayHandler.getGreatestCommonDivisor(list));
        System.out.println("LCM:" + arrayHandler.getLeastCommonMultiple(list));
        System.out.println("Simple number:" + arrayHandler.getSimpleNumbers(list));
        System.out.println("Lucky number:" + arrayHandler.getHappyNumbers(list));
        System.out.println("Fibonacci number:" + arrayHandler.getFibonacciNumbers(list));
        System.out.println("Palindromes:" + arrayHandler.getPalindromes(list));

    }
}

