package com.alex.aoc2020.util.Day7;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BagCollection {
    private static List<Bag> bags;

    public BagCollection(List<String> inputRules) {
        bags = inputRules.stream().map(Bag::new).collect(Collectors.toList());
    }

    public Set<String> countCanContain(String colour) {
        Set<String> canContain = new HashSet<>();

        for (Bag b : bags) {
            if (b.canContain(colour)) {
                canContain.add(b.getColour());
            }
        }
        Set<String> canContainCopy = new HashSet<>(canContain);
        for (String s : canContainCopy) {
            canContain.addAll(countCanContain(s));
        }

        return canContain;
    }

    public Bag getBagWithColour(String colour) {
        return bags.stream()
                .filter(b -> b.getColour().equals(colour))
                .collect(Collectors.toList())
                .get(0);
    }

    public int getNumberOfBagsInside(String colour) {
        Bag b = getBagWithColour(colour);
        int count = 0;
        int factor ;
        for (String s : b.getBagsCanContain()) {
            factor = b.getNumberOfBagsCanContain(s);
            count += factor;
            count += factor * getNumberOfBagsInside(s);
        }

        return count;
    }
}
