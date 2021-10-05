package ru.bugprod.webtable.repository.service.filter;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import ru.bugprod.webtable.repository.service.calculator.ExpressionCalculator;
import ru.bugprod.webtable.repository.service.filter.model.exception.IllegalConditionException;
import ru.bugprod.webtable.repository.service.util.VarNameFormer;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.selection.Selection;

import java.util.HashMap;

// col1 > col2*col3 & col2 <= 2
//TODO: поддержать скобочки
//TODO: сделать правильную последвоательность операций
public class ConditionExpressionCalculator {

    private static final String[] COMPARE_OPERATORS = {"=", ">", "<", "<=", ">="};
    private static final char[] BOOL_OPERATORS = {'&', '|'};

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
        var firstSub = ExpressionCalculator.executeExpression(table, segment.substring(0, leftIndex));
        var secondSub = ExpressionCalculator.executeExpression(table, segment.substring(rightIndex));
        var operator = segment.substring(leftIndex, rightIndex);

        switch (operator) {
            case "=":
                return firstSub.isEqualTo(secondSub);
            case "<":
                return firstSub.isLessThan(secondSub);
            case ">":
                return firstSub.isGreaterThan(secondSub);
            case "<=":
                return firstSub.isLessThanOrEqualTo(secondSub);
            case ">=":
                return firstSub.isGreaterThanOrEqualTo(secondSub);
            default:
                throw new IllegalConditionException("Condition is illegal");
        }
    }

    private static Selection executeOperation(Selection first, Selection second, char operation) {
        switch (operation) {
            case '&': return first.and(second);
            case '|': return first.or(second);
            default: throw new IllegalConditionException("Condition can not be parsed");
        }
    }

    public static void main(String[] args) {
        var col1 = DoubleColumn.create("a", 1, 2, 3);
        var col2 = DoubleColumn.create("b", 3, 4, 5);
        var col3 = DoubleColumn.create("c", 3, 4, 5);
        var table = Table.create(col1, col2, col3);
        getCondition(table, "a*2 > 2 & b > a | 3*(c*b + 1) <= a");
    }

}
