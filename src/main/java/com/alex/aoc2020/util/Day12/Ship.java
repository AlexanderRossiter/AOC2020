package com.alex.aoc2020.util.Day12;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Ship {
    private List<Action> actionList = new ArrayList<>();
    private int x = 0;
    private int y = 0;
    private int waypointX = 10;
    private int waypointY = 1;
    private Direction facing = Direction.E;
    private int facingBearing = 90;

    public Ship(List<String> instructionList) {
        actionList = instructionList.stream().map(Action::new).collect(Collectors.toList());
    }

    public void steer() {
        for (Action a : actionList) {
            followAction(a);
        }
    }

    public void waypointSteer() {
        for (Action a : actionList) {
            followWaypointAction(a);
            System.out.println(String.format("x-%d y-%d wpx-%d wpy-%d", x, y, waypointX, waypointY));
        }
    }

    private void followWaypointAction(Action a) {
        Direction d = a.getDirection();
        switch (d) {
            case N:
                waypointY += a.getDistance();
                break;
            case S:
                waypointY -= a.getDistance();
                break;
            case W:
                waypointX -= a.getDistance();
                break;
            case E:
                waypointX += a.getDistance();
                break;
            case L:
                rotateWaypointLeft(a.getDistance());
                break;
            case R:
                rotateWaypointRight(a.getDistance());
                break;
            case F:
                gotToWaypoint(a.getDistance());
                break;
        }
    }

    private void gotToWaypoint(int distance) {
        x += distance * waypointX;
        y += distance * waypointY;
    }

    private void rotateWaypointRight(int distance) {
        int tmpWptX = waypointX;
        int tmpWptY = waypointY;
        // They're never negative so this is okay also always less that 360 and between 1 and 3
        int nRotations = distance / 90;

        switch (nRotations) {
            case 1:
                waypointY = -tmpWptX;
                waypointX = tmpWptY;
                break;
            case 2:
                waypointY = -tmpWptY;
                waypointX = -tmpWptX;
                break;
            case 3:
                waypointY = tmpWptX;
                waypointX = -tmpWptY;
        }
    }

    private void rotateWaypointLeft(int distance) {
        int tmpWptX = waypointX;
        int tmpWptY = waypointY;
        // They're never negative so this is okay also always less that 360 and between 1 and 3
        int nRotations = distance / 90;

        switch (nRotations) {
            case 1:
                waypointY = tmpWptX;
                waypointX = -tmpWptY;
                break;
            case 2:
                waypointY = -tmpWptY;
                waypointX = -tmpWptX;
                break;
            case 3:
                waypointY = -tmpWptX;
                waypointX = tmpWptY;
        }
    }

    private void followAction(Action a) {
        Direction d = a.getDirection();
        switch (d) {
            case N:
                y += a.getDistance();
                break;
            case S:
                y -= a.getDistance();
                break;
            case W:
                x -= a.getDistance();
                break;
            case E:
                x += a.getDistance();
                break;
            case L:
                rotateLeft(a.getDistance());
                break;
            case R:
                rotateRight(a.getDistance());
                break;
            case F:
                gotForward(a.getDistance());
                break;
        }
    }

    private void gotForward(int distance) {
        switch (facing) {
            case N:
                y += distance;
                break;
            case S:
                y -= distance;
                break;
            case W:
                x -= distance;
                break;
            case E:
                x += distance;
                break;
        }
    }

    private void rotateRight(int distance) {
        facingBearing += distance;
        if (facingBearing < 0) {
            facingBearing = 360 + facingBearing;
        }
        facingBearing = facingBearing % 360;
        facing = Direction.findByBearing(facingBearing);

    }

    private void rotateLeft(int distance) {
        facingBearing -= distance;
        if (facingBearing < 0) {
            facingBearing = 360 + facingBearing;
        }
        facingBearing = facingBearing % 360;
        facing = Direction.findByBearing(facingBearing);

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
