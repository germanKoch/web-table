package ru.bugprod.webtable.calculator;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Pair;
import ru.bugprod.webtable.calculator.model.exception.IllegalExpressionException;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.NumericColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.numbers.NumberMapFunctions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressionCalculator {

    public static DoubleColumn executeExpression(Table dataFrame, String expression) {
        expression = expression.replace(" ", "");
        expression = expression.replace("-", "+-1*");
        expression = expression.replace("/", "*1/");
        expression = "(" + expression + ")";

        Map<String, DoubleColumn> intermediateResults = new HashMap<>();
        String exp;
        while ((exp= processNestedExpr(expression, dataFrame, intermediateResults)) != null) {
            expression = exp;
        }
        return intermediateResults.remove(expression);
    }

    private static String processNestedExpr(String expr, Table dataFrame, Map<String, DoubleColumn> intermediateResults) {
        Pair<Integer, Integer> indices = getBracketsIndices(expr);
        if (indices.getLeft().equals(indices.getRight()) && indices.getLeft().equals(-1)) {
            return null;
        }
        String targetExp = expr.substring(indices.getLeft() + 1, indices.getRight());
        DoubleColumn result = calculateBracketEntry(targetExp, dataFrame, intermediateResults);

        String varName = getVarName(dataFrame, intermediateResults);
        intermediateResults.put(varName, result);
        return StringUtils.replaceOnce(expr, "(" + targetExp + ")", varName);
    }

    private static DoubleColumn calculateBracketEntry(String expr, Table dataFrame, Map<String, DoubleColumn> intermediateResults) {
        List<DoubleColumn> terms = new ArrayList<>();

        var segments = expr.split("\\+");
        for (var segment : segments) {
            terms.add(calculateSegment(segment, dataFrame, intermediateResults));
        }
        return terms.stream().reduce(NumberMapFunctions::add).orElseThrow();
    }

    private static DoubleColumn calculateSegment(String expr, Table dataFrame, Map<String, DoubleColumn> intermediateResults) {
        DoubleColumn result = DoubleColumn.create("result", dataFrame.rowCount()).fillWith(1);
        var segments = expr.split("\\*");
        for (var segment : segments) {
            result = executeOperation(result, segment, dataFrame, intermediateResults);
        }
        return result;
    }

    private static DoubleColumn executeOperation(DoubleColumn result,
                                                 String segment,
                                                 Table dataFrame,
                                                 Map<String, DoubleColumn> intermediateResults) {
        boolean isDivide = segment.startsWith("1/");
        DoubleColumn computedResult;
        if (isDivide) {
            segment = segment.substring(2);
        }
        if (NumberUtils.isCreatable(segment)) {
            var value = Double.valueOf(segment);
            computedResult = executeMultiplying(result, value, isDivide);
        } else if (dataFrame.columnNames().contains(segment)) {
            computedResult = executeMultiplying(result, dataFrame.numberColumn(segment), isDivide);
        } else if (intermediateResults.containsKey(segment)) {
            computedResult = executeMultiplying(result, intermediateResults.remove(segment), isDivide);
        } else {
            throw new IllegalExpressionException("EllegalExpression");
        }
        return computedResult;
    }

    private static DoubleColumn executeMultiplying(DoubleColumn first, NumericColumn<?> second, boolean isDivide) {
        return !isDivide ? first.multiply(second) : first.divide(second);
    }

    private static DoubleColumn executeMultiplying(DoubleColumn first, Number second, boolean isDivide) {
        return !isDivide ? first.multiply(second) : first.divide(second);
    }

    private static String getVarName(Table dataFrame, Map<String, DoubleColumn> intermediateResults) {
        var varName = RandomStringUtils.randomAlphabetic(1);
        while (intermediateResults.containsKey(varName) || dataFrame.columnNames().contains(varName)) {
            varName = RandomStringUtils.randomAlphabetic(1);
        }
        return varName;
    }

    private static Pair<Integer, Integer> getBracketsIndices(String expr) {
        int firstBracket = -1;
        int secondsBracket = -1;
        var chars = expr.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '(') {
                firstBracket = i;
            } else if (chars[i] == ')') {
                secondsBracket = i;
                break;
            }
        }
        if (firstBracket * secondsBracket < 0) {
            throw new IllegalExpressionException("Brackets has illegal position");
        }
        return Pair.of(firstBracket, secondsBracket);
    }
}
