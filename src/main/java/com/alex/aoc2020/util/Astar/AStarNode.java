package com.alex.aoc2020.util.Astar;

import java.util.ArrayList;
import java.util.List;

public class AStarNode {
    private final List<AStarNode> neighbours = new ArrayList<AStarNode>();
    private int index;
    public final boolean isStart = false;
    public final boolean isEnd = false;
    public double fScore = Double.POSITIVE_INFINITY;
    public double gScore = Double.POSITIVE_INFINITY;


    public int getIndex() {
        return index;
    }

    public void setIndex(int i) {
        index = i;
    }
}
