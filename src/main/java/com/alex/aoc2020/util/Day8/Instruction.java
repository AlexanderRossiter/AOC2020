package com.alex.aoc2020.util.Day8;

public class Instruction {
    private final Operation o;
    private final int argument;

    public Instruction(Operation o_, int argument_) {
        o = o_;
        argument = argument_;
    }

    public Operation getOperation() {
        return o;
    }

    public int getArgument() {
        return argument;
    }

    public String getInstructionString() {
        String pm;
        if (Integer.signum(argument) > 0) {
            pm = "+";
        } else {
            pm = "";
        }
        return String.format("%s %s%d", o.toString(), pm, argument);
    }
}
