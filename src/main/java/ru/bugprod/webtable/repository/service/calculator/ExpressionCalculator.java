package ru.bugprod.webtable.repository.service.calculator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Pair;
import ru.bugprod.webtable.repository.service.calculator.model.exception.IllegalExpressionException;
import ru.bugprod.webtable.repository.service.util.VarNameFormer;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.NumericColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.numbers.NumberMapFunctions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExpressionCalculator {

    public static NumericColumn<?> executeExpression(Table dataFrame, String expression) {
        expression = expression.replace(" ", "");
        expression = expression.replace("-", "+-1*");
        expression = expression.replace("/", "*1/");
        expression = "(" + expression + ")";

        var intermediateResults = new HashMap<String, NumericColumn<?>>();
        String exp;
        while ((exp = processNestedExpr(expression, dataFrame, intermediateResults)) != null) {
            expression = exp;
        }
        return intermediateResults.remove(expression);
    }

    private static String processNestedExpr(String expr, Table dataFrame, Map<String, NumericColumn<?>> intermediateResults) {
        var indices = getBracketsIndices(expr);
        if (indices.getLeft().equals(indices.getRight()) && indices.getLeft().equals(-1)) {
            return null;
        }
        var targetExp = expr.substring(indices.getLeft() + 1, indices.getRight());
        var result = calculateBracketEntry(targetExp, dataFrame, intermediateResults);

        var varName = VarNameFormer.getVarName(dataFrame, intermediateResults.keySet());
        intermediateResults.put(varName, result);
        return StringUtils.replaceOnce(expr, "(" + targetExp + ")", varName);
    }

    private static NumericColumn<?> calculateBracketEntry(String expr, Table dataFrame, Map<String, NumericColumn<?>> intermediateResults) {
        var terms = new ArrayList<NumericColumn<?>>();

        var segments = expr.split("\\+");
        for (var segment : segments) {
            terms.add(calculateSegment(segment, dataFrame, intermediateResults));
        }
        return terms.stream().reduce(NumberMapFunctions::add).orElseThrow();
    }

    private static NumericColumn<?> calculateSegment(String expr, Table dataFrame, Map<String, NumericColumn<?>> intermediateResults) {
        NumericColumn<?> result = DoubleColumn.create("result", dataFrame.rowCount()).fillWith(1);
        var segments = expr.split("\\*");
        for (var segment : segments) {
            result = executeOperation(result, segment, dataFrame, intermediateResults);
        }
        return result;
    }

    private static NumericColumn<?> executeOperation(NumericColumn<?> result,
                                                     String segment,
                                                     Table dataFrame,
                                                     Map<String, NumericColumn<?>> intermediateResults) {
        boolean isDivide = segment.startsWith("1/");
        NumericColumn<?> computedResult;
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

    private static NumericColumn<?> executeMultiplying(NumericColumn<?> first, NumericColumn<?> second, boolean isDivide) {
        return !isDivide ? first.multiply(second) : first.divide(second);
    }

    private static NumericColumn<?> executeMultiplying(NumericColumn<?> first, Number second, boolean isDivide) {
        return !isDivide ? first.multiply(second) : first.divide(second);
    }

    private static Pair<Integer, Integer> getBracketsIndices(String expr) {
        int firstBracket = -1;
        int secondsBracket = -1;
        var chars = expr.toCharArray();
        for (var i = 0; i < chars.length; i++) {
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
