package com.alex.aoc2020.util.Day10;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JoltageAdapterDaisyChain {
    List<JoltageAdapter> daisyChain = new ArrayList<>();
    List<JoltageAdapter> availableAdapters;
    int outletJoltageRating = 0;
    int numberTotalAdapters;


    public JoltageAdapterDaisyChain(List<JoltageAdapter> allAdapters) {
        availableAdapters = allAdapters;
        numberTotalAdapters = allAdapters.size();
        generateDaisyChain();
    }

    public void generateDaisyChain() {
        int lastOutputJoltage = outletJoltageRating;
        JoltageAdapter picked;

        while (daisyChain.size() < numberTotalAdapters) {
            int finalLastOutputJoltage = lastOutputJoltage;
            picked = availableAdapters.stream()
                        .filter(ja -> ja.canAcceptInputJoltage(finalLastOutputJoltage))
                        .min(Comparator.comparingInt(JoltageAdapter::getOutputJoltage))
                        .orElse(new JoltageAdapter(-1));
            daisyChain.add(picked);
            availableAdapters.remove(picked);
            lastOutputJoltage = picked.getOutputJoltage();
        }
        daisyChain.add(0, new JoltageAdapter(outletJoltageRating));
        daisyChain.add(new JoltageAdapter(lastOutputJoltage+3));
    }

    public int getNumberWithJoltageDifference(int joltageDifference) {
        return (int) IntStream.range(0, daisyChain.size()-1)
                .map(i -> daisyChain.get(i+1).getOutputJoltage()-daisyChain.get(i).getOutputJoltage())
                .filter(diff -> diff == joltageDifference)
                .count();

    }

    public int size() {
        return daisyChain.size();
    }

    public List<JoltageAdapter> getDaisyChainList() {
        return daisyChain;
    }

    public boolean adapterIsRemovable(int i) {
        return (daisyChain.get(i+1).getOutputJoltage() - daisyChain.get(i-1).getOutputJoltage() < 4);
    }

    public JoltageAdapter getAdapterAtIndex(int i) {
        return daisyChain.get(i);
    }

    @Override
    public String toString() {
        return daisyChain.stream()
                .map(jc -> String.valueOf(jc.getOutputJoltage()))
                .collect(Collectors.joining("-", "{", "}"));
    }
}
