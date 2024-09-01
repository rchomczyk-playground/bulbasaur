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
  public Table constraint(final @NotNull String name, final @NotNull Condition condition) {
    constraints.add(new Constraint(name, condition));
    return this;
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

  public List<Constraint> getConstraints() {
    return constraints;
  }

  record Column(String name, String declaration) {}

  record Constraint(String name, Condition condition) {}
}
