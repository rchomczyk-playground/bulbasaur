package dev.shiza.bulbasaur.alter;

import dev.shiza.bulbasaur.Query;
import dev.shiza.bulbasaur.alter.AlterQueryGenerator.InstanceHolder;
import dev.shiza.bulbasaur.column.ColumnDefinition;
import dev.shiza.bulbasaur.column.ColumnDefinitions;
import java.util.Arrays;
import org.jetbrains.annotations.NotNull;

final class AlterQuery implements Alter {

  private final String table;
  private Query operation;

  AlterQuery(final String table) {
    this.table = table;
  }

  @Override
  public Alter add(final @NotNull String column, final @NotNull String definition) {
    this.operation = new Addition(column, definition);
    return this;
  }

  @Override
  public Alter add(final @NotNull String column, final @NotNull ColumnDefinition definition) {
    return add(column, definition.generate());
  }

  @Override
  public Alter add(final @NotNull String column, final @NotNull ColumnDefinition... definitions) {
    return add(
        column,
        Arrays.stream(definitions).reduce(ColumnDefinitions.empty(), ColumnDefinition::and));
  }

  @Override
  public Alter modify(final @NotNull String column, final @NotNull String definition) {
    this.operation = new Modifying(column, definition);
    return this;
  }

  @Override
  public Alter modify(final @NotNull String column, final @NotNull ColumnDefinition definition) {
    return modify(column, definition.generate());
  }

  @Override
  public Alter modify(
      final @NotNull String column, final @NotNull ColumnDefinition... definitions) {
    return modify(
        column,
        Arrays.stream(definitions).reduce(ColumnDefinitions.empty(), ColumnDefinition::and));
  }

  @Override
  public Alter change(
      final @NotNull String oldColumn,
      final @NotNull String newColumn,
      final @NotNull String definition) {
    this.operation = new Changing(oldColumn, newColumn, definition);
    return this;
  }

  @Override
  public Alter change(
      final @NotNull String oldColumn,
      final @NotNull String newColumn,
      final @NotNull ColumnDefinition definition) {
    return change(oldColumn, newColumn, definition.generate());
  }

  @Override
  public Alter change(
      final @NotNull String oldColumn,
      final @NotNull String newColumn,
      final @NotNull ColumnDefinition... definitions) {
    return change(
        oldColumn,
        newColumn,
        Arrays.stream(definitions).reduce(ColumnDefinitions.empty(), ColumnDefinition::and));
  }

  @Override
  public Alter drop(final @NotNull String column) {
    this.operation = new Dropping(column);
    return this;
  }

  @Override
  public Alter rename(final @NotNull String oldColumn, final @NotNull String newColumn) {
    this.operation = new Renaming(oldColumn, newColumn);
    return this;
  }

  @Override
  public String query() {
    return InstanceHolder.INSTANCE.generate(this);
  }

  public String getTable() {
    return table;
  }

  public Query getOperation() {
    return operation;
  }

  record Addition(String column, String definition) implements Query {

    @Override
    public String query() {
      return "ADD " + column + " " + definition;
    }
  }

  record Dropping(String column) implements Query {

    @Override
    public String query() {
      return "DROP COLUMN " + column;
    }
  }

  record Modifying(String column, String definition) implements Query {

    @Override
    public String query() {
      return "MODIFY " + column + " " + definition;
    }
  }

  record Changing(String oldColumn, String newColumn, String definition) implements Query {

    @Override
    public String query() {
      return "CHANGE " + oldColumn + " " + newColumn + " " + definition;
    }
  }

  record Renaming(String oldColumn, String newColumn) implements Query {

    @Override
    public String query() {
      return "RENAME COLUMN " + oldColumn + " TO " + newColumn;
    }
  }
}
