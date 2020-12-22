package com.alex.aoc2020.util.Day17;

import java.util.List;

public class ConwayCube {
    private final int x;
    private final int y;
    private final int z;
    private boolean isActive = false;
    private int surroundingActiveNodes = -1;


    public ConwayCube(int x_, int y_, int z_, boolean active) {
        x = x_;
        y = y_;
        z = z_;
        isActive = active;
    }

    public ConwayCube(int x_, int y_, int z_) {
        this(x_, y_, z_, false);
    }

    public boolean cubeIsAtPosition(int x_, int y_, int z_) {
        return (x == x_) && (y == y_) && (z == z_);
    }

    public boolean isActive() {
        return isActive;
    }

    public void activate() {
        isActive = true;
    }

    public void deactivate() {
        isActive = false;
    }

    public void setState(boolean state) {
        isActive = state;
    }

    public void update() {
        if (surroundingActiveNodes == 3 && !isActive) {
            activate();
        } else if ((surroundingActiveNodes == 2 || surroundingActiveNodes == 3) && isActive) {
            activate();
        } else {
            deactivate();
        }
        surroundingActiveNodes = -1;
    }

    public void countSurroundingActiveCubes(PocketDimension pd) {
        int count = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    if (!(i == 0 && j == 0 && k == 0)) {
                        count += pd.getConwayCubeAtLocation(x+i,y+j,z+k).isActive() ? 1 : 0;
                    }
                }
            }
        }
        surroundingActiveNodes = count;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
