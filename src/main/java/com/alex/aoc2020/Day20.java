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
        Image img = new Image(input.stream()
                .map(Tile::new)
                .collect(Collectors.toList()));

        try {
            img.arrange();
        } catch (Exception e) {
            e.printStackTrace();
        }


        img.removeTileBorders();
        System.out.println(img.searchForSeaMonsters());
        System.out.println(img);

//        img.rotate(0);
//        img.doFlipTypeSelf(2);
//        System.out.println(img);

    }

    public static void main(String[] args) {
        part1();
        part2();
    }
}
