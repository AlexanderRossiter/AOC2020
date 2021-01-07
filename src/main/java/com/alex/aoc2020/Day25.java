package com.alex.aoc2020;

import com.alex.aoc2020.util.InputGetter;

import java.util.List;

public class Day25 {
    private static final List<String> input = new InputGetter().getInputAsList(25,
            "/Users/ADR/Documents/AOC2020/src/main/resources/inputs",
            "\n");
    private static final long cardPublicKey = Long.parseLong(input.get(0));
    private static final long doorPublicKey = Long.parseLong(input.get(1));

    public static void part1() {
        System.out.println(getEncryptedKey( getLoopSize(7, cardPublicKey), doorPublicKey ));
        System.out.println(getEncryptedKey( getLoopSize(7, doorPublicKey), cardPublicKey ));

    }

    public static void main(String[] args) {
        part1();
    }

    private static int getLoopSize(long subjectNumber, long key) {
        long testVal = 1;
        int loopNumber = 0;
        while (testVal != key) {
            testVal *= subjectNumber;
            testVal = testVal % 20201227;
            loopNumber++;
        }

        return loopNumber;
    }

    private static long getEncryptedKey(int loopSize, long subjectNumber) {
        long val = 1;
        for (int i = 0; i < loopSize; i++) {
            val *= subjectNumber;
            val = val % 20201227;
        }
        return val;
    }

}
