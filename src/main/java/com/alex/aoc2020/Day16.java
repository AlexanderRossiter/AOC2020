package com.alex.aoc2020;

import com.alex.aoc2020.util.Day16.RuleSet;
import com.alex.aoc2020.util.Day16.Ticket;
import com.alex.aoc2020.util.InputGetter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day16 {
    private static final InputGetter inputGetter = new InputGetter();
    private static final String PATH = "/Users/ADR/Documents/AOC2020/src/main/resources/inputs";
    private static final List<String> input = inputGetter.getInputAsList(16, PATH, "\n\n");
    private static final RuleSet ruleSet = new RuleSet(Arrays.asList(input.get(0).split("\n")));
    private static final Ticket myTicket = new Ticket(Arrays.asList(input.get(1).split("\n")[1].split(",")), ruleSet);
    private static List<Ticket> nearbyTickets = new ArrayList<>();
    private static final List<Ticket> invalidTickets = new ArrayList<>();
    private static final HashMap<Integer, Set<Integer>> validRules = new HashMap<>();

    public static void part1() {
        System.out.println(getInvalidValues().stream().reduce(0, Integer::sum));
    }

    public static void part2(){
        removeInvalidTickets();
        initialiseValidRulesMap();
        System.out.println(doShittyThingThatCBAToRefactor());
    }

    public static void main(String[] args) {
        parseNearbyTickets();
        getInvalidTickets();
        part1();
        part2();
    }

    private static long doShittyThingThatCBAToRefactor() {
        for (Ticket t : nearbyTickets) {
            HashMap<Integer, Set<Integer>> hm = t.getRulesSatisfiedMap();
            for (int i = 0; i < t.size(); i++) {
                Set<Integer> set = validRules.get(i);
                Set<Integer> setForT = hm.get(i);
                set.addAll(setForT);
                validRules.put(i, set);
            }
        }

        for (int i = 0; i < myTicket.size(); i++) {
            Set<Integer> allRuleNumbers = IntStream.range(0,ruleSet.size()).boxed().collect(Collectors.toSet());
            Set<Integer> set = validRules.get(i);
            allRuleNumbers.removeAll(set);
            validRules.put(i, allRuleNumbers);
        }

        ruleSet.populateValueToRuleMap(validRules);

        long sum = 1;
        for (int i = 0; i < ruleSet.size(); i++) {
            if (ruleSet.getRule(i).getName().contains("departure")) {
                sum *= myTicket.getValue(ruleSet.getValueIndexFromRule(i));
            }
        }

        return sum;
    }

    private static void initialiseValidRulesMap() {
        for (int i = 0; i < myTicket.size(); i++) {
            validRules.put(i, new HashSet<>());
        }
    }

    private static void parseNearbyTickets() {
        List<String> nearbyTicketValues = new ArrayList<>(Arrays.asList(input.get(2).split("\n")));
        nearbyTicketValues.remove(0);
        nearbyTickets = nearbyTicketValues
                .stream()
                .map(s -> s.split(","))
                .map(Arrays::asList)
                .map(s -> new Ticket(s, ruleSet))
                .collect(Collectors.toList());
    }

    private static List<Integer> getInvalidValues() {
        List<Integer> invalidValues = new ArrayList<>();
        for (Ticket t : nearbyTickets) {
            invalidValues.addAll(t.getInvalidValues());
        }
        return invalidValues;
    }

    private static void getInvalidTickets() {
        for (Ticket t : nearbyTickets) {
            if (t.getInvalidValues().size() > 0) {
                invalidTickets.add(t);
            }
        }
    }

    private static void removeInvalidTickets() {
        for (Ticket t : invalidTickets) {
            nearbyTickets.remove(t);
        }
    }

}
