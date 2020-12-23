package com.alex.aoc2020.util.Day18;

public class Constant extends Value{
    private final long constant;

    public Constant(long constant_) {
        constant = constant_;
        asString = Long.toString(constant_);
    }

    @Override
    public long eval() {
        return constant;
    }
}
