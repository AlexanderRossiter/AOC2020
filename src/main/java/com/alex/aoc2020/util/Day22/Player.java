package com.alex.aoc2020.util.Day22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Player {
    private int playerId;
    private List<Integer> hand;

    public Player(String startingHand) {
        parseStartingHand(startingHand);
    }

    public Player(int playerId, List<Integer> hand) {
        this.playerId = playerId;
        this.hand = hand;
    }

    private void parseStartingHand(String startingHand) {
        List<String> hand = new ArrayList<>(Arrays.asList(startingHand.split("\n")));

        Matcher matcher = Pattern.compile("Player (\\d+):").matcher(hand.get(0));
        playerId = matcher.find() ? Integer.parseInt(matcher.group(1)) : -1;
        hand.remove(0);

        this.hand = hand.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    public int turnTopCard() {
        int card = hand.get(0);
        hand.remove(0);
        return card;
    }

    public void placeCardsAtBottom(List<Integer> cards) {
       hand.addAll(cards);
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getNumberOfCards() {
        return hand.size();
    }

    public int calculateScore() {
        int multiplier = hand.size();
        int score = 0;
        for (int c : hand) {
            score += multiplier * c;
            multiplier--;
        }
        return score;
    }

    public String getHandString() {
        StringBuilder sb = new StringBuilder();
        for (int c : hand) {
            sb.append(String.format("%d, ", c));
        }
        return sb.toString();
    }

    public List<Integer> getHand() {
        return new ArrayList<>(hand);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Player %d's hand:\n", playerId));
        for (int i : hand) {
            sb.append(String.format("%d\n", i));
        }
        return sb.toString();
    }
}
