package com.alex.aoc2020.util.Day12;

public enum Direction {
    N(0), S(180), E(90), W(270), L(-1), R(-1), F(-1);

    private final int bearing;
    Direction(int bearing_) {
        this.bearing = bearing_;
    }

    public int getValue() {
        return bearing;
    }
    public static Direction findByBearing(int desiredBearing){
        for(Direction d : values()){
            if( d.getValue() == desiredBearing){
                return d;
            }
        }
        return null;
    }
}
