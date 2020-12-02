package com.alex.aoc2020;

import com.alex.aoc2020.util.InputGetter;

import java.util.*;

public class Day2 {
    private static final InputGetter inputGetter = new InputGetter();
    private static final String PATH = "/Users/ADR/Documents/AOC2020/src/main/java/com/alex/aoc2020/inputs";

    public static List<String> stringSplitter(String raw) {
        String[] rawSplit = raw.split("[- :]");
        List<String> strSplit = new ArrayList<String>(Arrays.asList(rawSplit));

        strSplit.removeAll(Arrays.asList("", null));

        return strSplit;
    }

    public static List<List<String>> sortInputData(List<String> input) {
        List<List<String>> data = new ArrayList<>();
        for (String s : input) {
            data.add(stringSplitter(s));
        }

        return data;
    }

    public static int countOccurances(String s, char c) {
        int count = 0;

        for (int i = 0; i < s.length(); i ++ ) {
            if (s.charAt(i) == c) {
                count += 1;
            }
        }

        return count;
    }

    public static void part1() {
        List<String> input = inputGetter.getInputAsList(2, PATH);
        List<List<String>> data = sortInputData(input);

        int occurances;
        int noValid = 0;
        for (List<String> list : data) {
            occurances = countOccurances(list.get(3), list.get(2).charAt(0));
            if (occurances >= Integer.parseInt(list.get(0)) && occurances <= Integer.parseInt(list.get(1))) {
                noValid += 1;
                System.out.println(String.format("LB: %d; UB: %d; Char: %c; String: %s; Occurances: %d", Integer.parseInt(list.get(0)),
                        Integer.parseInt(list.get(1)), list.get(2).charAt(0), list.get(3), occurances));
            }
        }

        System.out.println(String.format("Number of valid passwords is %d.", noValid));

    }

    public static void part2() {
        List<String> input = inputGetter.getInputAsList(2, PATH);
        List<List<String>> data = sortInputData(input);

        int noValid = 0;
        int idx1;
        int idx2;
        for (List<String> list : data) {
            idx1 = Integer.parseInt(list.get(0))-1;
            idx2 = Integer.parseInt(list.get(1))-1;
            if ((list.get(3).charAt(idx1) ==  list.get(2).charAt(0) && list.get(3).charAt(idx2) !=  list.get(2).charAt(0))
                    || (list.get(3).charAt(idx2) ==  list.get(2).charAt(0) && list.get(3).charAt(idx1) !=  list.get(2).charAt(0))) {
                noValid += 1;
                System.out.println(String.format("LB: %d; UB: %d; Char: %c; String: %s;", Integer.parseInt(list.get(0)),
                        Integer.parseInt(list.get(1)), list.get(2).charAt(0), list.get(3)));
            }
        }

        System.out.println(String.format("Number of valid passwords is %d.", noValid));

    }

    public static void main(String[] args) {
        part1();
        part2();

    }

}
