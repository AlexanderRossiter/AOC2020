package com.alex.aoc2020;

import com.alex.aoc2020.util.Day6.SeatingGroup;
import com.alex.aoc2020.util.InputGetter;

import java.util.List;
import java.util.stream.Collectors;

public class Day6 {
    private static final InputGetter inputGetter = new InputGetter();
    private static final String PATH = "/Users/ADR/Documents/AOC2020/src/main/resources/inputs";
    private static final  List<String> input =  inputGetter.getInputAsList(6, PATH, "\n\n").stream()
            .map(s -> s.replace("\n", " "))
            .collect(Collectors.toList());

    public static void part1() {
        System.out.println(
                input.stream()
                .map(SeatingGroup::new)
                .map(SeatingGroup::countUniqueAnswers)
                .reduce(0, Integer::sum)
        );
    }

    public static void part2() {
        System.out.println(
                input.stream()
                        .map(SeatingGroup::new)
                        .map(SeatingGroup::countAllYesAnswers)
                        .reduce(0, Integer::sum)
        );
    }

    public static void main(String[] args) {
        part1();
        part2();
    }
}
