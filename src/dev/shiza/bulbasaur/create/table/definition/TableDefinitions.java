package dev.shiza.bulbasaur.create.table.definition;

import org.jetbrains.annotations.NotNull;

public final class TableDefinitions {

  private TableDefinitions() {}

  public static TableDefinition empty() {
    return () -> "";
  }

  public static TableDefinition dataType(final @NotNull String dataType) {
    return () -> dataType;
  }

  public static TableDefinition primaryKey() {
    return () -> "PRIMARY KEY";
  }

  public static TableDefinition autoIncrement() {
    return () -> "AUTO_INCREMENT";
  }

  public static TableDefinition references(
      final @NotNull String referencedTable, final @NotNull String referencedColumn) {
    return () -> "REFERENCES " + referencedTable + " (" + referencedColumn + ")";
  }

  public static TableDefinition defaults(final @NotNull Object defaultValue) {
    return () -> "DEFAULT '" + defaultValue + "'";
  }

  public static TableDefinition defaultsNull() {
    return () -> "NULL";
  }

  public static TableDefinition notNull() {
    return () -> "NOT NULL";
  }

  public static TableDefinition deleteCascade() {
    return () -> "ON DELETE CASCADE";
  }
}
