package dev.shiza.bulbasaur.condition;

public final class Conditions {

  private Conditions() {}

  public static Condition empty() {
    return () -> "";
  }

  public static Condition eq(final String field, final Object value) {
    return () -> field + " = " + value;
  }

  public static Condition eq(final String field) {
    return eq(field, "?");
  }

  public static Condition ne(final String field, final Object value) {
    return () -> field + " != " + value;
  }

  public static Condition ne(final String field) {
    return ne(field, "?");
  }

  public static Condition gt(final String field, final Object value) {
    return () -> field + " > " + value;
  }

  public static Condition gt(final String field) {
    return gt(field, "?");
  }

  public static Condition gte(final String field, final Object value) {
    return () -> field + " >= " + value;
  }

  public static Condition gte(final String field) {
    return gte(field, "?");
  }

  public static Condition lt(final String field, final Object value) {
    return () -> field + " < " + value;
  }

  public static Condition lt(final String field) {
    return lt(field, "?");
  }

  public static Condition lte(final String field, final Object value) {
    return () -> field + " <= " + value;
  }

  public static Condition lte(final String field) {
    return lte(field, "?");
  }
}
