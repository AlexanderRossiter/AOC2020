package com.alex.aoc2020;

import com.alex.aoc2020.util.InputGetter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day13 {
    private static final InputGetter inputGetter = new InputGetter();
    private static final String PATH = "/Users/ADR/Documents/AOC2020/src/main/resources/inputs";
    private static final List<String> input = inputGetter.getInputAsList(13, PATH);
    public static final int myTimestamp = Integer.parseInt(input.get(0));


    public static void part1() {
        List<Bus> buses = parseBusses(input.get(1))
                .stream()
                .map(Bus::new)
                .collect(Collectors.toList());

        Bus myBus = buses.get(IntStream.range(0, buses.size())
                .boxed()
                .min(Comparator.comparingInt(buses
                        .stream()
                        .map(b -> b.getEarliestTimeAfter(myTimestamp))
                        .map(ts -> ts - myTimestamp).collect(Collectors.toList())::get))
                .get());



        System.out.println((myBus.getEarliestTimeAfter(myTimestamp)-myTimestamp) * myBus.getBusId());

    }

    public static void part2() throws Exception {
        List<String> busTimestampList = Arrays.asList(input.get(1).split(","));
        List<Bus> buses = new ArrayList<>();

        int baseBusId = -1;
        for (int i = 0; i < busTimestampList.size(); i++) {
            if (!busTimestampList.get(i).equals("x")) {
                buses.add(new Bus(busTimestampList.get(i)));
                if (i == 0) {
                    baseBusId = buses.get(0).getBusId();
                }
                buses.get(buses.size() - 1).setRequiredDeltaTimestamp(i, baseBusId);
            }
        }

//        Bus b1 = new Bus("7");
//        Bus b2 = new Bus("9");
//
//        b2.setRequiredDeltaTimestamp(10, b1.getBusId());
//
//        for (int i = 0; i < 25; i++) {
//            int a = (lcm(b1.getBusId(), b2.getBusId()) * i + b2.getnCorrectDelta() + b2.extraN) * b2.getBusId();
//            System.out.println((a - (((a-b2.extraN*b2.getBusId()) / b1.getBusId()) * b1.getBusId())));// + b2.getBusId() * b2.extraN);
//        }



    }

    private static List<String> parseBusses(String busString) {
        return Arrays.stream(busString.split(","))
                .filter(s -> !s.equals("x"))
                .collect(Collectors.toList());
    }

    public static int gcd(int a, int b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

    public static int lcm(int a, int b) {
        return (a / gcd(a, b)) * b;
    }

    public static void main(String[] args) {
        part1();
        try {
            part2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

class Bus {
    private final int busId;
    private int requiredDeltaTimestamp;
    private int nCorrectDelta;
    public int extraN = 0;
    public List<Integer> validTimestamps = new ArrayList<>();
    public Bus(String busId_) {
        busId = Integer.parseInt(busId_);
    }

    public int getEarliestTimeAfter(int timeStamp) {
        if (timeStamp % busId == 0) {
            return timeStamp;
        } else
            return busId * (timeStamp / busId + 1);
    }

    public int getBusId() {
        return busId;
    }

    public void setRequiredDeltaTimestamp(int deltaTimestamp, int baseBusID) throws Exception {
        requiredDeltaTimestamp = deltaTimestamp;

        int n = deltaTimestamp % busId;
        extraN = deltaTimestamp / busId;


        for(int i = 1; i <= Day13.lcm(busId, baseBusID); i++) {
            if ((i * busId) % baseBusID == n || (i * busId) % baseBusID == 0) {
                nCorrectDelta = i;
                return;
            }
        }
        //System.out.println(deltaTimestamp);
        throw new Exception("No valid deltaT");

    }

    public int getnCorrectDelta() {
        return nCorrectDelta;
    }

    public int getRequiredDeltaTimestamp() {
        return requiredDeltaTimestamp;
    }

    public int getNthTimestampWithCorrectDifference(int n, int baseBusId) {
        return (Day13.lcm(busId, baseBusId) * n + getnCorrectDelta() + extraN) * getBusId();
    }

    public void fillValidTimestampsUpTo(int n, int baseBusId) {
        for (int i = 1; i < n; i++) {
            validTimestamps.add(getNthTimestampWithCorrectDifference(i, baseBusId));
        }
    }




}
