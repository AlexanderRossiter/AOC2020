package com.alex.aoc2020;

import com.alex.aoc2020.util.InputGetter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day9 {
    private static final InputGetter inputGetter = new InputGetter();
    private static final String PATH = "/Users/ADR/Documents/AOC2020/src/main/resources/inputs";
    private static final List<Long> input = parseInput(inputGetter.getInputAsList(9, PATH));


    public static void part1() {
        System.out.println(findFirstInvalid());
    }

    public static void part2() {
        List<Long> contiguousSet = findContiguousSet(findFirstInvalid());
        System.out.println(Collections.max(contiguousSet) + Collections.min(contiguousSet));
    }


    public static void main(String[] args) {
        part1();
        part2();
    }

    public static List<Long> parseInput(List<String> inputString) {
        List<Long> inputLong = new ArrayList<>();
        for (String s : inputString) {
            inputLong.add(Long.parseLong(s));
        }

        return inputLong;
    }

    public static Long findFirstInvalid() {
        List<Long> preceding25;
        for (int i = 25; i < input.size(); i++) {
            preceding25 = new ArrayList<>(input.subList(i-25,i));
            if (!isValid(input.get(i), preceding25)) {
                return input.get(i);
            }
        }
        return (long) -1;

    }

    public static boolean isValid(long testInt, List<Long> preceding25) {
        for (int i = 0; i < preceding25.size(); i++ ) {
            for (int j = i+1; j < preceding25.size(); j++) {
                if (preceding25.get(i) + preceding25.get(j) == testInt) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<Long> findContiguousSet(long desiredSum) {
        List<Long> contiguousSet;
        for (int startingIndex = 0; startingIndex < input.size(); startingIndex++) {
            long sum = 0;
            int loopIdx = startingIndex+1;
            while (sum <= desiredSum) {
                contiguousSet = new ArrayList<>(input.subList(startingIndex, loopIdx+1));
                sum = contiguousSet.stream().reduce((long) 0, Long::sum);
                if (sum == desiredSum) {
                    return contiguousSet;
                }
                loopIdx++;
            }
        }

        return new ArrayList<>();
    }
}
