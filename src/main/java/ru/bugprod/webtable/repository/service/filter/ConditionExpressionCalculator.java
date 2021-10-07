package ru.bugprod.webtable.repository.service.filter;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import ru.bugprod.webtable.repository.service.calculator.ExpressionCalculator;
import ru.bugprod.webtable.repository.service.filter.model.exception.IllegalConditionException;
import ru.bugprod.webtable.repository.service.util.VarNameFormer;
import tech.tablesaw.api.Table;
import tech.tablesaw.selection.Selection;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

//TODO: поддержать скобочки
//TODO: фильтрация по времени
//TODO: сделать правильную последвоательность операций
public class ConditionExpressionCalculator {

    private static final String[] COMPARE_OPERATORS = {"=", ">", "<", "<=", ">="};
    private static final char[] BOOL_OPERATORS = {'&', '|'};
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    public static Selection getCondition(Table table, String expression) {
        expression = expression.replace(" ", "");

        var intermediateResults = new HashMap<String, Selection>();
        var modulatedExpression = expression;

        var chars = expression.toCharArray();
        var leftIndex = 0;

        for (var i = 0; i < chars.length; i++) {
            if (ArrayUtils.contains(BOOL_OPERATORS, chars[i]) || i == chars.length - 1) {
                var rightIndex = i != chars.length - 1 ? i : chars.length;
                var segment = expression.substring(leftIndex, rightIndex);

                var result = computeSegment(table, segment);
                var varName = VarNameFormer.getVarName(table, intermediateResults.keySet());

                intermediateResults.put(varName, result);
                leftIndex = rightIndex + 1;
                modulatedExpression = StringUtils.replaceOnce(modulatedExpression, segment, varName);
            }
        }

        var result = Selection.withRange(0, table.rowCount());
        var operation = '&';
        chars = modulatedExpression.toCharArray();
        for (char aChar : chars) {
            if (ArrayUtils.contains(BOOL_OPERATORS, aChar)) {
                operation = aChar;
            } else {
                var selection = intermediateResults.remove(String.valueOf(aChar));
                result = executeOperation(result, selection, operation);
            }
        }
        return result;
    }

    private static Selection computeSegment(Table table, String segment) {
        var leftIndex = StringUtils.indexOfAny(segment, COMPARE_OPERATORS);
        var rightIndex = segment.charAt(leftIndex + 1) == '=' ? leftIndex + 2 : leftIndex + 1;

        var firstSub = segment.substring(0, leftIndex);
        var secondSub = segment.substring(rightIndex);
        var operator = segment.substring(leftIndex, rightIndex);

        if (GenericValidator.isDate(firstSub, DATE_FORMAT, true) || GenericValidator.isDate(secondSub, DATE_FORMAT, true)) {
            return executeDateComparing(table, firstSub, secondSub, operator);
        }
        return executeNumericComparing(table, firstSub, secondSub, operator);
    }

    private static Selection executeDateComparing(Table table, String firstSub, String secondSub, String operator) {
        if (table.columnNames().contains(firstSub)) {
            var firstSubColumn = table.dateColumn(firstSub);
            if (table.columnNames().contains(secondSub)) {
                var secondSubColumn = table.dateColumn(secondSub);
                switch (operator) {
                    case "=":
                        return firstSubColumn.isEqualTo(secondSubColumn);
                    case "<":
                        return firstSubColumn.isBefore(secondSubColumn);
                    case ">":
                        return firstSubColumn.isAfter(secondSubColumn);
                    case "<=":
                        return firstSubColumn.isOnOrBefore(secondSubColumn);
                    case ">=":
                        return firstSubColumn.isOnOrAfter(secondSubColumn);
                    default:
                        throw new IllegalConditionException("Condition is illegal");
                }
            } else {
                var secondSubColumn = LocalDate.parse(secondSub, DateTimeFormatter.ofPattern(DATE_FORMAT));
                switch (operator) {
                    case "=":
                        return firstSubColumn.isEqualTo(secondSubColumn);
                    case "<":
                        return firstSubColumn.isBefore(secondSubColumn);
                    case ">":
                        return firstSubColumn.isAfter(secondSubColumn);
                    case "<=":
                        return firstSubColumn.isOnOrBefore(secondSubColumn);
                    case ">=":
                        return firstSubColumn.isOnOrAfter(secondSubColumn);
                    default:
                        throw new IllegalConditionException("Condition is illegal");
                }
            }
        } else {
            var firstSubColumn = LocalDate.parse(secondSub, DateTimeFormatter.ofPattern(DATE_FORMAT));
            if (table.columnNames().contains(secondSub)) {
                var secondSubColumn = table.dateColumn(secondSub);
                switch (operator) {
                    case "=":
                        return secondSubColumn.isEqualTo(firstSubColumn);
                    case "<":
                        return secondSubColumn.isBefore(firstSubColumn);
                    case ">":
                        return secondSubColumn.isAfter(firstSubColumn);
                    case "<=":
                        return secondSubColumn.isOnOrBefore(firstSubColumn);
                    case ">=":
                        return secondSubColumn.isOnOrAfter(firstSubColumn);
                    default:
                        throw new IllegalConditionException("Condition is illegal");
                }
            } else {
                throw new IllegalConditionException("Condition is illegal");
            }
        }
    }

    private static Selection executeNumericComparing(Table table, String firstSub, String secondSub, String operator) {
        var firstSubColumn = ExpressionCalculator.executeExpression(table, firstSub);
        var secondSubColumn = ExpressionCalculator.executeExpression(table, secondSub);

        switch (operator) {
            case "=":
                return firstSubColumn.isEqualTo(secondSubColumn);
            case "<":
                return firstSubColumn.isLessThan(secondSubColumn);
            case ">":
                return firstSubColumn.isGreaterThan(secondSubColumn);
            case "<=":
                return firstSubColumn.isLessThanOrEqualTo(secondSubColumn);
            case ">=":
                return firstSubColumn.isGreaterThanOrEqualTo(secondSubColumn);
            default:
                throw new IllegalConditionException("Condition is illegal");
        }
    }

    private static Selection executeOperation(Selection first, Selection second, char operation) {
        switch (operation) {
            case '&':
                return first.and(second);
            case '|':
                return first.or(second);
            default:
                throw new IllegalConditionException("Condition can not be parsed");
        }
    }

}
