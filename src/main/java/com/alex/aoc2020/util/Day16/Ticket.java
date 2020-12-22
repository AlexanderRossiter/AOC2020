package com.alex.aoc2020.util.Day16;

import java.util.*;
import java.util.stream.Collectors;

public class Ticket {
    private final List<Integer> values;
    private final RuleSet ruleSet;
    private List<Integer> invalidValues = new ArrayList<>();
    private HashMap<Integer, Set<Integer>> rulesSatisfiedMap = new HashMap<>();

    public Ticket(List<String> values_, RuleSet rs) {
        values = values_.stream().map(Integer::parseInt).collect(Collectors.toList());
        ruleSet = rs;

        findInvalidValues();
        determineWhichValuePositionsSatisfyRules();
    }

    public int getValue(int n) {
        return values.get(n);
    }

    private void findInvalidValues() {
        invalidValues = values.stream()
                .filter(v -> !ruleSet.valueSatisfiesAtLeastOneRule(v))
                .collect(Collectors.toList());
    }

    public List<Integer> getInvalidValues() {
        return invalidValues;
    }

//    private void initialiseValuePositionList() {
//        for (int v : values) {
//            valuePositionSatisfiesRuleList.add(new ArrayList<>());
//        }
//    }
    private void addNumberToHashMap(int n, int idx) {
        if (rulesSatisfiedMap.containsKey(n)) {
            Set<Integer> arr = rulesSatisfiedMap.get(n);
            arr.add(idx);
            rulesSatisfiedMap.put(n, arr);
        } else {
            rulesSatisfiedMap.put(n, new HashSet<>(Collections.singletonList(idx)));
        }
    }

    private void initialiseValidRulesMap() {
        for (int i = 0; i < values.size(); i++) {
            rulesSatisfiedMap.put(i, new HashSet<>());
        }
    }

    private void determineWhichValuePositionsSatisfyRules() {
        initialiseValidRulesMap();
        int valuePosition = 0;
        for (int v : values) {
            for (int i = 0; i < ruleSet.size(); i++) {
                if (!ruleSet.getRule(i).valueSatisfiesRule(v)) {
                    addNumberToHashMap(valuePosition, i);
                }
            }
            valuePosition++;
        }
    }

    public int size() {
        return values.size();
    }

    public HashMap<Integer, Set<Integer>> getRulesSatisfiedMap() {
        return rulesSatisfiedMap;
    }
}
