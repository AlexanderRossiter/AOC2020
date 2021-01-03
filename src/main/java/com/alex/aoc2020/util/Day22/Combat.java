package com.alex.aoc2020.util.Day22;

import java.util.*;
import java.util.stream.Collectors;

public class Combat {
    private HashMap<Integer, Player> players = new HashMap<>();

    public Combat(List<String> startingState) {
        startingState.stream().map(Player::new).collect(Collectors.toSet()).forEach(p -> players.put(p.getPlayerId(), p));
    }

    private void playRound() {
        // Start assuming 2 player and hope part 2 doesn't screw us.
        Map<Integer, Player> cards = new HashMap<>();
        players.values().forEach(p -> cards.put(p.turnTopCard(), p));
        NavigableMap<Integer, Player> sortedCards = new TreeMap<>(cards);

        Player p = sortedCards.lastEntry().getValue(); // sortedCards.get(sortedCards.keySet().iterator().next());
        p.placeCardsAtBottom(reverseList(new ArrayList<>(sortedCards.keySet())));
    }

    public int playCombat() {
        int roundNo = 1;
        while (allPlayersHaveCards()) {
            System.out.println(getRoundDetails(roundNo));
            playRound();
            roundNo ++;
        }
        //System.out.println(getRoundDetails(roundNo));

        int winningScore = -1;
        for (Player p : players.values()) {
            winningScore = Math.max(p.calculateScore(), winningScore);
        }
        return winningScore;
    }

    private int playRecursiveRound(int gameNo, Map<Integer, Player> players) {
        int winnerID;

        Map<Integer, Player> cards = new HashMap<>();
        players.values().forEach(p -> cards.put(p.turnTopCard(), p));
        NavigableMap<Integer, Player> sortedCards = new TreeMap<>(cards);

        Map<Integer, Integer> playerIdCardMap = new HashMap<>();
        sortedCards.keySet().forEach(cardValue -> playerIdCardMap.put(sortedCards.get(cardValue).getPlayerId(), cardValue));

//        List<Integer> cardList = new ArrayList<>(sortedCards.keySet());
        if (playersHaveEnoughCardsToSubgame(sortedCards)) {
            Map<Integer, Player> newPlayerMap = new HashMap<>();
            newPlayerMap.put(1, new Player(1, new ArrayList<>(players.get(1).getHand().subList(0, playerIdCardMap.get(1)))));
            newPlayerMap.put(2, new Player(2, new ArrayList<>(players.get(2).getHand().subList(0, playerIdCardMap.get(2)))));

            winnerID = playRecursiveCombat(gameNo+1, newPlayerMap);

            Player p = players.get(winnerID);
            p.placeCardsAtBottom(Arrays.asList(playerIdCardMap.get(winnerID), playerIdCardMap.get(getLoserId(winnerID))));

        } else {
            Player p = sortedCards.lastEntry().getValue();
            winnerID = p.getPlayerId();
            p.placeCardsAtBottom(reverseList(new ArrayList<>(sortedCards.keySet())));
        }
        return winnerID;
    }

    private boolean playersHaveEnoughCardsToSubgame(NavigableMap<Integer, Player> sortedCards) {
        boolean isPlayable = true;
        for (Integer c: sortedCards.keySet()) {
            if ((sortedCards.get(c).getHand().size() < c || !isPlayable)) {
                isPlayable = false;
            }
        }
        return isPlayable;
    }

    private int getLoserId(int winnerId) {
        switch (winnerId) {
            case 1:
                return 2;
            case 2:
                return 1;
            default:
                return -1;
        }
    }


    private int playRecursiveCombat(int gameNo, Map<Integer, Player> playerMap) {
        Map<Integer, List<List<Integer>>> previousHandMap = new HashMap<>();
        // Initialise the previous hands.
        for (int id : playerMap.keySet()) {
            previousHandMap.put(id, new ArrayList<>(Collections.singletonList(playerMap.get(id).getHand())));
        }
        //System.out.println(String.format("--- Game %d ---", gameNo));
        int roundNo = 1;
        int winnerId = -1;
        while (allPlayersHaveCards(playerMap)) {
            //System.out.println(getRoundDetails(roundNo, playerMap));
            winnerId = playRecursiveRound(gameNo, playerMap);

            for (int id : playerMap.keySet()) {
                List<List<Integer>> ls = previousHandMap.get(id);
                ls.add(playerMap.get(id).getHand());
                previousHandMap.put(id, ls);
            }

            if (handAlreadyPresent(previousHandMap)) {
                return 1;
            }
            roundNo++;
        }
        //System.out.println(String.format("Winner was player %d, returning to Game %d.", winnerId, gameNo-1));
        return winnerId;
    }

    private boolean handAlreadyPresent(Map<Integer, List<List<Integer>>> previousHands) {
        List<List<Integer>> hands1 = new ArrayList<>(previousHands.get(1));
        List<List<Integer>> hands2 = new ArrayList<>(previousHands.get(2));

        List<Integer> lastHand1 = hands1.get(hands1.size()-1);
        List<Integer> lastHand2 = hands2.get(hands2.size()-1);

        hands1.remove(hands1.size()-1);
        hands2.remove(hands2.size()-1);

        return hands1.contains(lastHand1) && hands2.contains(lastHand2);

    }

    public int playCombat2() {
        HashMap<Integer, Player> recursivePlayers = deepCopyPlayerMap(players);
        int winnerId = playRecursiveCombat(1, recursivePlayers);
        return recursivePlayers.get(winnerId).calculateScore();
    }

    private boolean allPlayersHaveCards() {
        return players.values().stream().noneMatch(p -> p.getNumberOfCards() == 0);
    }

    private boolean allPlayersHaveCards(Map<Integer, Player> players) {
        return players.values().stream().noneMatch(p -> p.getNumberOfCards() == 0);
    }

    private String getRoundDetails(int roundNo) {
        return getRoundDetails(roundNo, players);
    }

    private String getRoundDetails(int roundNo, Map<Integer, Player> players) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("-- Round %d --\n", roundNo));
        for (Player p : players.values()) {
            sb.append(String.format("Player %d's hand: %s\n", p.getPlayerId(), p.getHandString()));
        }
        return sb.toString();
    }

    private static<T> List<T> reverseList(List<T> list)
    {
        List<T> reverse = new ArrayList<>(list);
        Collections.reverse(reverse);
        return reverse;
    }

    private static HashMap<Integer, Player> deepCopyPlayerMap(Map<Integer, Player> originalMap) {
        HashMap<Integer, Player> newHashmap = new HashMap<>();
        for (int id : originalMap.keySet()) {
            newHashmap.put(id, new Player( originalMap.get(id).getPlayerId(), originalMap.get(id).getHand()));
        }
        return newHashmap;
    }
}
