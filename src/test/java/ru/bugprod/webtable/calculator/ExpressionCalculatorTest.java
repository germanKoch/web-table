package ru.bugprod.webtable.calculator;

import org.junit.jupiter.api.Test;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;

public class ExpressionCalculatorTest {

    @Test
    public void baseTest() {
        DoubleColumn col1 = DoubleColumn.create("col1", 12, 27, 36, 234567, 34567183, 467831257, 12, 27, 36, 234567, 34567183, 467831257);
        DoubleColumn col2 = DoubleColumn.create("col2", 1123, 213, 312312, 123, 567125671, 432461234, 1123, 213, 312312, 123, 567125671, 432461234);
        DoubleColumn col3 = DoubleColumn.create("col3", 1123, 213, 3112, 112323, 6671, 4132434, 1123, 2213, 312, 1223, 571, 43234);
        var table = Table.create(col1, col2, col3);
        var result = ExpressionCalculator.executeExpression(table, "(col1 + col2)/col3 - (col1*col2)");
        System.out.println(result.print());

        System.out.println("TEST");
        for (int i = 0; i < col1.size(); i++) {
            var a1 = col1.get(i);
            var a2 = col2.get(i);
            var a3 = col3.get(i);
            System.out.println((a1+a2)/a3 - a1*a2);
        }
    }

}
