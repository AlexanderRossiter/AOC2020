package com.alex.aoc2020.util.Day17;

import java.util.List;

public class PocketDimension4D extends PocketDimension {

    public PocketDimension4D(List<String> initialFlatRegionList) {
        super(initialFlatRegionList);
    }

    @Override
    protected void padConwayCubeSet() {
        int minX = conwayCubeSet.stream().map(ConwayCube::getX).min(Integer::compare).get()-1;
        int minY = conwayCubeSet.stream().map(ConwayCube::getY).min(Integer::compare).get()-1;
        int minZ = conwayCubeSet.stream().map(ConwayCube::getZ).min(Integer::compare).get()-1;
        int minW = conwayCubeSet.stream().map(ConwayCube::getW).min(Integer::compare).get()-1;


        int maxX = conwayCubeSet.stream().map(ConwayCube::getX).max(Integer::compare).get()+1;
        int maxY = conwayCubeSet.stream().map(ConwayCube::getY).max(Integer::compare).get()+1;
        int maxZ = conwayCubeSet.stream().map(ConwayCube::getZ).max(Integer::compare).get()+1;
        int maxW = conwayCubeSet.stream().map(ConwayCube::getW).max(Integer::compare).get()+1;


        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                for (int k = minZ; k <= maxZ; k++) {
                    for (int w = minW; w <= maxW; w++) {
                        ConwayCube c = new ConwayCube(i,j,k,w);
                        conwayCubeSet.add(c);

                    }
                }
            }
        }
    }

    @Override
    protected void countSurroundingActiveCubes(ConwayCube c) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    for (int w = -1; w <= 1; w++) {
                        if (!(i == 0 && j == 0 && k == 0 && w == 0)) {
                            count += checkIfCubeAtLocationIsActive(c.getX() + i, c.getY() + j, c.getZ() + k, c.getW() + w) ? 1 : 0;
                        }
                    }
                }
            }
        }
        c.setSurroundingActiveNodes(count);
    }

    public boolean checkIfCubeAtLocationIsActive(int x, int y, int z, int w) {
        ConwayCube testCube = new ConwayCube(x, y, z, w);

        if (conwayCubeConwayCubeHashMap.containsKey(testCube)) {
            return conwayCubeConwayCubeHashMap.get(testCube).isActive();
        } else {
            conwayCubeSet.add(testCube);
        }

        return false;

    }


}
