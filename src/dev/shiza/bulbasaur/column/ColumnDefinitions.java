package dev.shiza.bulbasaur.column;

import org.jetbrains.annotations.NotNull;

public final class ColumnDefinitions {

  private ColumnDefinitions() {}

  public static ColumnDefinition empty() {
    return () -> "";
  }

  public static ColumnDefinition dataType(final @NotNull String dataType) {
    return () -> dataType;
  }

  public static ColumnDefinition primaryKey() {
    return () -> "PRIMARY KEY";
  }

  public static ColumnDefinition autoIncrement() {
    return () -> "AUTO_INCREMENT";
  }

  public static ColumnDefinition references(
      final @NotNull String referencedTable, final @NotNull String referencedColumn) {
    return () -> "REFERENCES " + referencedTable + " (" + referencedColumn + ")";
  }

  public static ColumnDefinition defaults(final @NotNull Object defaultValue) {
    return () -> "DEFAULT '" + defaultValue + "'";
  }

  public static ColumnDefinition defaultsNull() {
    return () -> "NULL";
  }

  public static ColumnDefinition notNull() {
    return () -> "NOT NULL";
  }

  public static ColumnDefinition deleteCascade() {
    return () -> "ON DELETE CASCADE";
  }
}
