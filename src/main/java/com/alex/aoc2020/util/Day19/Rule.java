package com.alex.aoc2020.util.Day19;

import java.util.*;

public class Rule {
    private List<List<String>> linksTo;
    private final String ruleNo;

    public Rule(List<List<String>> linksTo_, String ruleNo_) {
        linksTo = linksTo_;
        ruleNo = ruleNo_;
    }

    public List<String> getValidMessages(Map<String, Rule> ruleMap) {
        if (linksTo.size() == 2) { // Or rule
            List<String> validMessages = sortRuleLists(linksTo.get(0), ruleMap);
            validMessages.addAll(sortRuleLists(linksTo.get(1), ruleMap));
            return validMessages;

        } else if (linksTo.get(0).get(0).matches("[ab]")) { // Letter rule
            return new ArrayList<>(Collections.singleton(linksTo.get(0).get(0)));

        } else { // Standard rule
            return sortRuleLists(linksTo.get(0), ruleMap);
        }
    }

    private List<String> sortRuleLists(List<String> rules, Map<String, Rule> ruleMap) {
        List<List<String>> r = new ArrayList<>();
        for (String s : rules) {
            r.add(ruleMap.get(s).getValidMessages(ruleMap));
        }
        if (r.size() >= 2) {
            return concatenateMessages(new ArrayList<>(), r.size()-1, r);

        } else if (r.get(0).get(0).matches("[ab]+")) { // Letter rule
            return new ArrayList<>(r.get(0));
        } else{
            return  ruleMap.get(r.get(0).get(0)).getValidMessages(ruleMap);
        }
    }

    public static List<String> concatenateMessages(List<String> validMessages, int level, List<List<String>> allMessages) {

        List<String> validMessageCopy = new ArrayList<>(validMessages);
        validMessages = new ArrayList<>();
        if (validMessageCopy.size() > 0) {
            for (int i = 0; i < allMessages.get(level).size(); i++) {
                for (String s : validMessageCopy) {
                    validMessages.add(allMessages.get(level).get(i) + s);
                }
            }
            validMessages = new ArrayList<>( new HashSet<>(validMessages));

        } else {

            validMessages.addAll(allMessages.get(level));

        }

        if (level > 0) {
            validMessages = concatenateMessages(validMessages, level-1, allMessages);
        }



        return validMessages;
    }

    public String getRuleNo() {
        return ruleNo;
    }

    public List<List<String>> getLinksTo() {
        return linksTo;
    }

    public void setLinksTo(List<List<String>> linksTo) {
        this.linksTo = linksTo;
    }
}
