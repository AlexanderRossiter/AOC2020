package com.alex.aoc2020.util.Day6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SeatingGroup {
    private final String groupAnswersFlat;
    public final int groupSize;

    public SeatingGroup(String groupAnswers_) {
        List<String> groupAnswers = Arrays.asList(groupAnswers_.split(" "));
        groupAnswersFlat = groupAnswers_.replace(" ", "");

        groupSize = groupAnswers.size();
    }

    public int countAllYesAnswers() {
        int allYesCount = 0;
        for( char c : groupAnswersFlat.toCharArray() ) {
            if (groupAnswersFlat.chars()
                    .mapToObj(c2 -> (char) c2)
                    .filter(c2 -> c2 == c)
                    .count() == groupSize) {
                allYesCount += 1;
            }
        }
        return allYesCount / groupSize;
    }


    public int countUniqueAnswers() {
        List<Character> uniques = new ArrayList<>();
        groupAnswersFlat.chars().
                mapToObj(c -> (char) c).
                forEach(
                        c -> {
                            if (!uniques.contains(c)) {
                                uniques.add(c);
                            }
                        });

        return uniques.size();
    }
}
