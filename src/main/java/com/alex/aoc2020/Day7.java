package com.alex.aoc2020;

import com.alex.aoc2020.util.Day7.Bag;
import com.alex.aoc2020.util.InputGetter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day7 {
    private static final InputGetter inputGetter = new InputGetter();
    private static final String PATH = "/Users/ADR/Documents/AOC2020/src/main/resources/inputs";
    private static final List<String> input = inputGetter.getInputAsList(7, PATH);

    public static void part1() {

    List<Bag> bags = new ArrayList<>();
    for (String s : input) {
        bags.add(new Bag(s));
    }

    Set<String> all = new HashSet<>(countCanContain(bags, "shiny gold"));
    System.out.println(all.size());
    }

    public static void main(String[] args) {
        part1();
    }

    public static List<String> countCanContain(List<Bag> bags, String colour) {
        List<String> canContain = new ArrayList<>();
        List<List<String>> canContainAll = new ArrayList<>();

        for (Bag b : bags) {
            if (b.canContain(colour)) {
                canContain.add(b.getColour());
            }
        }
        canContainAll.add(canContain);

        for (String s : canContain) {
            canContainAll.add(countCanContain(bags, s));
        }

        return canContainAll.stream().flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
