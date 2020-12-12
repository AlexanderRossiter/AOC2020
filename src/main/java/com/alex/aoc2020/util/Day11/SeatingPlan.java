package com.alex.aoc2020.util.Day11;

import java.util.Arrays;
import java.util.List;

public class SeatingPlan {
    private char[][] seatingPlan;
    private int nCol;
    private int nRow;

    public SeatingPlan(List<String> seatingPlanList) {
        parseSeatingPlanToCharArray(seatingPlanList);
    }

    private void parseSeatingPlanToCharArray(List<String> seatingPlanList) {
        nRow = seatingPlanList.size();
        nCol = seatingPlanList.get(0).length();
        seatingPlan = new char[nRow][nCol];

        for (int i = 0; i < nRow; i++) {
            for (int j = 0; j < nCol; j++) {
                seatingPlan[i][j] = seatingPlanList.get(i).charAt(j);
            }
        }
    }

    public void runSeatingSimulation(int rule) {
        char[][] previousSeatingPlan;
        do {
            previousSeatingPlan = seatingPlan;
            seatingPlan = runSeatingIteration(seatingPlan, rule);
        } while (!seatingPlansAreTheSame(seatingPlan, previousSeatingPlan));
    }

    private char[][] runSeatingIteration(char[][] currentSeatingPlan) {
        return runSeatingIteration(currentSeatingPlan, 1);

    }

    private char[][] runSeatingIteration(char[][] currentSeatingPlan, int rule) {
        char[][] nextSeatingPlan = new char[nRow][nCol];

        // Do internals
        for (int i = 0; i < nRow ; i++) {
            for (int j = 0; j < nCol; j++) {
                if (rule == 1) {
                    nextSeatingPlan[i][j] = applyRuleForSeat(currentSeatingPlan, i, j);
                } else {
                    nextSeatingPlan[i][j] = applyRuleForSeat2(currentSeatingPlan, i, j);
                }
            }
        }

        return nextSeatingPlan;

    }

    private boolean seatIsEmpty(char seat) {
        return (seat == 'L');
    }

    private boolean seatIsOccupied(char seat) {
        return seat == '#';
    }

    private boolean seatIsFloor(char seat) {
        return seat == '.';
    }

    private char applyRuleForSeat(char[][] seatingPlan, int i, int j) {
        if (seatIsEmpty(seatingPlan[i][j]) && numberSurroundingOccupiedSeats(seatingPlan, i, j) == 0) {
            return '#';
        } else if (!seatIsEmpty(seatingPlan[i][j]) && !seatIsFloor(seatingPlan[i][j])
                && numberSurroundingOccupiedSeats(seatingPlan, i, j) >= 4) {
            return 'L';
        } else {
            return seatingPlan[i][j];
        }
    }

    private char applyRuleForSeat2(char[][] seatingPlan, int i, int j) {
        if (seatIsEmpty(seatingPlan[i][j]) && numberVisibleOccupiedSeats(seatingPlan, i, j) == 0) {
            return '#';
        } else if (!seatIsEmpty(seatingPlan[i][j]) && !seatIsFloor(seatingPlan[i][j])
                && numberVisibleOccupiedSeats(seatingPlan, i, j) >= 5) {
            return 'L';
        } else {
            return seatingPlan[i][j];
        }
    }

    private int numberSurroundingOccupiedSeats(char[][] seatingPlan, int i, int j) {
        int count = 0;


        if (indexIsInBounds(i-1,j) && seatIsOccupied(seatingPlan[i-1][j])){
            count += 1;
        }

        if (indexIsInBounds(i,j-1) && seatIsOccupied(seatingPlan[i][j-1])){
            count += 1;
        }

        if (indexIsInBounds(i+1,j) && seatIsOccupied(seatingPlan[i+1][j])){
            count += 1;
        }

        if (indexIsInBounds(i,j+1) && seatIsOccupied(seatingPlan[i][j+1])){
            count += 1;
        }

        if (indexIsInBounds(i-1,j-1) && seatIsOccupied(seatingPlan[i-1][j-1])){
            count += 1;
        }

        if (indexIsInBounds(i-1,j+1) && seatIsOccupied(seatingPlan[i-1][j+1])){
            count += 1;
        }

        if (indexIsInBounds(i+1,j+1) && seatIsOccupied(seatingPlan[i+1][j+1])){
            count += 1;
        }

        if (indexIsInBounds(i+1,j-1) && seatIsOccupied(seatingPlan[i+1][j-1])){
            count += 1;
        }


        return count;
    }

    private int numberVisibleOccupiedSeats(char[][] seatingPlan, int i, int j) {
        int count = 0;

        count += findIfNextVisibleSeatInDirectionIsOccupied(i, j-1, 0, -1);
        count += findIfNextVisibleSeatInDirectionIsOccupied(i, j+1, 0, 1);
        count += findIfNextVisibleSeatInDirectionIsOccupied(i+1, j, 1, 0);
        count += findIfNextVisibleSeatInDirectionIsOccupied(i-1, j, -1, 0);
        count += findIfNextVisibleSeatInDirectionIsOccupied(i+1, j+1, 1, 1);
        count += findIfNextVisibleSeatInDirectionIsOccupied(i+1, j-1, 1, -1);
        count += findIfNextVisibleSeatInDirectionIsOccupied(i-1, j+1, -1, 1);
        count += findIfNextVisibleSeatInDirectionIsOccupied(i-1, j-1, -1, -1);

        return count;
    }

    private int findIfNextVisibleSeatInDirectionIsOccupied(int startingI, int startingJ, int iIncrement, int jIncrement) {
        int idxI = startingI;
        int idxJ = startingJ;
        char seat;
        if (indexIsInBounds(idxI, idxJ)) {
            while (indexIsInBounds(idxI, idxJ)) {
                seat = seatingPlan[idxI][idxJ];
                if (!(seat == '.')) {
                    break;
                }
                idxI += iIncrement;
                idxJ += jIncrement;
            }


            if (indexIsInBounds(idxI, idxJ) && seatIsOccupied(seatingPlan[idxI][idxJ])) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    private boolean seatingPlansAreTheSame(char[][] seatingPlanA, char[][] seatingPlanB) {
        for (int i = 0; i < nRow; i++) {
            for (int j = 0; j < nCol; j++) {
                if (!(seatingPlanA[i][j] == seatingPlanB[i][j])){
                    return false;
                }
            }
        }
        return true;
    }

    public int countNumberOccupied() {
        int count = 0;
        for (int i = 0; i < nRow; i++) {
            for (int j = 0; j < nCol; j++) {
                if (seatIsOccupied(seatingPlan[i][j])) {
                    count += 1;
                }
            }
        }

        return count;
    }

    private boolean indexIsInBounds(int i, int j) {
        return ((i >= 0) && (i < nRow) && (j >= 0) && (j < nCol));
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nRow; i++) {
            for (int j = 0; j < nCol; j++) {
                sb.append(seatingPlan[i][j]);
            }
            sb.append('\n');
        }

        return sb.toString();
    }

}
