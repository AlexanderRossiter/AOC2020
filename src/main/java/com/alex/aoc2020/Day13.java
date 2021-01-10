package com.alex.aoc2020;

import com.alex.aoc2020.util.InputGetter;

import java.util.*;
import java.util.stream.Collectors;

public class Day13 {
    private static final InputGetter inputGetter = new InputGetter();
    private static final String PATH = "/Users/ADR/Documents/AOC2020/src/main/resources/inputs";
    private static final List<String> input = inputGetter.getInputAsList(13, PATH);
    public static final int myTimestamp = Integer.parseInt(input.get(0));


    public static void part1() {
        List<Integer> busIdList = parseBuses(input.get(1));
        int minWaitTime = Integer.MAX_VALUE;
        int tmpWaitTime;
        int myBusId = -1;
        for (int busId : busIdList) {
            tmpWaitTime = getEarliestTimeAfter(busId, myTimestamp) - myTimestamp;
            if (tmpWaitTime < minWaitTime) {
                minWaitTime = tmpWaitTime;
                myBusId = busId;
            }
        }
        System.out.println(myBusId * minWaitTime);
    }

    public static void part2() {
        HashMap<Long, Long> busIdRemainderMap = getIdRemainderMap();
        System.out.println(chineseRemainder(new ArrayList<>(busIdRemainderMap.values()),
                new ArrayList<>(busIdRemainderMap.keySet())));
    }

    public static void main(String[] args) {
        part1();
        part2();
    }

    private static int getEarliestTimeAfter(int busId, int timestamp) {
        if (timestamp % busId == 0) {
            return timestamp;
        } else {
            return busId * (timestamp / busId + 1);
        }
    }

    public static long chineseRemainder(List<Long> remainders, List<Long> divisors) {
        long prod = divisors.stream().reduce((long) 1, (a, b) -> a * b);

        long sum = 0;
        long Ni;
        for (int i = 0; i < remainders.size(); i++) {
            Ni = prod / divisors.get(i);
            sum += remainders.get(i) * gcdExtended(Ni, divisors.get(i)).get(1) * Ni;
        }

        return ((sum % prod) + prod) % prod;
    }

    public static List<Long> gcdExtended(long a, long b)
    {
        // Base Case
        long x;
        long y;
        if (a == 0)
        {
            x = 0;
            y = 1;
            return new ArrayList<>(Arrays.asList(b, x, y));
        }

        List<Long> gcd = gcdExtended(b%a, a);

        // Update x and y using results of recursive
        // call
        x = gcd.get(2) - (b/a) * gcd.get(1);
        y = gcd.get(1);

        return new ArrayList<>(Arrays.asList(gcd.get(0), x, y));
    }

    private static HashMap<Long, Long> getIdRemainderMap() {
        List<Long> busIdList = parseBuses2(input.get(1));
        HashMap<Long, Long> busIdRemainderMap = new HashMap<>();

        for (int i = 0; i < busIdList.size(); i++) {
            if (!(busIdList.get(i) == -1)) {
                busIdRemainderMap.put(busIdList.get(i),
                        (busIdList.get(i) - i) + (i / busIdList.get(i)) * busIdList.get(i));
            }
        }
        return busIdRemainderMap;
    }

    private static List<Integer> parseBuses(String busString) {
        return Arrays.stream(busString.split(","))
                .filter(s -> !s.equals("x"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private static List<Long> parseBuses2(String busString) {
        return Arrays.stream(busString.split(","))
                .map(s -> s.equals("x") ? "-1" : s)
                .map(Long::parseLong)
                .collect(Collectors.toList());
        //return Arrays.asList(busString.split(","));
    }

    public static int gcd(int a, int b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

    public static int lcm(int a, int b) {
        return (a / gcd(a, b)) * b;
    }



    // Method for the original method that wasn't going to work. Left in as they're kinf of interesting...
    private static Integer[] getFactorWithCorrectDifference(int deltaTimestamp, int baseBusId, int busId) {
        int n = deltaTimestamp % busId;  // This takes care of if there are several buses in between.
        int extraN = deltaTimestamp / busId; // The extra multiples if deltaTimestamp > busId.

        for(int i = 1; i <= lcm(busId, baseBusId); i++) {
            if ((i * busId) % baseBusId == n || (i * busId) % baseBusId == 0) {
                int lcmVal = lcm(busId, baseBusId);
                int multVal = lcmVal == busId ? 0 : i;
                //lcmVal = lcmVal == busId ? 0 : lcmVal;
                return new Integer[]{multVal, extraN, lcmVal}; // This repeats with n via (lcm * n + (i+extraN)*busId)
            }
        }
        return new Integer[]{-1,-1, -1};
    }

    public static Long getNthTimestampWithCorrectDifference(int n, int busId, Integer[] differenceInformation) {
        return ((long) differenceInformation[2] * (long) n
                + ((long) differenceInformation[0] + (long) differenceInformation[1]) * (long) busId);
    }



}