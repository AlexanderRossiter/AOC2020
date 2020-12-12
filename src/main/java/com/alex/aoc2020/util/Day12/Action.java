package com.alex.aoc2020.util.Day12;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Action {
    private Direction direction;
    private int distance;

    public Action(String instruction) {
        parseInstruction(instruction);
    }

    private void parseInstruction(String instruction) {
        Pattern pattern = Pattern.compile("(\\w)(\\d+)");
        Matcher matcher = pattern.matcher(instruction);

        if (matcher.find()) {
            String operation = matcher.group(1);
            distance = Integer.parseInt(matcher.group(2));
            switch (operation) {
                case "N":
                    direction = Direction.N;
                    break;
                case "S":
                    direction = Direction.S;
                    break;
                case "W":
                    direction = Direction.W;
                    break;
                case "E":
                    direction = Direction.E;
                    break;
                case "L":
                    direction = Direction.L;
                    break;
                case "R":
                    direction = Direction.R;
                    break;
                case "F":
                    direction = Direction.F;
                    break;
            }
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public int getDistance() {
        return distance;
    }
}
