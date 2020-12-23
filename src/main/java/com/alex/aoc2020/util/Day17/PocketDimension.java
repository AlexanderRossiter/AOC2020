package com.alex.aoc2020.util.Day17;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

abstract class PocketDimension {
    protected Set<ConwayCube> conwayCubeSet = new HashSet<>();
    protected HashMap<ConwayCube, ConwayCube> conwayCubeConwayCubeHashMap = new HashMap<>();


    public PocketDimension(List<String> initialFlatRegionList) {
        try {
            parseInitialFlatRegionList(initialFlatRegionList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void parseInitialFlatRegionList(List<String> initialFlatRegionList) throws Exception {
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
            System.out.println(String.format("Cycle number %d", i));
            runCycle();
        }
    }

    protected void runCycle() {
        padConwayCubeSet();
        for (ConwayCube c : conwayCubeSet) {
            conwayCubeConwayCubeHashMap.put(c, c);
        }

        Set<ConwayCube> thisCycleConwayCubeSet = new HashSet<>(conwayCubeSet);
        for (ConwayCube c : thisCycleConwayCubeSet) {
            countSurroundingActiveCubes(c);
        }
        thisCycleConwayCubeSet.addAll(conwayCubeSet);
        conwayCubeSet = new HashSet<>(thisCycleConwayCubeSet);

        for (ConwayCube c : conwayCubeSet) {
            c.update();
        }

    }

    public int numberActiveCubes() {
        return conwayCubeSet.stream()
                .filter(ConwayCube::isActive)
                .collect(Collectors.toSet())
                .size();
    }

    abstract void padConwayCubeSet();

    abstract void countSurroundingActiveCubes(ConwayCube c);
}
