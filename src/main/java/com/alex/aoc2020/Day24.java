package com.alex.aoc2020;

import com.alex.aoc2020.util.Day24.Coordinate;
import com.alex.aoc2020.util.InputGetter;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.*;

public class Day24 {
    private static final List<String> input = new InputGetter().getInputAsList(24,
            "/Users/ADR/Documents/AOC2020/src/main/resources/inputs",
            "\n");
    private static final List<List<String>> instructions = new ArrayList<>();
    private static final Map<Coordinate, Boolean> tileMap = new HashMap<>();


    public static void part1() {
        System.out.println(countBlackTiles());
    }

    public static void part2() {
        run(100);
        System.out.println(countBlackTiles());
    }

    public static void main(String[] args) {
        parseInstructions();
        followAllInstructions();
        part1();
        part2();
    }

    private static int countBlackTiles() {
        return (int) tileMap.values().stream().filter(b -> b).count();
    }

    private static void run(int nDays) {
        for (int i = 0; i < nDays; i++) {
            runOneDay();
            //System.out.println(countBlackTiles());
        }
    }

    private static void runOneDay() {
        addAllAdjacentTiles();
        Map<Coordinate, Boolean> yesterday = copyHashMap(tileMap);
        for (Coordinate c : yesterday.keySet()) {
            if (shouldBeFlipped(c, yesterday)) {
                tileMap.put(c, !tileMap.get(c));
            }
        }
    }

    private static void addAllAdjacentTiles() {
        Map<Coordinate, Boolean> copy = copyHashMap(tileMap);
        for (Coordinate c : copy.keySet()) {
            List<Coordinate> surroundingCoordinates = new ArrayList<>(Arrays.asList(
                    new Coordinate(c.x + 2, c.y),
                    new Coordinate(c.x - 2, c.y),
                    new Coordinate(c.x + 1, c.y + 1),
                    new Coordinate(c.x + 1, c.y - 1),
                    new Coordinate(c.x - 1, c.y - 1),
                    new Coordinate(c.x - 1, c.y + 1)));

            for (Coordinate c2 : surroundingCoordinates) {
                if (!tileMap.containsKey(c2)) {
                    tileMap.put(new Coordinate(c2.x, c2.y), false);
                }
            }
        }
    }

    private static boolean shouldBeFlipped(Coordinate c, Map<Coordinate, Boolean> yesterday) {
        int count = 0;
        boolean isBlack = yesterday.get(c);
        List<Coordinate> surroundingCoordinates = new ArrayList<>(Arrays.asList(
                new Coordinate(c.x+2, c.y),
                new Coordinate(c.x-2, c.y),
                new Coordinate(c.x+1, c.y+1),
                new Coordinate(c.x+1, c.y-1),
                new Coordinate(c.x-1, c.y-1),
                new Coordinate(c.x-1, c.y+1)));

        for (Coordinate c2 : surroundingCoordinates) {
            if (yesterday.containsKey(c2)) {
                count += yesterday.get(c2) ? 1 : 0;
            } else {
                tileMap.put(new Coordinate(c2.x, c2.y), false);
            }
        }
        return (isBlack && (count == 0 || count > 2)) || (!isBlack && count == 2);
    }

    private static void followAllInstructions() {
        instructions.stream().map(Day24::getTileLocation).forEach(c -> {
            if (tileMap.containsKey(c)) {
                tileMap.put(c, !tileMap.get(c));
            } else {
                tileMap.put(c, true);
            }
        });
    }

    private static void parseInstructions() {
        CharacterIterator it;
        List<String> instruction;
        StringBuilder sb;
        for (String s : input) {
            instruction = new ArrayList<>();
            it = new StringCharacterIterator(s);
            while (it.current() != CharacterIterator.DONE) {
                sb = new StringBuilder();
                if (it.current() == 'e') {
                    instruction.add("e");
                } else if (it.current() == 'w') {
                    instruction.add("w");
                } else {
                    sb.append(it.current());
                    sb.append(it.next());
                    instruction.add(sb.toString());
                }
                it.next();
            }
            instructions.add(instruction);
        }
    }


    private static Coordinate getTileLocation(List<String> instruction) {
        Coordinate c = new Coordinate(0, 0);
        for (String dir : instruction) {
            switch (dir) {
                case "e":
                    c.x += 2;
                    break;
                case "w":
                    c.x -= 2;
                    break;
                case "ne":
                    c.x += 1;
                    c.y += 1;
                    break;
                case "nw":
                    c.x -= 1;
                    c.y += 1;
                    break;
                case "sw":
                    c.x -= 1;
                    c.y -= 1;
                    break;
                case "se":
                    c.x += 1;
                    c.y -= 1;
                    break;
            }
        }
        return c;
    }

    private static Map<Coordinate, Boolean> copyHashMap(Map<Coordinate, Boolean> oldHasMap) {
        Map<Coordinate, Boolean> newHashMap = new HashMap<>();

        for (Coordinate c : oldHasMap.keySet()) {
            newHashMap.put(new Coordinate(c.x, c.y), oldHasMap.get(c));
        }

        return newHashMap;
    }
}
