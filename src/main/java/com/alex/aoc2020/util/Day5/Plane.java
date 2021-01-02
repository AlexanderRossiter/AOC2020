package com.alex.aoc2020.util.Day5;

import java.util.Arrays;
import java.util.List;

public class Plane {
    private final List<Ticket> ticketList;
    private final int nCol = 8;
    private final int nRow = 128;
    private final boolean[][] seatFull = new boolean[nRow][nCol];

    public Plane(List<Ticket> ticketList_) {
        ticketList = ticketList_;

        fillSeatsFromTickets();
    }

    private void fillSeatsFromTickets() {
        for (Ticket t : ticketList) {
            seatFull[t.getRow()][t.getCol()] = true;
        }

    }

    public void printPlaneSeats(){
        System.out.println(Arrays.deepToString(seatFull));
    }

    private int getColFromSeatId(int seatId) {
        return seatId % nCol;
    }

    private int getRowFromSeatId(int seatId) {
        return seatId / nCol;
    }

    public int getMySeatId() {
        int i;
        int j;
        int mySeatID = -1;

        for (int seatId = 0; seatId < nRow * 8 - 1; seatId++) {
            i = seatId / nCol;
            j = seatId % nCol;
            if (!seatFull[i][j] &&
                    seatFull[getRowFromSeatId(seatId+1)][getColFromSeatId(seatId+1)] &&
                    seatFull[getRowFromSeatId(seatId-1)][getColFromSeatId(seatId+-1)]) {
                mySeatID = seatId;
                break;
            }
        }
        return mySeatID;
    }

}
