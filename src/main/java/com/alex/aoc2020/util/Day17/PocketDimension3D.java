package com.alex.aoc2020.util.Day17;

import java.util.List;
import java.util.stream.Collectors;

public class PocketDimension3D extends PocketDimension{
    //protected Set<ConwayCube> conwayCubeSet = new HashSet<>();

    public PocketDimension3D(List<String> initialFlatRegionList) {
        super(initialFlatRegionList);
    }


    @Override
    protected void padConwayCubeSet() {
        int minX = conwayCubeSet.stream().map(ConwayCube::getX).min(Integer::compare).get()-1;
        int minY = conwayCubeSet.stream().map(ConwayCube::getY).min(Integer::compare).get()-1;
        int minZ = conwayCubeSet.stream().map(ConwayCube::getZ).min(Integer::compare).get()-1;


        int maxX = conwayCubeSet.stream().map(ConwayCube::getX).max(Integer::compare).get()+1;
        int maxY = conwayCubeSet.stream().map(ConwayCube::getY).max(Integer::compare).get()+1;
        int maxZ = conwayCubeSet.stream().map(ConwayCube::getZ).max(Integer::compare).get()+1;


        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                conwayCubeSet.add(new ConwayCube(i, j, maxZ));
            }
        }
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                conwayCubeSet.add(new ConwayCube(i, j, minZ));
            }
        }

        for (int i = minX; i <= maxX; i++) {
            for (int k = minZ+1; k <= maxZ-1; k++) {
                conwayCubeSet.add(new ConwayCube(i, minY, k));
            }
        }

        for (int i = minX; i <= maxX; i++) {
            for (int k = minZ+1; k <= maxZ-1; k++) {
                conwayCubeSet.add(new ConwayCube(i, maxY, k));
            }
        }

        for (int j = minY-1; j <= minY+1; j++) {
            for (int k = minZ+1; k <= maxZ-1; k++) {
                conwayCubeSet.add(new ConwayCube(minX, j, k));
            }
        }

        for (int j = minY-1; j <= minY+1; j++) {
            for (int k = minZ+1; k <= maxZ-1; k++) {
                conwayCubeSet.add(new ConwayCube(maxX, j, k));
            }
        }


    }

    @Override
    protected void countSurroundingActiveCubes(ConwayCube c) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    if (!(i == 0 && j == 0 && k == 0)) {
                        count += checkIfCubeAtLocationIsActive(c.getX()+i,c.getY()+j,c.getZ()+k) ? 1 : 0;
                    }
                }
            }
        }
        c.setSurroundingActiveNodes(count);
    }



    public boolean checkIfCubeAtLocationIsActive(int x, int y, int z) {
        ConwayCube testCube = new ConwayCube(x, y, z);

        if (conwayCubeConwayCubeHashMap.containsKey(testCube)) {
            return conwayCubeConwayCubeHashMap.get(testCube).isActive();
        } else {
            conwayCubeSet.add(testCube);
        }

        return false;

    }




}
