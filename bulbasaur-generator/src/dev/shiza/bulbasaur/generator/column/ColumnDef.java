package dev.shiza.bulbasaur.generator.column;

public record ColumnDef(String name, String definition, ColumnScope scope) {

  public static ColumnDef from(final Column column, final String fieldName) {
    return new ColumnDef(
        column.name().isBlank() ? fieldName : column.name(), column.definition(), column.scope());
  }
}
