package com.alex.aoc2020;

import com.alex.aoc2020.util.Day20.Image;
import com.alex.aoc2020.util.Day20.Tile;
import com.alex.aoc2020.util.InputGetter;

import java.util.List;
import java.util.stream.Collectors;

public class Day20 {
    private static final InputGetter inputGetter = new InputGetter();
    private static final String PATH = "/Users/ADR/Documents/AOC2020/src/main/resources/inputs";
    private static final List<String> input = inputGetter.getInputAsList(20, PATH, "\n\n");

    public static void part1() {
        System.out.println(new Image(
                input.stream()
                        .map(Tile::new)
                        .collect(Collectors.toList()))
                .getCornerIdProduct());
    }

    public static void part2() {
        Image img = tryImageArrange( new Image(input.stream()
                .map(Tile::new)
                .collect(Collectors.toList()))).removeTileBorders();

        System.out.println(img.searchForSeaMonsters());
    }

    public static Image tryImageArrange(Image img) {
        try {
            img.arrange();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;
    }

    public static void main(String[] args) {
        part1();
        part2();
    }
}
