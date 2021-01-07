package com.alex.aoc2020;

import com.alex.aoc2020.util.Day23.Game;
import com.alex.aoc2020.util.InputGetter;

public class Day23 {
    private static final String input = new InputGetter().getInputAsList(23,
            "/Users/ADR/Documents/AOC2020/src/main/resources/inputs",
            "\n").get(0);

    public static void part1() {
        Game g = new Game(input);
        System.out.println(g.playCups(100));
    }

    public static void part2() {
        Game g = new Game(input);
        System.out.println(g.playCups2(10000000));
    }

    public static void main(String[] args) {
        part1();
        part2();
    }

}
