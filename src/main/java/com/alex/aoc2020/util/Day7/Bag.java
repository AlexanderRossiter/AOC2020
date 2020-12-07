package com.alex.aoc2020.util.Day7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bag {
    private String colour;
    private final Hashtable<String, Integer> coloursCanContain = new Hashtable<String, Integer>();

    public Bag(String rule) {
        try {
            stripBagRule(rule);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stripBagRule(String rule) throws Exception {

        List<String> ruleList = new ArrayList<>(Arrays.asList(rule.replace(".", "").split(",")));
        String regexFirst = "(\\w+) (\\w+) bags contain (\\d) (\\w+) (\\w+) bags?";
        String regexSubsequent = "\\s?(\\d) (\\w+) (\\w+) bags?";
        String regexEmptyBag = "(\\w+) (\\w+) bags contain no other bags";

        Pattern pattern = Pattern.compile(regexFirst);
        Matcher matcher = pattern.matcher(ruleList.get(0));
        List<String> groups;

        if (ruleList.get(0).matches(regexFirst)) {
            groups = getGroups(matcher);
            colour = groups.get(1) + " " + groups.get(2);
            coloursCanContain.put(groups.get(4) + " " + groups.get(5), Integer.parseInt(groups.get(3)));
            ruleList.remove(0);

            pattern = Pattern.compile(regexSubsequent);
            for (String s : ruleList) {
                if (s.matches(regexSubsequent)) {
                    matcher = pattern.matcher(s);
                    groups = getGroups(matcher);
                    coloursCanContain.put(groups.get(2) + " " + groups.get(3), Integer.parseInt(groups.get(1)));
                }
            }

        } else if (ruleList.get(0).matches(regexEmptyBag)) {
            pattern = Pattern.compile(regexEmptyBag);
            matcher = pattern.matcher(ruleList.get(0));
            groups = getGroups(matcher);
            colour = groups.get(1) + " " + groups.get(2);
        } else {
            throw new Exception("Bad bag rule.");
        }
    }

    private List<String> getGroups(Matcher matcher) {
        List<String> groups = new ArrayList<>();
        while (matcher.find()) {
            for (int j = 0; j <= matcher.groupCount(); j++) {
                groups.add(matcher.group(j));
            }
        }
        return groups;
    }

    public boolean canContain(String bagName) {
        return coloursCanContain.containsKey(bagName);
    }

    public String getColour() {
        return colour;
    }

}
