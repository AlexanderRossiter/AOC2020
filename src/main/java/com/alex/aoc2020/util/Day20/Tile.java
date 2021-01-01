package com.alex.aoc2020.util.Day20;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tile {
    private char[][] pixelArr;
    private int size;
    private final List<String> borders = new ArrayList<>();
    private long id;
    private String asString;
    private final Set<Long> neighbours = new HashSet<>();
    private Map<Integer, BorderDescription> borderDescriptionMap = new HashMap<>();

    public Tile(String imageString) {
        try {
            parseImageString(imageString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        generateBoundaryStrings();
    }

    private void parseImageString(String imageString) throws Exception {
        List<String> imageStringList = new ArrayList<>(Arrays.asList(imageString.split("\n")));
        Pattern pattern = Pattern.compile("Tile (\\d+):");
        Matcher matcher = pattern.matcher(imageStringList.get(0));
        if (matcher.find()) {
            id = Long.parseLong(matcher.group(1));
        } else {
            throw new Exception("Bad image.");
        }
        imageStringList.remove(0);

        size = imageStringList.size();
        pixelArr = new char[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                pixelArr[i][j] = imageStringList.get(j).charAt(i);
            }
        }

    }

    private void generateBoundaryStrings() {
        StringBuilder sb = new StringBuilder();
        // 0
        for (int i = 0; i < size; i++) {
            sb.append(pixelArr[0][i]);
        }
        borders.add(sb.toString());

        // 1
        sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(pixelArr[i][size-1]);
        }
        borders.add(sb.toString());

        // 2
        sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(pixelArr[size-1][i]);
        }
        borders.add(sb.toString());

        // 3
        sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(pixelArr[i][0]);
        }
        borders.add(sb.toString());
    }

    public void rotate(int nRot) {
        char[][] rot = new char[size][size];
        for (int n = 0; n < nRot; n++) {
            rot = new char[size][size];

            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {
                    rot[i][j] = pixelArr[j][size-i-1];
                }
            }
            pixelArr = rot;

        }
    }

    public void fliplr() {
        char[][] flip = new char[size][size];
        for (int i = 0; i < size; ++i) {
            System.arraycopy(pixelArr[size - i - 1], 0, flip[i], 0, size);
        }
        pixelArr = flip;
    }

    public void flipud() {
        char[][] flip = new char[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                flip[i][j] = pixelArr[i][size-j-1];
            }
        }
        pixelArr = flip;
    }

    public void removeBorder() {
        char[][] tmpPixelArr = new char[size-2][size-2];

        for (int i = 1; i < size-1; i++) {
            System.arraycopy(pixelArr[i], 1, tmpPixelArr[i - 1], 0, size - 1 - 1);
        }
        size = size-2;
        pixelArr = new char[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(tmpPixelArr[i], 0, pixelArr[i], 0, size);
        }
    }



    public void addNeighbour(long tid, int borderId1, int borderId2, boolean flipped) {
        neighbours.add(tid);
        borderDescriptionMap.put(borderId1, new BorderDescription(tid, borderId2, flipped));
    }

    public long getId() {
        return id;
    }

    public List<String> getBorders() {
        return borders;
    }

    public Set<Long> getNeighbours() {
        return neighbours;
    }

    public Map<Integer, BorderDescription> getBorderDescriptionMap() {
        return borderDescriptionMap;
    }

    public int getSize() {
        return size;
    }

    public String getBorderString(int borderId) {
        StringBuilder sb = new StringBuilder();
        switch (borderId) {
            case 0:
                for (int i = 0; i < size; i++) {
                    sb.append(pixelArr[0][i]);
                }
                break;
            case 1:
                for (int i = 0; i < size; i++) {
                    sb.append(pixelArr[i][size-1]);
                }
                break;
            case 2:
                for (int i = 0; i < size; i++) {
                    sb.append(pixelArr[size-1][i]);
                }
                break;
            case 3:
                for (int i = 0; i < size; i++) {
                    sb.append(pixelArr[i][0]);
                }
                break;
        }
        return sb.toString();
    }

    public void setSideAsImageEdge(int borderId) {
        switch (borderId) {
            case 0:
                for (int i = 0; i < size; i++) {
                    pixelArr[0][i] = '*';
                }
                break;
            case 1:
                for (int i = 0; i < size; i++) {
                    pixelArr[i][size-1] = '*';
                }
                break;
            case 2:
                for (int i = 0; i < size; i++) {
                    pixelArr[size-1][i] = '*';
                }
                break;
            case 3:
                for (int i = 0; i < size; i++) {
                    pixelArr[i][0] = '*';
                }
                break;
        }
    }

    public char[][] getPixelArr() {
        return pixelArr;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Tile Id: %d\n", id));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(pixelArr[j][i]);
            }
            sb.append("\n");
        }

        return sb.toString();
    }


}
