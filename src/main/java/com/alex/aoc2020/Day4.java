package com.alex.aoc2020;

import com.alex.aoc2020.util.Day4.Passport;
import com.alex.aoc2020.util.InputGetter;

import java.util.ArrayList;
import java.util.List;

public class Day4 {
    private static final InputGetter inputGetter = new InputGetter();
    private static final String PATH = "/Users/ADR/Documents/AOC2020/src/main/resources/inputs";
    private static final  List<String> input = sortInput( inputGetter.getInputAsList(4, PATH, "\n\n") );


    private static List<String> sortInput(List<String> input) {
        for (int i = 0; i < input.size(); i ++) {
            input.set(i, input.get(i).replace("\n", " ")) ;
        }

        return input;
    }

    private static int countValidPassports1(List<Passport> passports) {
        int count = 0;
        for (Passport p : passports) {
            if (p.passportContainsAllRequiredFields()) {
                count += 1;
            }
        }
        return count;
    }

    private static int countValidPassports2(List<Passport> passports) {
        int count = 0;
        for (Passport p : passports) {
            if (p.passportIsValid()) {
                count += 1;
            }
        }
        return count;
    }

    public static void part1() {
        List<Passport> passports = new ArrayList<>();

        for (String s : input) {
            passports.add(new Passport(s));
        }

        System.out.println(String.format("Number of valid passports: %d", countValidPassports1(passports)));
    }

    public static void part2() {
        List<Passport> passports = new ArrayList<>();

        for (String s : input) {
            passports.add(new Passport(s));
        }
        System.out.println(String.format("Number of valid passports: %d", countValidPassports2(passports)));

    }

    public static void main(String[] args) {
        part1();
        part2();
    }

}
