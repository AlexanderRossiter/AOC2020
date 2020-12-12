package com.alex.aoc2020.util.Day10;

public class JoltageAdapter {
    private final int outputJoltage;

    public JoltageAdapter(int outputJoltage_) {
        outputJoltage = outputJoltage_;
    }

    public int getOutputJoltage() {
        return outputJoltage;
    }

    public boolean canAcceptInputJoltage(int inputJoltage) {
        return (inputJoltage >= outputJoltage-3 && inputJoltage <= outputJoltage-1);
    }
}
