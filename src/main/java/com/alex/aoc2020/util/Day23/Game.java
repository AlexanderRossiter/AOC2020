package com.alex.aoc2020.util.Day23;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private Map<Integer, Cup> cupMap = new HashMap();

    private int currentCupLabel = 0;

    private int nCups;

    public Game(String cupString) {
        parseCupString(cupString);
    }

    private void parseCupString(String cupString) {
        CharacterIterator it = new StringCharacterIterator(cupString);
        currentCupLabel = Integer.parseInt(String.valueOf(cupString.charAt(0)));

        while(it.current() != CharacterIterator.DONE) {
            cupMap.put(Integer.parseInt(String.valueOf(it.current())),
                    new Cup(Integer.parseInt(String.valueOf(it.current()))));

            try {
                cupMap.get(Integer.parseInt(String.valueOf(it.current())))
                        .setNextCupLabel(Integer.parseInt(String.valueOf(it.next())));
            } catch (NumberFormatException e) {
                cupMap.get(Integer.parseInt(String.valueOf(it.previous())))
                        .setNextCupLabel(currentCupLabel);
                it.next();
            }
        }
        nCups = cupMap.size();
    }

    public String playCups(int nRounds) {
        for (int i = 0; i < nRounds; i++) {
            playCrabRound();
        }
        return getFinalCupString();
    }

    public long playCups2(int nRounds) {
        makeFullNumberSet();
        for (int i = 0; i < nRounds; i++) {
            if (i % 100 == 0) {
                System.out.println(String.format("%f %% Complete.", ((float) i / (float) nRounds) * 100.));
            }

            playCrabRound();
        }
        int label1 = cupMap.get(1).getNextCupLabel();
        return (long) label1 * (long) cupMap.get(label1).getNextCupLabel();
    }

    private String getFinalCupString() {
        StringBuilder sb = new StringBuilder();
        Cup c = cupMap.get(1);
        for (int i = 0; i < nCups-1; i++) {
            sb.append(c.getNextCupLabel());
            c = cupMap.get(c.getNextCupLabel());
        }

        return sb.toString();
    }

    public void playCrabRound() {
        // Get the cups picked up.
        List<Integer> crabCupLabels = new ArrayList<>(Arrays.asList(
                cupMap.get(currentCupLabel).getNextCupLabel(),
                cupMap.get(cupMap.get(currentCupLabel).getNextCupLabel()).getNextCupLabel(),
                cupMap.get(cupMap.get(cupMap.get(currentCupLabel).getNextCupLabel()).getNextCupLabel()).getNextCupLabel()));



        // Find the destination cup.
        int destinationCupLabel = currentCupLabel-1;
        destinationCupLabel = destinationCupLabel == 0 ? nCups : destinationCupLabel;

        while (crabCupLabels.contains(destinationCupLabel)) {
            destinationCupLabel--;
            destinationCupLabel = destinationCupLabel % nCups;
            destinationCupLabel = destinationCupLabel == 0 ? nCups : destinationCupLabel;
        }

//        System.out.println(toString());
//        System.out.println(crabCupLabels);
//        System.out.println(destinationCupLabel);

        // Make sure the current cup now points to the cup after the last one picked up.
        cupMap.get(currentCupLabel)
                .setNextCupLabel(
                        cupMap.get(crabCupLabels.get(2))
                                .getNextCupLabel());

        // Make a note of what cup the destination cup points to.
        int destinationCupNextLabel = cupMap.get(destinationCupLabel).getNextCupLabel();

        // Make sure destination cup now points to the first of the picked up cups.
        cupMap.get(destinationCupLabel).setNextCupLabel(crabCupLabels.get(0));

        // Now make the last picked up cup point to the cup the destination cup used to point to.
        cupMap.get(crabCupLabels.get(2)).setNextCupLabel(destinationCupNextLabel);

        // Set the new current cup
        currentCupLabel = cupMap.get(currentCupLabel).getNextCupLabel();

    }

    private void makeFullNumberSet() {
        Cup c = cupMap.get(cupMap.keySet().stream().filter(i -> cupMap.get(i).getNextCupLabel() == currentCupLabel).collect(Collectors.toList()).get(0));
        c.setNextCupLabel(nCups+1);
        for (int i = nCups+1; i <= 1000000; i++) {
            c = new Cup(i);
            if (i == 1000000) {
                c.setNextCupLabel(currentCupLabel);
            } else {
                c.setNextCupLabel(i + 1);
            }
            cupMap.put(i, c);


        }
        nCups = cupMap.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String s;

        Cup c = cupMap.get(currentCupLabel);
        for (int i = 0; i < nCups; i++) {
            s = c.getLabel() == currentCupLabel ? "(" + c.getLabel() + "), " : c.getLabel() + ", ";
            sb.append(s);
            c = cupMap.get(c.getNextCupLabel());
        }

        return sb.toString();

    }
}
