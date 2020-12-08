package com.alex.aoc2020.util.Day8;

public enum Operation {
    ACC("acc"), JMP("jmp"), NOP("nop");

    private final String string;
    Operation(String string_) {
        this.string = string_;
    }

    public String toString() {
        return string;
    }
}
