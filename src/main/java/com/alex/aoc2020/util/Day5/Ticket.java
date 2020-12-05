package com.alex.aoc2020.util.Day5;

public class Ticket {
    private final String rowId;
    private final String colId;
    private final String seatStr;
    private final int seatId;
    private final int col;
    private final int row;

    public Ticket(String seatStr_) {
        seatStr = seatStr_;
        rowId = seatStr.substring(0, 7).replace('F', 'L').replace('B', 'R');
        colId = seatStr.substring(7);

        row = getRowValue();
        col = getColValue();

        seatId = row * 8 + col;
    }

    public String getRowId() {
        return rowId;
    }

    public String getColId() {
        return colId;
    }

    public String getSeatStr() {
        return seatStr;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getSeatId() {
        return seatId;
    }

    private int binarySearchSeat(String s, int L, int R) {
        int i = 0;
        int m;
        while (L != R) {
            m = (L + R) / 2;
            //System.out.println(String.format("L: %d R: %d", L, R));
            if (s.charAt(i) == 'R') {
                L = m + 1;
            } else if (s.charAt(i) == 'L') {
                R = m;
            }
            i += 1;
        }
        return L;
    }

    private int getColValue() {
        return binarySearchSeat(colId, 0, 7);
    }

    private int getRowValue() {
        return binarySearchSeat(rowId, 0, 127);
    }


}
