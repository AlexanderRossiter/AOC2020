package com.alex.aoc2020.util.Day17;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PocketDimension {
    private Set<ConwayCube> conwayCubeSet = new HashSet<>();

    public PocketDimension(List<String> initialFlatRegionList) {
        try {
            parseInitialFlatRegionList(initialFlatRegionList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseInitialFlatRegionList(List<String> initialFlatRegionList) throws Exception {
        int nCol = initialFlatRegionList.get(0).length();
        int nRow = initialFlatRegionList.size();

        for (int i = 0; i < nCol; i++) {
            for (int j = 0; j < nRow; j++) {
                if (initialFlatRegionList.get(j).charAt(i) == '.') {
                    conwayCubeSet.add(new ConwayCube(i, j, 0));
                } else if (initialFlatRegionList.get(j).charAt(i) == '#') {
                    conwayCubeSet.add(new ConwayCube(i, j, 0, true));
                } else {
                    throw new Exception("Invalid input");
                }
            }
        }
    }

    public void runNCycles(int n) {
        for (int i = 0; i < n; i++) {
            runCycle();
        }
    }

    private void runCycle() {
        for (ConwayCube c : conwayCubeSet) {
            c.countSurroundingActiveCubes(this);
        }

        for (ConwayCube c : conwayCubeSet) {
            c.update();
        }
    }

    public ConwayCube getConwayCubeAtLocation(int x, int y, int z) {
        ConwayCube cube = conwayCubeSet.stream()
                .filter(c -> c.getX() == x && c.getY() == y && c.getZ() == z)
                .collect(Collectors.toList()).get(0);
        if (cube == null) {
            cube = new ConwayCube(x, y, z);
            conwayCubeSet.add(cube);
        }
        return cube;
    }

    public int numberActiveCubes() {
        return conwayCubeSet.stream()
                .filter(ConwayCube::isActive)
                .collect(Collectors.toSet())
                .size();
    }
}
