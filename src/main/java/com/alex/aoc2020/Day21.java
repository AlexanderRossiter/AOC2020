package com.alex.aoc2020;

import com.alex.aoc2020.util.InputGetter;

import java.util.*;
import java.util.stream.Collectors;

public class Day21 {
    private static final InputGetter inputGetter = new InputGetter();
    private static final String PATH = "/Users/ADR/Documents/AOC2020/src/main/resources/inputs";
    private static final List<String> input = inputGetter.getInputAsList(21, PATH, "\n");
    private static Map<Set<String>, Set<String>> ingredientMap = new HashMap<>();

    public static void part1() {
        System.out.println(getNumberOfAppearancesOfSafeIngredients());
    }

    public static void part2() {
        System.out.println(
                generateCanonicalDangerousIngredientList(
                        simplifyIngredientAllergenMap(
                                getAllergenHashMap())));
    }

    public static void main(String[] args) {
        input.forEach(Day21::parseLine);
        part1();
        part2();
    }

    private static String generateCanonicalDangerousIngredientList(Map<String, String> allergenIngredientMap) {
        Map<String, String> treeAllergenIngredientMap = new TreeMap<>(allergenIngredientMap);
        StringBuilder sb = new StringBuilder();
        for (String s : treeAllergenIngredientMap.keySet()) {
            sb.append(treeAllergenIngredientMap.get(s));
            sb.append(",");
        }
        sb.replace(sb.length()-1,sb.length(),"");
        return sb.toString();
    }

    private static void parseLine(String line) {
        Set<String> ingredients = new HashSet<>(Arrays.asList(Arrays.asList(line.split("\\(")).get(0).split(" ")));
        Set<String> allergens   = Arrays.stream(Arrays.asList(line.split("\\(")).get(1).replace(")", "").replace(",", "").split(" ")).filter(s -> !s.equals("contains")).collect(Collectors.toSet());

        ingredientMap.put(ingredients, allergens);
    }

    private static Map<String, String> simplifyIngredientAllergenMap(Map<String, Set<String>> baseIngredientAllergenMap) {
        int maxSize = -1;
        Map<String, String> allergenIngredientMap = new HashMap<>();
        while (!(maxSize == 1)) {
            maxSize = -1;
            for (String s : baseIngredientAllergenMap.keySet()) {
                Set<String> ingredientSet = baseIngredientAllergenMap.get(s);
                if (ingredientSet.size() == 1) {
                    allergenIngredientMap.put(s, ingredientSet.iterator().next());
                    removeIngredientFromOtherAllergens(baseIngredientAllergenMap, ingredientSet.iterator().next());
                }
                if (ingredientSet.size() > maxSize)
                    maxSize = ingredientSet.size();
            }
        }
        return allergenIngredientMap;
    }

    private static void removeIngredientFromOtherAllergens(Map<String, Set<String>> baseIngredientAllergenMap, String ingredient) {
        for (String s : baseIngredientAllergenMap.keySet()) {
            if (baseIngredientAllergenMap.get(s).contains(ingredient) && baseIngredientAllergenMap.get(s).size() > 1) {
                Set<String> newIngredientSet = baseIngredientAllergenMap.get(s);
                newIngredientSet.remove(ingredient);
                baseIngredientAllergenMap.put(s, newIngredientSet);
            }
        }
    }

    private static int getNumberOfAppearancesOfSafeIngredients() {
        List<String> allFoodIngredientsList = new ArrayList<>();
        for (Set<String> s : ingredientMap.keySet()) {
            allFoodIngredientsList.addAll(s);
        }
        int count = 0;
        for (String ingredient : getSafeIngredients()) {
            count += Collections.frequency(allFoodIngredientsList, ingredient);
        }
        return count;
    }

    private static Set<String> getAllAllergens() {
        Set<String> allergens = new HashSet<>();
        for (Set<String> s : ingredientMap.keySet()) {
            allergens.addAll(ingredientMap.get(s));
        }
        return allergens;
    }

    private static Set<String> getAllIngredients() {
        Set<String> ingredients = new HashSet<>();
        for (Set<String> s : ingredientMap.keySet()) {
            ingredients.addAll(s);
        }
        return ingredients;
    }

    private static Set<String> getSafeIngredients() {
        Set<String> safeIngredients;
        Set<String> uncertainIngredients = new HashSet<>();

        for (Set<String> s : getAllergenHashMap().values()) {
            uncertainIngredients.addAll(s);
        }
        safeIngredients = symmetricDifference(uncertainIngredients, getAllIngredients());
        return safeIngredients;
    }

    private static Map<String, Set<String>> getAllergenHashMap(){

        Set<String> intersection;
        Map<String, Set<String>> ingredientAllergenMap = new HashMap<>();

        for (String allergen : getAllAllergens()) {
            intersection = new HashSet<>();
            for (Set<String> s : ingredientMap.keySet()) {
                if (ingredientMap.get(s).contains(allergen)) {
                    if (intersection.size() == 0) {
                        intersection.addAll(s);
                    } else {
                        intersection.retainAll(s);
                    }
                }
            }
            ingredientAllergenMap.put(allergen, intersection);

        }
        return ingredientAllergenMap;
    }

    private static <T> Set<T> symmetricDifference(Set<T> a, Set<T> b) {
        Set<T> result = new HashSet<T>(a);
        for (T element : b) {
            // .add() returns false if element already exists
            if (!result.add(element)) {
                result.remove(element);
            }
        }
        return result;
    }
}
