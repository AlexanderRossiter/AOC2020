package com.alex.aoc2020;

import com.alex.aoc2020.util.Day5.Plane;
import com.alex.aoc2020.util.Day5.Ticket;
import com.alex.aoc2020.util.InputGetter;

import java.util.ArrayList;
import java.util.List;

public class Day5 {
    private static final InputGetter inputGetter = new InputGetter();
    private static final String PATH = "/Users/ADR/Documents/AOC2020/src/main/resources/inputs";
    private static final List<String> input = inputGetter.getInputAsList(5, PATH);


    public static void part1() {
        int maxSeatId = -1;
        Ticket t;
        for (String s : input) {
            t = new Ticket(s);
            if (t.getSeatId() > maxSeatId) {
                maxSeatId = t.getSeatId();
            }
        }
        System.out.println(String.format("Max seat ID: %d", maxSeatId));
    }

    public static void part2() {
        List<Ticket> tickets = new ArrayList<>();
        for (String s : input) {
            tickets.add(new Ticket(s));
        }

        Plane plane = new Plane(tickets);

        plane.printPlaneSeats();
        System.out.println(plane.getMySeatId());
    }

    public static void main(String[] args) {
        part1();
        part2();
    }
}


