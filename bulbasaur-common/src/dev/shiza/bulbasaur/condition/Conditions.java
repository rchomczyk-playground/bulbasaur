package dev.shiza.bulbasaur.condition;

import dev.shiza.bulbasaur.statement.PreparedStatementUtils;

public final class Conditions {

  private static final String ESCAPED_VALUE = "?";

  private Conditions() {}

  public static Condition empty() {
    return () -> "";
  }

  public static Condition in(final String field, final int size) {
    return () -> field + " IN " + "(" + PreparedStatementUtils.getEscapedParameters(size) + ")";
  }

  public static Condition eq(final String field, final Object value) {
    return () -> field + " = " + value;
  }

  public static Condition eq(final String field) {
    return eq(field, ESCAPED_VALUE);
  }

  public static Condition ne(final String field, final Object value) {
    return () -> field + " != " + value;
  }

  public static Condition ne(final String field) {
    return ne(field, ESCAPED_VALUE);
  }

  public static Condition gt(final String field, final Object value) {
    return () -> field + " > " + value;
  }

  public static Condition gt(final String field) {
    return gt(field, ESCAPED_VALUE);
  }

  public static Condition gte(final String field, final Object value) {
    return () -> field + " >= " + value;
  }

  public static Condition gte(final String field) {
    return gte(field, ESCAPED_VALUE);
  }

  public static Condition lt(final String field, final Object value) {
    return () -> field + " < " + value;
  }

  public static Condition lt(final String field) {
    return lt(field, ESCAPED_VALUE);
  }

  public static Condition lte(final String field, final Object value) {
    return () -> field + " <= " + value;
  }

  public static Condition lte(final String field) {
    return lte(field, ESCAPED_VALUE);
  }

  public static Condition between(final String field, final Object min, final Object max) {
    return () -> field + " BETWEEN " + min + " AND " + max;
  }

  public static Condition between(final String field) {
    return between(field, ESCAPED_VALUE, ESCAPED_VALUE);
  }

  public static Condition like(final String field, final String pattern) {
    return () -> field + " LIKE " + pattern;
  }

  public static Condition like(final String field) {
    return like(field, ESCAPED_VALUE);
  }
}
