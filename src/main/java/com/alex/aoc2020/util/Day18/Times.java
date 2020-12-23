package com.alex.aoc2020.util.Day18;

public class Times extends BinaryOperation {

    public Times(ExpressionComponent l, ExpressionComponent r) {
        super(l, r);
        asString = asString.replace("?", "*");

    }

    @Override
    public long eval() {
        return left.eval() * right.eval();
    }
}
