package com.alex.aoc2020.util.Day20;

public class BorderDescription {
    public boolean flipped;
    public long tileId;
    public int borderId;

    public BorderDescription(long tileId_, int borderId_, boolean flipped_) {
        tileId = tileId_;
        borderId = borderId_;
        flipped = flipped_;
    }

    @Override
    public String toString() {
        return String.format("(tileId: %d, borderId: %d, flipped: %b)", tileId, borderId, flipped);
    }
}
