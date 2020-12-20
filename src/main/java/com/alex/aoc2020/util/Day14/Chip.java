package com.alex.aoc2020.util.Day14;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Chip {
    private final List<String> instructions;
    private Hashtable<String, String> memoryAddresses = new Hashtable<>();

    public Chip(List<String> instructionList) {
        instructions = instructionList;
    }

    public void decodeProgram() {
        decodeProgram(0);
    }


        public void decodeProgram(int version){
        String memoryRegex = "mem\\[(\\d+)\\] = (\\d+)";
        String maskRegex = "mask = ([X01]+)";

        Pattern memoryPattern = Pattern.compile(memoryRegex);
        Pattern maskPattern = Pattern.compile(maskRegex);
        Matcher matcher;
        String mask = "";

        for (String s : instructions) {
            if (s.matches(memoryRegex)) {
                matcher = memoryPattern.matcher(s);
                matcher.find();
                if (version == 0) {
                    memoryAddresses.put(matcher.group(1),
                            applyMaskToBinaryString(Integer.toBinaryString(Integer.parseInt(matcher.group(2))), mask));
                } else {
                    List<String> addresses = applyMaskToBinaryString2(Integer.toBinaryString(Integer.parseInt(matcher.group(1))), mask);
                    for (String bs : addresses) {
                        memoryAddresses.put(Long.toString(Long.parseLong(bs, 2)), matcher.group(2));
                    }
                }

            } else if (s.matches(maskRegex)) {
                matcher = maskPattern.matcher(s);
                matcher.find();
                mask = matcher.group(1);

            }
        }

    }

    private String applyMaskToBinaryString(String bin, String mask) {
        bin = padBinaryStringToLength(bin, mask.length());

        StringBuilder sb = new StringBuilder(bin);
        for (int i = 0; i < mask.length(); i++) {
            if (!(mask.charAt(i) == 'X')) {
                sb.setCharAt(i, mask.charAt(i));
            }
        }

        return Long.toString(Long.parseLong(sb.toString(),2));
    }

    private List<String> applyMaskToBinaryString2(String bin, String mask) {
        List<String> memoryAddresses = new ArrayList<>();
        bin = padBinaryStringToLength(bin, mask.length());
        StringBuilder sb = new StringBuilder(bin);
        for (int i = 0; i < mask.length(); i++) {
            if (!(mask.charAt(i) == '0')) {
                sb.setCharAt(i, mask.charAt(i));
            }
        }

        int nFloating = getNumberFloatingBits(sb.toString());
        String binaryFloating;
        for (int i = 0; i < (int) Math.pow(2, nFloating); i++) {
            binaryFloating = padBinaryStringToLength(Integer.toBinaryString(i), nFloating);
            StringBuilder sbVariations = new StringBuilder(sb.toString());

            int stringIndex = 0;
            int floatingBinaryIndex = 0;
            while(stringIndex < sb.toString().length()) {
                if (sb.toString().charAt(stringIndex) == 'X') {
                    sbVariations.setCharAt(stringIndex, binaryFloating.charAt(floatingBinaryIndex));
                    floatingBinaryIndex++;
                }
                stringIndex++;
            }
            memoryAddresses.add(sbVariations.toString());
        }
        return memoryAddresses;
    }

    private String padBinaryStringToLength(String bin, int n) {
        if (bin.length() < n) {
            int diff = n - bin.length();
            String s = "0";
            bin = IntStream.range(0, diff).mapToObj(i -> s).collect(Collectors.joining()) + bin;
        }

        return bin;
    }

    private int getNumberFloatingBits(String bin) {
        return bin.chars()
                .mapToObj(c -> (char) c)
                .filter(c -> c == 'X')
                .collect(Collector.of(
                StringBuilder::new,
                StringBuilder::append,
                StringBuilder::append,
                StringBuilder::toString)).length();

    }




    public long returnMemorySum() {
        Set<String> setOfMemoryAddresses = memoryAddresses.keySet();
        long sum = 0;
        for (String key : setOfMemoryAddresses) {
            sum += Long.parseLong(memoryAddresses.get(key));
        }

        return sum;
    }
}
