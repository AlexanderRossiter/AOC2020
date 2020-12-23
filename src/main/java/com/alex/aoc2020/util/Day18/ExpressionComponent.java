package com.alex.aoc2020.util.Day18;

abstract public class ExpressionComponent {
    protected String asString;

    abstract public long eval();

    @Override
    public String toString() {
        return asString;
    }

}
