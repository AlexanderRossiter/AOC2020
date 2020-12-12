package com.alex.aoc2020;

import com.alex.aoc2020.util.Day12.Ship;
import com.alex.aoc2020.util.InputGetter;

import java.util.List;

public class Day12 {
    private static final InputGetter inputGetter = new InputGetter();
    private static final String PATH = "/Users/ADR/Documents/AOC2020/src/main/resources/inputs";
    private static final List<String> input = inputGetter.getInputAsList(12, PATH);


    public static void part1() {
        Ship ship = new Ship(input);
        ship.steer();
        System.out.println(Math.abs(ship.getX()) + Math.abs(ship.getY()));

    }

    public static void part2() {
        Ship ship = new Ship(input);
        ship.waypointSteer();
        System.out.println(Math.abs(ship.getX()) + Math.abs(ship.getY()));

    }

    public static void main(String[] args) {
        part1();
        part2();
    }
}
