package com.alex.aoc2020;

import com.alex.aoc2020.util.Day14.Chip;
import com.alex.aoc2020.util.InputGetter;

import java.util.List;


public class Day14 {
    private static final InputGetter inputGetter = new InputGetter();
    private static final String PATH = "/Users/ADR/Documents/AOC2020/src/main/resources/inputs";
    private static final List<String> input = inputGetter.getInputAsList(14, PATH);

    public static void part1() {
        Chip c = new Chip(input);
        c.decodeProgram();
        System.out.println(c.returnMemorySum());

    }

    public static void part2() {
        Chip c = new Chip(input);
        c.decodeProgram(1);
        System.out.println(c.returnMemorySum());
    }



    public static void main(String[] args) {
        part1();
        part2();
    }



}
