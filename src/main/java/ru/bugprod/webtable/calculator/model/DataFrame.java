package ru.bugprod.webtable.calculator.model;

public abstract class DataFrame {

    public abstract Column plus(String a, String b);

    public abstract Column plus(String a, Column b);

    public abstract Column plus(String a, double b);

    public abstract Column minus(String a, String b);

    public abstract Column minus(String a, Column b);

    public abstract Column minus(String a, double b);

    public abstract Column multiply(String a, String b);

    public abstract Column multiply(String a, Column b);

    public abstract Column multiply(String a, double b);

    public abstract Column divide(String a, String b);

    public abstract Column divide(String a, Column b);

    public abstract Column divide(String a, double b);

    public abstract void newColumn(Column result);

    public abstract boolean isKeyExists(String key);
}
