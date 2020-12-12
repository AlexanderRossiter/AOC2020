package com.alex.aoc2020;

import com.alex.aoc2020.util.Day10.JoltageAdapter;
import com.alex.aoc2020.util.Day10.JoltageAdapterDaisyChain;
import com.alex.aoc2020.util.InputGetter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day10 {
    private static final InputGetter inputGetter = new InputGetter();
    private static final String PATH = "/Users/ADR/Documents/AOC2020/src/main/resources/inputs";
    private static final List<Integer> input = inputGetter.getInputAsIntArrayList(10, PATH);

    public static void part1() {
        JoltageAdapterDaisyChain dc = new JoltageAdapterDaisyChain(
                input.stream()
                        .map(JoltageAdapter::new)
                        .collect(Collectors.toList()));

        System.out.println(dc.toString());
        System.out.println(dc.getNumberWithJoltageDifference(1) * dc.getNumberWithJoltageDifference(3));
    }

    public static void part2() {
        JoltageAdapterDaisyChain dc = new JoltageAdapterDaisyChain(
                input.stream()
                        .map(JoltageAdapter::new)
                        .collect(Collectors.toList()));

        //System.out.println(findNumberValidCombinations(dc).size());

        getNumberValidChains(dc);
    }

    public static void main(String[] args) {
        part1();
        part2();
    }

    public static int getNumberValidChains(JoltageAdapterDaisyChain daisyChain) {
        List<Integer> removableAdapterIndices = IntStream.range(1, daisyChain.size() - 1)
                .filter(daisyChain::adapterIsRemovable)
                .boxed()
                .collect(Collectors.toList());
        List<JoltageAdapter> removableAdapters = removableAdapterIndices.stream()
                .map(daisyChain::getAdapterAtIndex)
                .collect(Collectors.toList());
        List<Integer> gapBetweenRemovableAdapters = IntStream.range(0, removableAdapters.size() - 1)
                .map(i -> removableAdapters.get(i+1).getOutputJoltage() - removableAdapters.get(i).getOutputJoltage())
                .boxed()
                .collect(Collectors.toList());
//        int count = 0;
//
//        count += (int) Math.pow(2, gapBetweenRemovableAdapters.stream().filter(i -> i > 3).count())-1;
//        for (int i : gapBetweenRemovableAdapters) {
//            if (i == 1) {
//                factor += 1;
//            }
//        }

        System.out.println(gapBetweenRemovableAdapters);

        return gapBetweenRemovableAdapters.size();
    }

    public static Set<String> stupidMethodThatTakesTooLong(JoltageAdapterDaisyChain daisyChain) {
        List<Integer> removableAdapterIndices = IntStream.range(1, daisyChain.size() - 1)
                .filter(daisyChain::adapterIsRemovable)
                .boxed()
                .collect(Collectors.toList());
        Set<String> daisyChainSet = new HashSet<>();
        daisyChainSet.add(daisyChain.toString());
        for (int i : removableAdapterIndices) {
            List<JoltageAdapter> reducedDaisyChainList = new ArrayList<>(daisyChain.getDaisyChainList());
            reducedDaisyChainList.remove(i);
            reducedDaisyChainList.remove(0);
            reducedDaisyChainList.remove(reducedDaisyChainList.size()-1);

            JoltageAdapterDaisyChain reducedDaisyChain = new JoltageAdapterDaisyChain(reducedDaisyChainList);
            daisyChainSet.addAll(stupidMethodThatTakesTooLong(reducedDaisyChain));
        }

        return daisyChainSet;
    }
}
