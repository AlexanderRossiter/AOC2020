package com.alex.aoc2020;

import com.alex.aoc2020.util.Day8.Computer;
import com.alex.aoc2020.util.Day8.Program;
import com.alex.aoc2020.util.InputGetter;

import java.util.List;

public class Day8 {
    private static final InputGetter inputGetter = new InputGetter();
    private static final String PATH = "/Users/ADR/Documents/AOC2020/src/main/resources/inputs";
    private static final List<String> input = inputGetter.getInputAsList(8, PATH);

    public static void part1() {
        Program program = new Program(input);
        Computer computer = new Computer();
        computer.runInstructions(program);
    }


    public static void part2() {
        Program program = new Program(input);
        Computer computer = new Computer();
        System.out.println(computer.findAndSwitchCorruptedInstruction(program));
    }


    public static void main(String[] args) {
        part1();
        part2();
    }
}
