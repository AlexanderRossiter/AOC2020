package com.alex.aoc2020;

import com.alex.aoc2020.util.InputGetter;

import java.util.*;
import java.util.stream.Collectors;

public class Day15 {
    private static final InputGetter inputGetter = new InputGetter();
    private static final String PATH = "/Users/ADR/Documents/AOC2020/src/main/resources/inputs";
    private static final List<Integer> input = Arrays.stream(inputGetter.getInputAsList(15, PATH).get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());
    private static HashMap<Integer, List<Integer>> lastTimeSpokenMap;

    public static void part1() {
        initialiseHashMap();
        System.out.println(playMemoryGame(2020));
    }

    public static void part2() {
        initialiseHashMap();
        System.out.println(playMemoryGame(30000000));
    }

    public static void main(String[] args) {
        part1();
        part2();
    }


    private static int playMemoryGame(int noRounds) {
        int thisNumber = input.get(input.size()-1);
        for (int i = input.size(); i < noRounds; i++) {
            thisNumber = numberPreviouslySpoken(thisNumber)
                    ? getDifferenceBetweenRepeatedNumbers(thisNumber) : 0;

            addNumberToHashMap(thisNumber, i);
        }
        return thisNumber;
    }

    private static void addNumberToHashMap(int n, int idx) {
        if (lastTimeSpokenMap.containsKey(n)) {
            List<Integer> arr = lastTimeSpokenMap.get(n);
            arr.add(idx);
            lastTimeSpokenMap.put(n, arr);
        } else {
            lastTimeSpokenMap.put(n, new ArrayList<>(Collections.singletonList(idx)));
        }
    }

    private static void initialiseHashMap() {
        lastTimeSpokenMap = new HashMap<>();
        for (int i = 0; i < input.size(); i++) {
            addNumberToHashMap(input.get(i), i);
        }
    }

    private static boolean numberPreviouslySpoken(int n) {
        return (lastTimeSpokenMap.containsKey(n) && lastTimeSpokenMap.get(n).size() > 1);
    }

    private static int getDifferenceBetweenRepeatedNumbers(int n) {
        List<Integer> l = lastTimeSpokenMap.get(n);
        return l.get(l.size()-1) - l.get(l.size()-2);
    }

}
