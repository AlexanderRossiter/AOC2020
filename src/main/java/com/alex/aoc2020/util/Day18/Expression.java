package com.alex.aoc2020.util.Day18;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Expression {
    private String expressionString;
    private ExpressionComponent expression;
    private final int method;

    public Expression(String e, int m) {
        expressionString = e;
        if (m == 0) {
            method = 0;
            parseExpression(e);
        } else {
            method = 1;
            parseExpression2(e);
        }
        generateExpressionFromString(expressionString);
    }

    public Expression(String e) {
        this(e, 0);
    }

    private void parseExpression(String e) {
        List<Expression> subExpressions = new ArrayList<>();
        int count = 0;
        int idxStart = 0;
        String finalString = e;
        for (int i = e.length()-1; i >=0 ; i--) {
            if (e.charAt(i) == ')') {
                count += 1;
                if (count == 1){
                    idxStart = i;
                }
            } else if (e.charAt(i) == '(') {
                count -= 1;
                if (count == 0) {
                    subExpressions.add(new Expression(e.substring(i+1, idxStart), method));
                    finalString = finalString.substring(0, i)
                            + subExpressions.get(subExpressions.size()-1).eval()
                            + finalString.substring(idxStart+1) ;
                }
            }
        }
        expressionString = finalString;
    }

    private void parseExpression2(String e) {
        parseExpression(e);

        String regex = "(\\d+ \\+ \\d+(( \\+ \\d+)+)?)";
        String testRegex = "\\((\\d+ \\+ \\d+(( \\+ \\d+)+)?)\\)";
        String testStr = expressionString.replaceAll(regex, "($1)");

        if (!testStr.matches(testRegex)) {
            expressionString = testStr;
            parseExpression(expressionString);
        }
    }

    private void generateExpressionFromString(String e) {
        if (!e.contains("(")) {
            List<String> expressionStr = new ArrayList<>(Arrays.asList(e.split(" ")));
            Constant c1 = new Constant(Long.parseLong(expressionStr.get(0)));
            Constant c2 = new Constant(Long.parseLong(expressionStr.get(2)));
            expression = expressionStr.get(1).equals("+") ? new Plus(c1, c2) : new Times(c1, c2);

            for (int i = 3; i < expressionStr.size() - 1; i+=2) {
                c2 = new Constant(Long.parseLong(expressionStr.get(i + 1)));
                expression = expressionStr.get(i).equals("+") ? new Plus(expression, c2) : new Times(expression, c2);
            }

        } else
            expression = new Constant(-1);

    }

    public long eval() {
        return expression.eval();
    }

    @Override
    public String toString() {
        return expressionString;
    }


}
