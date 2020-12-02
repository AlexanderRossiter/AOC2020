package com.alex.aoc2020;

import com.alex.aoc2020.util.InputGetter;

import java.util.ArrayList;
import java.util.List;


public class Day1 {
    private static final InputGetter inputGetter = new InputGetter();
    private static final String PATH = "/Users/ADR/Documents/AOC2020/src/main/java/com/alex/aoc2020/inputs";

    public static void part1() {

        ArrayList<Integer> input = inputGetter.getInputAsIntArrayList(1, PATH);

        int sum = 0;
        int finalI = 0;
        int finalJ = 0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.size(); j++) {
                if (i != j) {
                    sum = input.get(i) + input.get(j);
                    if (sum == 2020) {
                        finalI = i;
                        finalJ = j;
                        break;
                    }
                }
            }
        }

        System.out.println(String.format("Items are %d and %d, their product is %d.",
                input.get(finalI), input.get(finalJ), input.get(finalI) * input.get(finalJ)));

    }

        public static void part2() {
            ArrayList<Integer> input = inputGetter.getInputAsIntArrayList(1, PATH);

            int sum = 0;
            int finalI = 0;
            int finalJ = 0;
            int finalK = 0;

            for (int i = 0; i < input.size(); i++) {
                for (int j = 0; j < input.size(); j++) {
                    for (int k = 0; k < input.size(); k++) {
                        if ((i != j) && (i != k) && (j != k)){
                            sum = input.get(i) + input.get(j) + input.get(k);
                            if (sum == 2020) {
                                finalI = i;
                                finalJ = j;
                                finalK = k;
                                break;
                            }
                        }
                    }
                }
            }

            System.out.println(String.format("Items are %d, %d and %d, their product is %d.",
                    input.get(finalI), input.get(finalJ), input.get(finalK),
                    input.get(finalI) * input.get(finalJ) * input.get(finalK)));




    }

    public static void main(String[] args) {
        part1();
        part2();
    }
}
