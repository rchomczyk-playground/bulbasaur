package dev.shiza.bulbasaur.generator.query;

public enum Query {
  CREATE,
  SELECT,
  INSERT,
  UPDATE,
  DELETE,
  REPLACE;

  public static Query from(final String input) {
    try {
      return Query.valueOf(input.toUpperCase());
    } catch (final IllegalArgumentException exception) {
      return null;
    }
  }
}
