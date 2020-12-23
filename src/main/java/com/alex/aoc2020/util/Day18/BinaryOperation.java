package com.alex.aoc2020.util.Day18;

abstract public class BinaryOperation extends ExpressionComponent{
    protected ExpressionComponent left;
    protected ExpressionComponent right;

    public BinaryOperation(ExpressionComponent l, ExpressionComponent r) {
        left = l;
        right = r;
        asString = " ( " + l.toString()  + " ? " + r.toString() + " ) ";
    }




}
