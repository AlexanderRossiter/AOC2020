package com.alex.aoc2020.util.Day4;

import java.util.*;

public class Passport {
    private final Hashtable<String, String> fieldsDict;
    private static final Set<String> requiredFields = new HashSet<>(Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"));

    public Passport(String fields) {
        fieldsDict = generateFieldDictionary(fields);
    }

    private static Hashtable<String, String> generateFieldDictionary(String fields) {
        Hashtable<String, String> fieldsDict = new Hashtable<String, String>();
        String[] tempFieldsArr = fields.split(" ");
        for (String s : tempFieldsArr) {
            String[] sSplit = s.split(":");
            fieldsDict.put(sSplit[0], sSplit[1]);
        }

        return fieldsDict;
    }

    public boolean passportContainsField(String field) {
        return fieldsDict.containsKey(field);
    }

    public boolean passportContainsAllRequiredFields() {
        for (String s : requiredFields) {
            if (!passportContainsField(s)) {
                return false;
            }
        }
        return true;
    }

    public boolean birthYearIsValid() {
        String s = fieldsDict.get("byr");
        return (s.length() == 4 && Integer.parseInt(s) >= 1920 && Integer.parseInt(s) <= 2002);
    }

    public boolean issueYearIsValid() {
        String s = fieldsDict.get("iyr");
        return (s.length() == 4 && Integer.parseInt(s) >= 2010 && Integer.parseInt(s) <= 2020);
    }

    public boolean expirationYearIsValid() {
        String s = fieldsDict.get("eyr");
        return (s.length() == 4 && Integer.parseInt(s) >= 2020 && Integer.parseInt(s) <= 2030);
    }

    public boolean heightIsValid() {
        String s = fieldsDict.get("hgt");
        String unit = s.substring(s.length()-2);
        String value = s.substring(0, s.length()-2);

        if (unit.equals("cm")) {
            return (Integer.parseInt(value) >= 150 && Integer.parseInt(value) <= 193);
        } else if (unit.equals("in")) {
            return (Integer.parseInt(value) >= 59 && Integer.parseInt(value) <= 76);
        } else {
            return false;
        }
    }

    public boolean hairColorIsValid() {
        String s = fieldsDict.get("hcl");
        return s.matches("^#[a-f0-9]{6}$");
    }

    public boolean eyeColorIsValid() {
        Set<String> validColors = new HashSet<>(Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth"));
        String s = fieldsDict.get("ecl");

        return validColors.contains(s);
    }

    public boolean passportIdIsValid() {
        String s = fieldsDict.get("pid");
        return s.matches("^[0-9]{9}$");
    }

    public boolean countryIdIsValid() {
        return true;
    }

    public boolean passportIsValid() {
        boolean valid = true;

        // Do it explicitly so can print which fails, and check everything even if we know
        // its not valid. Could just return one but boolean.
        if (!passportContainsAllRequiredFields()) {
            System.out.println("Doesn't have correct fields.");
            valid = false;
        } else {
            if (!birthYearIsValid()) {
                System.out.println("Birth year invalid");
                valid = false;
            }
            if (!issueYearIsValid()) {
                System.out.println("Issue year invalid");
                valid = false;
            }
            if (!expirationYearIsValid()) {
                System.out.println("Expiration year invalid");
                valid = false;
            }
            if (!heightIsValid()) {
                System.out.println("Height invalid");
                valid = false;
            }
            if (!hairColorIsValid()) {
                System.out.println("Hair color invalid");
                valid = false;
            }
            if (!eyeColorIsValid()) {
                System.out.println("Eye color invalid");
                valid = false;
            }
            if (!passportIdIsValid()) {
                System.out.println("Passport id invalid");
                valid = false;
            }
            if (!countryIdIsValid()) {
                System.out.println("Country invalid");
                valid = false;
            }
        }

        if (!valid) {
            System.out.println(fieldsDict);
        }
        return valid;

    }



}
