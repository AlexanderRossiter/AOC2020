package com.alex.aoc2020.util.Day17;

import java.util.Objects;

public class ConwayCube {
    private final int x;
    private final int y;
    private final int z;
    private final int w;
    private boolean isActive = false;
    private int surroundingActiveNodes = -1;


    public ConwayCube(int x_, int y_, int z_, int w_, boolean active) {
        x = x_;
        y = y_;
        z = z_;
        w = w_;
        isActive = active;
    }

    public ConwayCube(int x_, int y_, int z_, boolean active) {
        this(x_, y_, z_, 0, active);
    }

    public ConwayCube(int x_, int y_, int z_, int w_) {
        this(x_, y_, z_, w_, false);
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


    public void setSurroundingActiveNodes(int n) {
        surroundingActiveNodes = n;
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

    public int getW() {
        return w;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof ConwayCube)) {
            return false;
        }

        ConwayCube c = (ConwayCube) o;

        return x == c.getX() && y == c.getY() && z == c.getZ() && w == c.getW();
    }



}
