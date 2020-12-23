package com.alex.aoc2020;

import com.alex.aoc2020.util.Day18.*;
import com.alex.aoc2020.util.InputGetter;

import java.util.List;

public class Day18 {
    private static final InputGetter inputGetter = new InputGetter();
    private static final String PATH = "/Users/ADR/Documents/AOC2020/src/main/resources/inputs";
    private static final List<String> input = inputGetter.getInputAsList(18, PATH);

    public static void part1() {
        System.out.println(input.stream()
                .map(Expression::new)
                .map(Expression::eval)
                .reduce((long) 0, Long::sum));
    }

    public static void part2() {
        System.out.println(input.stream()
                .map(s -> new Expression(s, 1))
                .map(Expression::eval)
                .reduce((long) 0, Long::sum));
    }

    public static void main(String[] args) {
        part1();
        part2();
    }

}
