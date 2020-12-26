package com.alex.aoc2020;

import com.alex.aoc2020.util.Day19.Rule;
import com.alex.aoc2020.util.InputGetter;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19 {
    private static final InputGetter inputGetter = new InputGetter();
    private static final String PATH = "/Users/ADR/Documents/AOC2020/src/main/resources/inputs";
    private static final List<String> input = inputGetter.getInputAsList(19, PATH, "\n\n");
    private static final List<String> ruleList = new ArrayList<>(Arrays.asList(input.get(0).split("\n")));
    private static final List<String> messageList = new ArrayList<>(Arrays.asList(input.get(1).split("\n")));
    private static final Map<String, List<List<String>>> ruleMap = new HashMap<>();
    private static final Map<String, Rule> realRuleMap = new HashMap<>();


    public static void part1() {
        System.out.println(countMatches(realRuleMap.get("0").getValidMessages(realRuleMap)));
    }

    public static void part2() {
        System.out.println(countMatches2(realRuleMap.get("0").getValidMessages(realRuleMap)));
    }

    public static void main(String[] args) {
        initialise();
        //part1();
        part2();
    }

    public static void initialise() {
        for (String s : ruleList) {
            parseRuleErrorHandler(s);
        }
        for (String s : ruleMap.keySet()) {
            realRuleMap.put(s, new Rule(ruleMap.get(s), s));
        }
    }

    private static void parseRuleErrorHandler(String rule) {
        try {
            parseRule(rule);
        } catch (Exception e) {
            System.out.println(rule);
            e.printStackTrace();
        }
    }

    public static int countMatches(List<String> validMessages) {
        int count = 0;
        for (String s : messageList) {
            for (String vm : validMessages) {
                if (s.equals(vm)) {
                    count += 1;
                    break;
                }
            }
        }
        return count;
    }

    public static int countMatches2(List<String> validMessages) {
        int count = 0;

        int count42;
        int count31;

        List<String> rule42 = realRuleMap.get("42").getValidMessages(realRuleMap);
        List<String> rule31 = realRuleMap.get("31").getValidMessages(realRuleMap);
        int rule42Length = rule42.get(0).length();
        int rule31Length = rule31.get(0).length();
        int end8Index = 0;

        boolean matches42;
        boolean matches31;
        boolean check;

        for (String s : messageList) {
            matches42 = false;
            matches31 = false;
            check = true;
            count42 = 0;
            count31 = 0;

            // Test rule 8
            for (int i = 0; i < s.length(); i += rule42Length) {
                for (String vm : rule42) {
                    if (s.substring(i, i+rule42Length).equals(vm)) {
                        matches42 = true;
                        end8Index = i + rule42Length;
                        count42++;
                        System.out.println(String.format("42: %s", vm));
                        break;
                    }
                }
//                if (!matches42) {
//                    check = false;
//                    break;
//                }
            }

            for (int i = end8Index; i < s.length(); i += rule31Length) {
                for (String vm : rule31) {
                    if (s.substring(i, i+rule31Length).equals(vm)) {
                        matches31 = true;
                        count31++;
                        System.out.println(String.format("31: %s", vm));
                        break;
                    }
                }
                if (!matches31) {
                    check = false;
                    break;
                }
            }
            if (check && matches42 && matches31 && (count31 < count42) && (s.length() == count31 * rule31Length + count42 * rule42Length)) {
                count += 1;
                System.out.println(String.format("%s %d %d", s, count42, count31));
            }
        }


        return count;
    }

    public static void parseRule(String rule) throws Exception {
        String regexBaseRule          = "(\\d+): \"(\\w)\"";
        String regexSubRule           = "(\\d+): (\\d+) (\\d+)";
        String regexSubRuleThree      = "(\\d+): (\\d+) (\\d+) (\\d+)";
        String regexSubRuleSingle     = "(\\d+): (\\d+)";
        String regexSubRulePipe       = "(\\d+): (\\d+) (\\d+) \\| (\\d+) (\\d+)";
        String regexSubRulePipeSingle = "(\\d+): (\\d+) \\| (\\d+)";


        Pattern pattern;
        Matcher matcher;

        List<List<String>> linkedRules = new ArrayList<>();

        if (rule.matches(regexBaseRule)) {
            pattern = Pattern.compile(regexBaseRule);
            matcher = pattern.matcher(rule);
            matcher.find();
            linkedRules.add(new ArrayList<>(Collections.singleton(matcher.group(2))));


        } else if (rule.matches(regexSubRule)) {
            pattern = Pattern.compile(regexSubRule);
            matcher = pattern.matcher(rule);
            matcher.find();
            List<String> l = new ArrayList<>();

            for (int i = 2; i <= 3; i++) {
                l.add(matcher.group(i));
            }
            linkedRules.add(l);


        } else if (rule.matches(regexSubRuleThree)) {
            pattern = Pattern.compile(regexSubRuleThree);
            matcher = pattern.matcher(rule);
            matcher.find();
            List<String> l = new ArrayList<>();

            for (int i = 2; i <= 4; i++) {
                l.add(matcher.group(i));
            }
            linkedRules.add(l);

        } else if (rule.matches(regexSubRuleSingle)) {
            pattern = Pattern.compile(regexSubRuleSingle);
            matcher = pattern.matcher(rule);
            matcher.find();
            linkedRules.add(new ArrayList<>(Collections.singleton(matcher.group(2))));


        } else if (rule.matches(regexSubRulePipe)) {
            pattern = Pattern.compile(regexSubRulePipe);
            matcher = pattern.matcher(rule);
            matcher.find();
            List<String> l;
            for (int i = 2; i <= 4; i+=2) {
                l = new ArrayList<>();
                l.add(matcher.group(i));
                l.add(matcher.group(i+1));
                linkedRules.add(l);
            }


        } else if (rule.matches(regexSubRulePipeSingle)) {
            pattern = Pattern.compile(regexSubRulePipeSingle);
            matcher = pattern.matcher(rule);
            matcher.find();
            List<String> l;
            for (int i = 2; i <= 3; i++) {
                l = new ArrayList<>();
                l.add(matcher.group(i));
                linkedRules.add(l);
            }

        } else {
            throw new Exception("Bad rule.");
        }


        ruleMap.put(matcher.group(1), linkedRules);

    }



}
