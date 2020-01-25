package com.tsystems.javaschool.tasks.calculator;


import java.util.*;

public class Calculator {

    private static final Set<String> CHARS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("/", "+", "-")));
    private static final String REGEX_STATEMENT = "[0-9.+/\\-*()]+";

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        // TODO: Implement the logic here
        if (statement == null ||
                !statement.matches(REGEX_STATEMENT) ||
                checkRepeatSign(statement) ||
                !checkParentheses(statement) ||
                statement.contains("/0")) {
            return null;
        } else {
            String result = calculateStatement(statement);
            return result;
        }
    }

    public String calculateStatement(String statement) {
        statement = statement.replaceAll("-", "+-");
        while (statement.contains("(")) {
            statement = calculateOneParentheses(statement);
        }
        return calculateStatements(statement);
    }


    public String findParentheses(String statement) {
        String result = null;
        int openedParenthese = 0;
        int closedParenthese = 0;
        closedParenthese = statement.indexOf(')');
        openedParenthese = statement.lastIndexOf('(') + 1;
        if (openedParenthese > closedParenthese) {
            return statement;
        } else {
            result = statement.substring(openedParenthese, closedParenthese);
            return result;
        }
    }

    public String calculateOneParentheses(String statement) {
        String inParenth = calculateStatements(statement);
        return statement.substring(0, statement.lastIndexOf('(')) + inParenth + statement.substring(statement.indexOf(')') + 1);
    }

    public String calculateSimpleStatement(String statement) {
        String result = null;
        Double resultD = null;
        String sign = null;
        Double a;
        Double b;
        int signIndex = -1;
        for (int i = 1; i < statement.length(); i++) {
            if ((statement.charAt(i) == '*') ||
                    (statement.charAt(i) == '/') ||
                    (statement.charAt(i) == '+')) {
                sign = String.valueOf(statement.charAt(i));
                signIndex = i;
                break;
            }
        }
        switch (sign) {
            case "*":
                a = Double.valueOf(statement.substring(0, signIndex));
                b = Double.valueOf(statement.substring(signIndex + 1));
                resultD = a * b;
                break;
            case "/":
                a = Double.valueOf(statement.substring(0, signIndex));
                b = Double.valueOf(statement.substring(signIndex + 1));
                if (b != 0.0) {
                    resultD = a / b;
                    break;
                }
                break;
            case "+":
                a = Double.valueOf(statement.substring(0, signIndex));
                b = Double.valueOf(statement.substring(signIndex + 1));
                resultD = a + b;
                break;
        }
        if (resultD != null) {
            result = returnResultString(resultD);
        }
        return result;
    }

    public String returnResultString(double resultD) {
        String result = null;
        if (resultD % 1 == 0) {
            result = String.valueOf((int) Math.round(resultD));
        } else {
            result = String.valueOf(resultD);
        }
        return result;
    }

    public String calculateStatements(String statement) {
        statement = findParentheses(statement);
        ArrayList<Integer> signsIndex = new ArrayList<>();
        String middleStatement;
        do {
            if (statement == null) {
                break;
            }
            signsIndex.clear();
            for (int i = 1; i < statement.length(); i++) {
                if ((statement.charAt(i) == '*') ||
                        (statement.charAt(i) == '/') ||
                        (statement.charAt(i) == '+')) {
                    signsIndex.add(i);
                }
            }
            if (signsIndex.size() == 1) {
                statement = calculateSimpleStatement(statement);

            } else if (checkPriority(statement)) {
                for (int i = 0; i < signsIndex.size(); i++) {
                    if ((statement.charAt(signsIndex.get(i)) == '*') || (statement.charAt(signsIndex.get(i)) == '/')) {
                        if (i == (signsIndex.size() - 1)) {
                            middleStatement = calculateSimpleStatement(statement.substring(signsIndex.get(i - 1) + 1));
                            statement = statement.substring(0, signsIndex.get(i - 1) + 1) + middleStatement;
                            break;
                        } else if (i == 0) {
                            middleStatement = calculateSimpleStatement(statement.substring(0, signsIndex.get(i + 1)));
                            statement = middleStatement + statement.substring(signsIndex.get(i + 1));
                            break;
                        } else {
                            middleStatement = calculateSimpleStatement(statement.substring(signsIndex.get(i - 1) + 1, signsIndex.get(i + 1)));
                            statement = statement.substring(0, signsIndex.get(i - 1) + 1) + middleStatement + statement.substring(signsIndex.get(i + 1));
                            break;
                        }
                    }
                }
            } else {
                while (signsIndex.size() > 1) {
                    middleStatement = calculateSimpleStatement(statement.substring(0, signsIndex.get(1)));
                    statement = middleStatement + statement.substring(signsIndex.get(1));
                    break;
                }
            }

        } while (signsIndex.size() >= 1);
        return statement;
    }

    public boolean checkPriority(String statement) {
        boolean withPriority = false;
        for (int i = 0; i < statement.length(); i++) {
            if ((statement.charAt(i) == '*') ||
                    (statement.charAt(i) == '/')) {
                withPriority = true;
            }
        }
        return withPriority;
    }

    public boolean checkRepeatSign(String statement) {
        boolean repeatSign = false;
        ArrayList<Integer> signsIndex = new ArrayList<>();
        for (int i = 1; i < statement.length(); i++) {
            if ((statement.charAt(i) == '*') ||
                    (statement.charAt(i) == '/') ||
                    (statement.charAt(i) == '+') ||
                    (statement.charAt(i) == '-') ||
                    (statement.charAt(i) == '.')) {
                signsIndex.add(i);
            }
        }
        for (int i = 0; i < (signsIndex.size() - 1); i++) {
            if (signsIndex.get(i) == (signsIndex.get(i + 1) - 1)) {
                repeatSign = true;
            }
        }
        return repeatSign;
    }

    public boolean checkParentheses(String statement) {
        boolean pairParentheses = false;
        int a = 0;
        int b = 0;
        for (int i = 0; i < statement.length(); i++) {
            if (statement.charAt(i) == '(') {
                a += 1;
            }
            if (statement.charAt(i) == ')') {
                b += 1;
            }
        }
            if (a == b) {
                pairParentheses = true;
            }
        return pairParentheses;
    }
}
