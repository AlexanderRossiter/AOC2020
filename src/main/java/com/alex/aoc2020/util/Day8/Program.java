package com.alex.aoc2020.util.Day8;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Program {
    private boolean isTerminated = false;
    public List<Instruction> instructions = new ArrayList<>();
    private static final Pattern pattern = Pattern.compile("([a-z]{3})\\s([+-])(\\d+)");
    private int accumulator = 0;
    private int terminatedAt = -1;
    public List<Integer> visitedInstructions;
    private final List<String> instructionStringList;


    public Program(List<String> instructions_) {
        generateProgram(instructions_);
        instructionStringList = instructions_;
    }

    public List<String> getInstructionStringList() {
        return instructionStringList;
    }

    public void refresh() {
        accumulator = 0;
        terminatedAt = -1;
        isTerminated = false;
        visitedInstructions = new ArrayList<>();
    }

    private void generateProgram(List<String> instructionList) {
        for (String instruction : instructionList) {
            instructions.add(parseInstruction(instruction));
        }
    }
    private Instruction parseInstruction(String instruction) {
        Matcher matcher = pattern.matcher(instruction);

        if (matcher.find()) {
            String operation = matcher.group(1);
            int argument = Integer.parseInt(matcher.group(3));
            try {
                int sign = getPlusMinus(matcher.group(2).charAt(0));
                return new Instruction(getOperation(operation), sign * argument);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private int getPlusMinus(char c) throws Exception {
        if (c == '+') {
            return 1;
        } else if (c == '-') {
            return -1;
        } else {
            throw new Exception("Bad +-");
        }
    }

    private Operation getOperation(String op) {
        Operation operation;
        switch (op) {
            case "acc":
                operation= Operation.ACC;
                break;
            case "jmp":
                operation = Operation.JMP;
                break;
            case "nop":
                operation = Operation.NOP;
                break;
            default:
                operation = null;
        }
        return operation;
    }

    public int size() {
        return instructions.size();
    }

    public void terminate() {
        isTerminated = true;
    }

    public Instruction getInstruction(int i) {
        return instructions.get(i);
    }

    public boolean isTerminated() {
        return isTerminated;
    }

    public int getAccumulator() {
        return accumulator;
    }

    public void addToAccumulator(int i) {
        accumulator += i;
    }

    public int getTerminatedAt() {
        return terminatedAt;
    }

    public void setTerminatedAt(int terminatedAt) {
        this.terminatedAt = terminatedAt;
    }

    public void replaceInstructionAt(int i, Instruction newIntruction) {
        instructions.set(i, newIntruction);
    }
}
