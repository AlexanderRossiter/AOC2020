package com.alex.aoc2020.util.Day23;

import java.util.Objects;

public class Cup {
    private final int label;
    private int nextCupLabel;

    public Cup(int label) {
        this.label = label;
    }

    public void setNextCupLabel(int nextCupLabel) {
        this.nextCupLabel = nextCupLabel;
    }

    public int getLabel() {
        return label;
    }

    public int getNextCupLabel() {
        return nextCupLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Cup)) {
            return false;
        }

        Cup cup = (Cup) o;
        return label == cup.label;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(label);
    }

    @Override
    public String toString() {
        return String.format("%d -> %d", label, nextCupLabel);
    }
}
