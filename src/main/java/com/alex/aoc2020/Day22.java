package com.alex.aoc2020;

import com.alex.aoc2020.util.Day22.Combat;
import com.alex.aoc2020.util.InputGetter;

import java.util.List;

public class Day22 {
    private static final List<String> input = new InputGetter().getInputAsList(22,
            "/Users/ADR/Documents/AOC2020/src/main/resources/inputs",
            "\n\n");

    public static void part1() {
        System.out.println(new Combat(input).playCombat());
    }

    public static void part2() {
        System.out.println(new Combat(input).playCombat2());
    }

    public static void main(String[] args) {
        part1();
        part2();
    }

}
