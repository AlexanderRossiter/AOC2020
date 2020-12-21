package com.alex.aoc2020.util.Day15;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RuleSet {
    private List<Rule> ruleList;
    private HashMap<Integer, Integer> valueToRuleMap = new HashMap<>();
    private HashMap<Integer, Integer> ruleToValueMap = new HashMap<>();


    public RuleSet(List<String> rules) {
        ruleList = rules.stream().map(Rule::new).collect(Collectors.toList());
    }

    public Rule getRule(int n) {
        return ruleList.get(n);
    }

    public boolean valueSatisfiesAtLeastOneRule(int value) {
        for (Rule r : ruleList) {
            if (r.valueSatisfiesRule(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Rule r : ruleList) {
            sb.append(r.toString());
            sb.append("\n");
        }
        sb.replace(sb.length()-1, sb.length(), "");
        return sb.toString();
    }

    public int size() {
        return ruleList.size();
    }

    public void populateValueToRuleMap(HashMap<Integer, Set<Integer>> validRuleMap) {
        while (!isFullyPopulated()) {
            for (int i : validRuleMap.keySet()) {
                if (validRuleMap.get(i).size() == 1) {
                    int ruleNo = validRuleMap.get(i).iterator().next();
                    valueToRuleMap.put(i, ruleNo);
                    for (int j : validRuleMap.keySet()) {
                        if (validRuleMap.get(j).contains(ruleNo)) {
                            Set<Integer> set = validRuleMap.get(j);
                            set.remove(ruleNo);
                            validRuleMap.put(j, set);
                        }
                    }
                }
            }
        }
        generateRuleToValueMap();
    }

    private void generateRuleToValueMap() {
        for(Map.Entry<Integer, Integer> entry : valueToRuleMap.entrySet()){
            ruleToValueMap.put(entry.getValue(), entry.getKey());
        }
    }

    public int getValueIndexFromRule(int n) {
        return ruleToValueMap.get(n);
    }

    public HashMap<Integer, Integer> getValueToRuleMap() {
        return valueToRuleMap;
    }

    public HashMap<Integer, Integer> getRuleToValueMap() {
        return ruleToValueMap;
    }

    private boolean isFullyPopulated() {
        return valueToRuleMap.keySet().size() == size();
    }
}
