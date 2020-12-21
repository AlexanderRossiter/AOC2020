package com.alex.aoc2020.util.Day15;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rule {
    private String name;
    private int l1;
    private int l2;
    private int u1;
    private int u2;

    public Rule(String rule) {
        try {
            parseRule(rule);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseRule(String rule) throws Exception {
        String regex = "([a-z ]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(rule);

        if (matcher.find()) {
            name = matcher.group(1);
            l1 = Integer.parseInt(matcher.group(2));
            u1 = Integer.parseInt(matcher.group(3));
            l2 = Integer.parseInt(matcher.group(4));
            u2 = Integer.parseInt(matcher.group(5));
        } else {
            throw new Exception("Invalid Rule");
        }
    }

    public boolean valueSatisfiesRule(int n) {
        return (n >= l1 && n <= u1) || (n >= l2 && n <= u2);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%s: %d-%d or %d-%d", name, l1, u1, l2, u2);
    }
}
