package com.alex.aoc2020;

import com.alex.aoc2020.util.Day11.SeatingPlan;
import com.alex.aoc2020.util.InputGetter;

import java.util.List;

public class Day11 {
    private static final InputGetter inputGetter = new InputGetter();
    private static final String PATH = "/Users/ADR/Documents/AOC2020/src/main/resources/inputs";
    private static final List<String> input = inputGetter.getInputAsList(11, PATH);


    public static void part1() {
        SeatingPlan sp = new SeatingPlan(input);
        sp.runSeatingSimulation(1);
        System.out.println(sp.countNumberOccupied());
    }

    public static void part2() {
        SeatingPlan sp = new SeatingPlan(input);
        sp.runSeatingSimulation(2);
        System.out.println(sp.countNumberOccupied());
    }

    public static void main(String[] args) {
        part1();
        part2();
    }
}
