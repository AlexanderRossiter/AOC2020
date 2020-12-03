package com.alex.aoc2020;

import com.alex.aoc2020.util.Day3.TobogganTreeMap;
import com.alex.aoc2020.util.InputGetter;


public class Day3 {
    private static final InputGetter inputGetter = new InputGetter();
    private static final String PATH = "/Users/ADR/Documents/AOC2020/src/main/resources/inputs";
    private static final TobogganTreeMap map = new TobogganTreeMap(inputGetter.getInputAsList(3, PATH));


    public static void part1() {
        System.out.println(String.format("Part1: %d trees\n", map.checkSlope(3, 1)));
    }

    public static void part2() {
        int totalCountProduct = map.checkSlope(1, 1) *
                map.checkSlope(3, 1) *
                map.checkSlope(5, 1) *
                map.checkSlope(7, 1) *
                map.checkSlope(1, 2);

        System.out.println(String.format("Part1: %d trees\n", totalCountProduct));

    }

    public static void main(String[] args) {
        part1();
        part2();
    }

}


