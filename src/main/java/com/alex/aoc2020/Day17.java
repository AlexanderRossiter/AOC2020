package com.alex.aoc2020;

import com.alex.aoc2020.util.Day17.PocketDimension3D;
import com.alex.aoc2020.util.Day17.PocketDimension4D;
import com.alex.aoc2020.util.InputGetter;

import java.util.List;

public class Day17 {
    private static final InputGetter inputGetter = new InputGetter();
    private static final String PATH = "/Users/ADR/Documents/AOC2020/src/main/resources/inputs";
    private static final List<String> input = inputGetter.getInputAsList(17, PATH);

    public static void part1() {
        PocketDimension3D pd = new PocketDimension3D(input);
        pd.runNCycles(6);
        System.out.println(pd.numberActiveCubes());
    }

    public static void part2() {
        PocketDimension4D pd = new PocketDimension4D(input);
        pd.runNCycles(6);
        System.out.println(pd.numberActiveCubes());
    }

    public static void main(String[] args) {
        part1();
        part2();
    }

}
