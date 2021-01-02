package com.alex.aoc2020.util.Day8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Computer {

    public Program runInstructions(Program program) {
        program.visitedInstructions = new ArrayList<>(Collections.singletonList(0));

        int i = 0;
        Instruction instr;
        while (!program.isTerminated()) {

            instr = program.getInstruction(i);

            switch (instr.getOperation()) {
                case ACC:
                    program.addToAccumulator(instr.getArgument());
                    i++;
                    break;
                case JMP:
                    i += instr.getArgument();
                    break;
                case NOP:
                    i++;
                    break;
            }
            if (program.visitedInstructions.contains(i)) {
                System.out.println(String.format("Infinite loop detected, accumulator is %d", program.getAccumulator()));
                program.visitedInstructions.add(i);
                program.terminate();
                program.setTerminatedAt(i);
            }
            program.visitedInstructions.add(i);

            if(i == program.size()-1) {
                System.out.println(String.format("Program completed successfully, accumulator is %d", program.getAccumulator()));

                program.terminate();
                program.setTerminatedAt(i);
            }
        }

        return program;
    }

    private List<String> programToStringList(Program program) {
        return program.instructions.stream().
                map(Instruction::getInstructionString).
                collect(Collectors.toList());
    }

    public int findAndSwitchCorruptedInstruction(Program program) {
        program = runInstructions(program);
        if (program.getTerminatedAt() == program.size()) {
            return program.getAccumulator();
        }
        Program programAltered = new Program(program.getInstructionStringList());
        System.out.println(programAltered.getAccumulator());

        for (int i : program.visitedInstructions) {
            if (program.getInstruction(i).getOperation() == Operation.NOP) {
                programAltered.replaceInstructionAt(i, new Instruction(Operation.JMP, program.getInstruction(i).getArgument()));
                programAltered = runInstructions(programAltered);
                System.out.println(String.format("%s ----> %s", program.getInstruction(i).getInstructionString(), programAltered.getInstruction(i).getInstructionString()));

            } else if (program.getInstruction(i).getOperation() == Operation.JMP) {
                programAltered.replaceInstructionAt(i, new Instruction(Operation.NOP, program.getInstruction(i).getArgument()));
                programAltered = runInstructions(programAltered);
                System.out.println(String.format("%s ----> %s", program.getInstruction(i).getInstructionString(), programAltered.getInstruction(i).getInstructionString()));
            }

            if (programAltered.getTerminatedAt() == programAltered.size()-1) {
                return programAltered.getAccumulator();
            } else {
                // Reset instructions.
                programAltered = new Program(program.getInstructionStringList());
            }
        }
        return Integer.MAX_VALUE;
    }
}
