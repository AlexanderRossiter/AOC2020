package com.alex.aoc2020.util.Day1;

import java.util.List;

public class TobogganTreeMap {
    static List<String> map;
    public int periodicLength;
    public int courseLength;

    public TobogganTreeMap(List<String> map_) {
        map = map_;
        periodicLength = map.get(1).length();
        courseLength = map.size();
    }

    public boolean tileIsTree(int i, int j) {
        return map.get(j).charAt(i) == '#';
    }

    public int checkSlope(int iIncrement, int jIncrement) {
        int count = 0;
        int i = 0;
        int j = 0;
        while (j < courseLength) {
            if (tileIsTree(i % periodicLength, j)) {
                count += 1;
            }
            i += iIncrement;
            j += jIncrement;
        }

        return count;
    }
}

