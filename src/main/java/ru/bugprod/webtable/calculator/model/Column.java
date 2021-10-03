package ru.bugprod.webtable.calculator.model;

public abstract class Column {

    public abstract Column plus(Column b);

    public abstract Column minus(Column b);

    public abstract Column multiply(Column b);

    public abstract Column divide(Column b);
}
