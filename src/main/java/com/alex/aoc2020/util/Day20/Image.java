package com.alex.aoc2020.util.Day20;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Image {
    private Tile[][] tileArr;
    private final int size;
    private final Map<Long, Tile> tileMap = new HashMap<>();
    private char[][] pixelArr;
    private int pixelArrSize;

    public Image(List<Tile> tiles) {
        fillMap(tiles);
        countFillBorders();
        setTileBorders();
        size = (int) Math.sqrt(tiles.size());
    }

    private void fillMap(List<Tile> tiles) {
        for (Tile t : tiles) {
            tileMap.put(t.getId(), t);
        }
    }

    private void countFillBorders() {
        for (long id1 : tileMap.keySet()) {
            for (long id2 : tileMap.keySet()) {
                if (!(id1 == id2)) {
                    testIfShareBorder(tileMap.get(id1), tileMap.get(id2));
                }
            }
        }
    }

    private void testIfShareBorder(Tile t1, Tile t2) {
        String s1;
        String s2;
        for (int i = 0; i < 4; i++) {
            s1 = t1.getBorders().get(i);
            for (int j = 0; j < 4; j++) {
                s2 = t2.getBorders().get(j);
                if (s1.equals(s2)) {
                    t1.addNeighbour(t2.getId(), i, j, false);
                } else if (new StringBuilder(s1).reverse().toString().equals(s2)) {
                    t1.addNeighbour(t2.getId(), i, j, true);
                }
            }
        }
    }

    private void setTileBorders() {
        for (long key : tileMap.keySet())
            for (int borderId = 0; borderId < 4; borderId++)
                if (tileMap.get(key).getBorderDescriptionMap().get(borderId) == null)
                    tileMap.get(key).setSideAsImageEdge(borderId);
    }

    public String getNeighboursString() {
        StringBuilder sb = new StringBuilder();
        for (long id : tileMap.keySet()) {
            sb.append(String.format("id: %d -> %d neighbours.\n", id, tileMap.get(id).getNeighbours().size()));
        }

        return sb.toString();
    }

    public Image removeTileBorders() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tileArr[i][j].removeBorder();
            }
        }
        generatePixelArr();
        return this;
    }

    public long getCornerIdProduct() {
        return tileMap.keySet().stream()
                .map(tileMap::get)
                .filter(t -> t.getNeighbours().size() == 2)
                .map(Tile::getId)
                .reduce((long) 1, (a, b) -> a * b);
    }

    public Tile getTileById(long id) {
        return tileMap.get(id);
    }

    public Map<Long, Tile> getTileMap() {
        return tileMap;
    }

    public void arrange() throws Exception {
        placeTilesByNeighbours();
        Tile[][] arrangeTileArr = new Tile[size][size];
        if (!alignTilesBackTracking(arrangeTileArr, 0, 0, tileArr))
            throw new Exception("Could not align grid.");
        tileArr = copyArray(arrangeTileArr);
        generatePixelArr();
    }

    private void generatePixelArr() {
        int tileSize = tileArr[0][0].getSize();
        int n = size * tileSize;
        pixelArrSize = n;
        pixelArr = new char[n][n];
        List<String> listOfRows = new ArrayList<>();

        for (int j = 0; j < n; j++) {
            int internalTileRow = j % tileSize;
            int tileRow = j / tileSize;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                sb.append(new String(tileArr[tileRow][i].getPixelArr()[internalTileRow]));
            }
            listOfRows.add(sb.toString());
        }

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                pixelArr[row][col] = listOfRows.get(row).charAt(col);
            }
        }

    }

    private void placeTilesByNeighbours() {
        // Places all the tiles in the grid based on their neighbours.

        tileArr = new Tile[size][size];
        List<Long> usedTileIds = new ArrayList<>();

        // Place first one, a corner.
        tileArr[0][0] =  tileMap.keySet().stream()
                .map(tileMap::get)
                .filter(t -> t.getNeighbours().size() == 2).collect(Collectors.toList()).get(0);
        usedTileIds.add(tileArr[0][0].getId());

        // Place the first one's two neighbours.
        tileArr[0][1] = tileMap.get(new ArrayList<>(tileArr[0][0].getNeighbours()).get(0));
        tileArr[1][0] = tileMap.get(new ArrayList<>(tileArr[0][0].getNeighbours()).get(1));
        usedTileIds.add(tileArr[0][1].getId());
        usedTileIds.add(tileArr[1][0].getId());


        // Go along top adding neighbours
        int nNeighbours;
        Tile t;
        for (int i = 2; i < size; i++) {
            nNeighbours = i == size-1 ? 2 : 3;

            int finalNNeighbours = nNeighbours;
            t = tileMap.get(tileArr[0][i-1].getNeighbours().stream().filter(id -> (tileMap.get(id).getNeighbours().size() == finalNNeighbours) && !usedTileIds.contains(id)).collect(Collectors.toList()).get(0));
            tileArr[0][i] = t;
            usedTileIds.add(t.getId());
        }

        // Go along side adding neighbours
        for (int i = 2; i < size; i++) {
            nNeighbours = i == size-1 ? 2 : 3;

            int finalNNeighbours = nNeighbours;
            t = tileMap.get(tileArr[i-1][0].getNeighbours().stream().filter(id -> (tileMap.get(id).getNeighbours().size() == finalNNeighbours) && !usedTileIds.contains(id)).collect(Collectors.toList()).get(0));
            tileArr[i][0] = t;
            usedTileIds.add(t.getId());

        }

        // Fill in the rest via common neighbours
        for (int i = 1; i < size; i++) {
            for (int j = 1; j < size; j++) {
                t = getCommonNeighbourTo(tileArr[i-1][j].getId(), tileArr[i][j-1].getId(), usedTileIds);
                tileArr[i][j] = t;
                usedTileIds.add(t.getId());
            }
        }

    }

    private Tile getCommonNeighbourTo(long tid1, long tid2, List<Long> usedTiles) {
        Tile t1 = tileMap.get(tid1);
        Tile t2 = tileMap.get(tid2);

        Set<Long> commonNeighbour = new HashSet<>(t1.getNeighbours());
        commonNeighbour.retainAll(t2.getNeighbours());
        commonNeighbour = commonNeighbour.stream().filter(id -> !usedTiles.contains(id)).collect(Collectors.toSet());

        return tileMap.get(new ArrayList<>(commonNeighbour).get(0));
    }

    private boolean alignTilesBackTracking(Tile[][] currentSolution, int row, int col, Tile[][] originalArr) {

        // If we've finished.
        if (row == size-1 && col == size) {
            return true;
        }

        // Check col, row index.
        if (col == size) {
            row++;
            col = 0;
        }
        System.out.println(String.format("Backtracking at row: %d, col: %d", row, col));


        // Set this tile to it's original value.
        currentSolution[row][col] = originalArr[row][col];

        // Loop through the various possible operations.
        for (int orderNumber = 0; orderNumber < 2; orderNumber++) {
            for (int rotNumber = 0; rotNumber < 4; rotNumber++) {
                for (int flipNumber = 0; flipNumber < 3; flipNumber++) {

                    switch (orderNumber) {
                        case 0:
                            currentSolution[row][col].rotate(rotNumber);
                            doFlipType(flipNumber, currentSolution, row, col);
                            break;
                        case 1:
                            doFlipType(flipNumber, currentSolution, row, col);
                            currentSolution[row][col].rotate(rotNumber);
                            break;
                    }

                    if (tilePositionIsValid(currentSolution, row, col)) {

                        if (alignTilesBackTracking(currentSolution, row, col + 1, originalArr))
                            return true;
                    }

                    currentSolution[row][col] = originalArr[row][col];

                }
            }
        }

        return false;
    }

    private void doFlipType(int flipNumber, Tile[][] currentSolution, int row, int col) {
        switch (flipNumber) {
            case 0:
                break;
            case 1:
                currentSolution[row][col].fliplr();
                break;
            case 2:
                currentSolution[row][col].fliplr();
                currentSolution[row][col].flipud();
                break;
        }
    }

    public void doFlipTypeSelf(int flipNumber) {
        switch (flipNumber) {
            case 0:
                break;
            case 1:
                fliplr();
                break;
            case 2:
                flipud();
                break;
            case 3:
                fliplr();
                flipud();
                break;
        }
    }

    private boolean tilePositionIsValid(Tile[][] currentSolution, int row, int col) {
        // Horrendous function to see if tile is valid when placed.
        Tile t = currentSolution[row][col];

        if (t.getNeighbours().size() == 2) {
            if (row == 0 && col == 0) {
                return t.getBorderString(0).equals("*".repeat(t.getSize())) && t.getBorderString(3).contains("*".repeat(t.getSize()));
            } else if (row == 0) {
                return t.getBorderString(0).equals("*".repeat(t.getSize()))
                        && t.getBorderString(1).equals("*".repeat(t.getSize()))
                        && currentSolution[row][col-1].getBorderString(1).equals(t.getBorderString(3));
            }else if (col == 0) {
                return t.getBorderString(3).equals("*".repeat(t.getSize()))
                        && t.getBorderString(2).equals("*".repeat(t.getSize()))
                        && currentSolution[row-1][col].getBorderString(2).equals(t.getBorderString(0));
            } else {
                return t.getBorderString(1).equals("*".repeat(t.getSize()))
                        && t.getBorderString(2).equals("*".repeat(t.getSize()))
                        && currentSolution[row-1][col].getBorderString(2).equals(t.getBorderString(0))
                        && currentSolution[row][col-1].getBorderString(1).equals(t.getBorderString(3));
            }

        } else if (t.getNeighbours().size() == 3) {
            if (row == 0) {
                return t.getBorderString(0).equals("*".repeat(t.getSize()))
                        && currentSolution[row][col-1].getBorderString(1).equals(t.getBorderString(3));
            } else if (col == 0) {
                return t.getBorderString(3).equals("*".repeat(t.getSize()))
                        && currentSolution[row-1][col].getBorderString(2).equals(t.getBorderString(0));
            }else if (col == size-1) {
                return t.getBorderString(1).equals("*".repeat(t.getSize()))
                        && currentSolution[row][col-1].getBorderString(1).equals(t.getBorderString(3))
                        && currentSolution[row-1][col].getBorderString(2).equals(t.getBorderString(0));
            } else {
                return t.getBorderString(2).equals("*".repeat(t.getSize()))
                        && currentSolution[row][col-1].getBorderString(1).equals(t.getBorderString(3))
                        && currentSolution[row-1][col].getBorderString(2).equals(t.getBorderString(0));
            }

        } else {
            return currentSolution[row - 1][col].getBorderString(2).equals(t.getBorderString(0))
                    && currentSolution[row][col - 1].getBorderString(1).equals(t.getBorderString(3));

        }
    }


    public Tile getTileAtIndex(int i, int j) {
        return tileArr[i][j];
    }

    public String getGridLayout() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i ++) {
            for (int j = 0; j < size; j++) {
                sb.append(String.format("%d ", tileArr[j][i].getId()));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private Tile[][] copyArray(Tile[][] arr) {
        Tile[][] copy = new Tile[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(tileArr[i], 0, copy[i], 0, size);
        }
        return copy;
    }

    public void rotate(int nRot) {
        char[][] rot = new char[size][size];
        for (int n = 0; n < nRot; n++) {
            rot = new char[pixelArrSize][pixelArrSize];

            for (int row = 0; row < pixelArrSize; row++) {
                for (int col = 0; col < pixelArrSize; col++) {
                    rot[row][col] = pixelArr[pixelArrSize-col-1][row];
                }
            }
            pixelArr = rot;

        }
    }

    public void flipud() {
        char[][] flip = new char[pixelArrSize][pixelArrSize];
        for (int i = 0; i < pixelArrSize; ++i) {
            System.arraycopy(pixelArr[pixelArrSize - i - 1], 0, flip[i], 0, pixelArrSize);
        }
        pixelArr = flip;
    }

    public void fliplr() {
        char[][] flip = new char[pixelArrSize][pixelArrSize];
        for (int i = 0; i < pixelArrSize; ++i) {
            for (int j = 0; j < pixelArrSize; ++j) {
                flip[i][j] = pixelArr[i][pixelArrSize-j-1];
            }
        }
        pixelArr = flip;
    }

    private List<String> getRowStrings() {
        StringBuilder sb;
        List<String> rowStrings = new ArrayList<>();

        for (int row = 0; row < pixelArrSize; row++) {
            sb = new StringBuilder();
            for (int col = 0; col < pixelArrSize; col++) {
                sb.append(pixelArr[row][col]);
            }
            rowStrings.add(sb.toString());
        }
        return rowStrings;
    }

    public int searchForSeaMonsters() {
        String seaMonsterRegex1 = "([.#O]+)?#[.#O]{4}##[.#O]{4}##[.#O]{4}###[.#O]+";
        String seaMonsterRegexAbove = "[.#O]{VAR}#[.#O]+";
        String seaMonsterRegexBelow = "[.#O]{VAR}#[.#O]{2}#[.#O]{2}#[.#O]{2}#[.#O]{2}#[.#O]{2}#[.#O]+";
        Set<List<Integer>> seaMonsterStartingIndices = new HashSet<>();
        List<String> stringRowList;

        int count = 0;
        Pattern pattern;
        Matcher matcher;
        String group;


        // Loop through the various possible operations.
        for (int orderNumber = 0; orderNumber < 2; orderNumber++) {
            for (int flipNumber = 0; flipNumber < 4; flipNumber++) {
                for (int rotNumber = 0; rotNumber < 4; rotNumber++) {

                    switch (orderNumber) {
                        case 0:
                            rotate(rotNumber);
                            doFlipTypeSelf(flipNumber);
                            break;
                        case 1:
                            doFlipTypeSelf(flipNumber);
                            rotate(rotNumber);
                            break;
                    }
                    // Loop twice to look for more than one per row.
                    for (int i = 0; i < 2; i++) {
                        stringRowList = getRowStrings();

                        // Go through all the rows looking for the sea monster central row.
                        for (int row = 1; row < pixelArrSize - 1; row++) {
                            pattern = Pattern.compile(seaMonsterRegex1);
                            matcher = pattern.matcher(stringRowList.get(row));

                            if (matcher.find()) {
                                group = matcher.group(1) == null ? "" : matcher.group(1);
                                String smRA = seaMonsterRegexAbove.replace("VAR", Integer.toString(group.length() + 18));
                                String smRB = seaMonsterRegexBelow.replace("VAR", Integer.toString(group.length() + 1));
                                if (stringRowList.get(row - 1).matches(smRA) && stringRowList.get(row + 1).matches(smRB)) {
                                    count += 1;
                                    seaMonsterStartingIndices.add(Arrays.asList(row, group.length()));

                                }
                            }
                        }
                        markMonster(seaMonsterStartingIndices);
                        seaMonsterStartingIndices = new HashSet<>();
                    }
                    if (count > 0)
                        break;
                }
                if (count > 0)
                    break;
            }
            if (count > 0)
                break;
        }

        return countHashes();

    }

    private void markMonster(Set<List<Integer>> seaMonsterStartingIndices) {
        for (List<Integer> l : seaMonsterStartingIndices) {
            pixelArr[l.get(0)][l.get(1)]      = 'O';
            pixelArr[l.get(0)][l.get(1)+5]    = 'O';
            pixelArr[l.get(0)][l.get(1)+6]    = 'O';
            pixelArr[l.get(0)][l.get(1)+11]   = 'O';
            pixelArr[l.get(0)][l.get(1)+12]   = 'O';
            pixelArr[l.get(0)][l.get(1)+17]   = 'O';
            pixelArr[l.get(0)][l.get(1)+18]   = 'O';
            pixelArr[l.get(0)][l.get(1)+19]   = 'O';
            pixelArr[l.get(0)-1][l.get(1)+18] = 'O';
            pixelArr[l.get(0)+1][l.get(1)+1]  = 'O';
            pixelArr[l.get(0)+1][l.get(1)+4]  = 'O';
            pixelArr[l.get(0)+1][l.get(1)+7]  = 'O';
            pixelArr[l.get(0)+1][l.get(1)+10] = 'O';
            pixelArr[l.get(0)+1][l.get(1)+13] = 'O';
            pixelArr[l.get(0)+1][l.get(1)+16] = 'O';
        }

    }

    private int countHashes() {
        int count = 0;
        for(int i = 0; i < pixelArrSize; i++) {
            for (int j = 0; j < pixelArrSize; j++) {
                if (pixelArr[i][j] == '#')
                    count++;
            }
        }
        return count;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("    ------------IMAGE-------------    ");
        for (int j = 0; j < pixelArrSize; j++) {
            if (j % (tileArr[0][0].getSize()) == 0) {
                sb.append("\n");
            }
            for (int i = 0; i < pixelArrSize; i++) {
                if (i % (tileArr[0][0].getSize()) == 0) {
                    sb.append("   ");
                }
                sb.append(pixelArr[j][i]);
            }
            sb.append("\n");
        }


        return sb.toString();
    }
}
