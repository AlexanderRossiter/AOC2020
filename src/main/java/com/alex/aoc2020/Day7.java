package com.alex.aoc2020;

import com.alex.aoc2020.util.Day7.BagCollection;
import com.alex.aoc2020.util.InputGetter;

import java.util.List;


public class Day7 {
    private static final InputGetter inputGetter = new InputGetter();
    private static final String PATH = "/Users/ADR/Documents/AOC2020/src/main/resources/inputs";
    private static final List<String> input = inputGetter.getInputAsList(7, PATH);

    public static void part1() {
        BagCollection bc = new BagCollection(input);
        System.out.println(bc.countCanContain("shiny gold").size());

    }

    public static void part2() {

        BagCollection bc = new BagCollection(input);
        System.out.println(bc.getNumberOfBagsInside("shiny gold"));
    }

    public static void main(String[] args) {
        part1();
        part2();
    }


}
