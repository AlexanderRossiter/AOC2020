package com.alex.aoc2020.util.Astar;

import java.util.ArrayList;
import java.util.List;

public class AStar {
    private List<AStarNode> openSet = new ArrayList<AStarNode>();
    private final List<AStarNode> closedSet = new ArrayList<AStarNode>();
    private final List<AStarNode> nodes = new ArrayList<AStarNode>();
    private AStarNode startingNode = new StartingAStarNode();
    private AStarNode finishingNode = new FinishingAStarNode();


    public Path findShortestPath(List<Double> h) {
        int length = 0;

        openSet.add(startingNode);

        List<AStarNode> cameFrom = new ArrayList<>();

        AStarNode current = new AStarNode();
        int currentIdx;
        while (openSet.size() > 0) {
            current = getNodeWithLowestFScore(openSet);
            if (current.isStart) {
                return reconstructPath(cameFrom, current);
            }
            openSet = removeNodeFromList(openSet, current);
        }

        return reconstructPath(cameFrom, current);

    }

    private Path reconstructPath(List<AStarNode> cameFrom, AStarNode current) {
        return new Path();
    }

    private AStarNode getNodeWithLowestFScore(List<AStarNode> list) {
        double score = Double.POSITIVE_INFINITY;
        AStarNode winner = new AStarNode();
        for (AStarNode aStarNode : list) {
            if (aStarNode.fScore < score) {
                score = aStarNode.fScore;
                winner = aStarNode;
            }
        }
        return winner;
    }

    private List<AStarNode> removeNodeFromList(List<AStarNode> list, AStarNode node) {
        for (int i = list.size()-1; i >= 0; i--) {
            if (node.getIndex() == list.get(i).getIndex()) {
                list.remove(i);
            }
        }

        return list;
    }

    public void setStartingNode(AStarNode start) {
        startingNode = start;
    }

    public void setFinishingNode(AStarNode finish) {
        finishingNode = finish;
    }
}
