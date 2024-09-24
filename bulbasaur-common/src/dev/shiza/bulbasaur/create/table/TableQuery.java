package dev.shiza.bulbasaur.create.table;

import dev.shiza.bulbasaur.column.ColumnDefinition;
import dev.shiza.bulbasaur.column.ColumnDefinitions;
import dev.shiza.bulbasaur.condition.Condition;
import dev.shiza.bulbasaur.create.table.TableQueryGenerator.InstanceHolder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.NotNull;

final class TableQuery implements Table {

  private final String name;
  private final List<String> primaryKeys = new ArrayList<>();
  private final List<Column> columns = new ArrayList<>();
  private final List<Constraint> constraints = new ArrayList<>();
  private boolean ifNotExists;

  TableQuery(final String name) {
    this.name = name;
  }

  @Override
  public Table ifNotExists() {
    this.ifNotExists = true;
    return this;
  }

  @Override
  public Table column(final @NotNull String name, final @NotNull String definition) {
    columns.add(new Column(name, definition));
    return this;
  }

  @Override
  public Table column(final @NotNull String name, final @NotNull ColumnDefinition definition) {
    return column(name, definition.generate());
  }

  @Override
  public Table column(final @NotNull String name, final @NotNull ColumnDefinition... definitions) {
    return column(
        name, Arrays.stream(definitions).reduce(ColumnDefinitions.empty(), ColumnDefinition::and));
  }

  @Override
  public Table primaryKeys(final @NotNull String... columns) {
    primaryKeys.addAll(List.of(columns));
    return this;
  }

  @Override
  public Table constraint(final @NotNull String name, final @NotNull String declaration) {
    constraints.add(new Constraint(name, declaration));
    return this;
  }

  @Override
  public Table constraint(final @NotNull String name, final @NotNull Condition condition) {
    return constraint(name, "CHECK (\n" + condition.generate() + "\n)");
  }

  @Override
  public String query() {
    return InstanceHolder.INSTANCE.generate(this);
  }

  public String getName() {
    return name;
  }

  public boolean isIfNotExists() {
    return ifNotExists;
  }

  public List<Column> getColumns() {
    return columns;
  }

  public List<String> getPrimaryKeys() {
    return primaryKeys;
  }

  public List<Constraint> getConstraints() {
    return constraints;
  }

  record Column(String name, String declaration) {}

  record Constraint(String name, String declaration) {}
}
