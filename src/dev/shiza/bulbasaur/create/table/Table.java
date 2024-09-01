package dev.shiza.bulbasaur.create.table;

import dev.shiza.bulbasaur.Query;
import dev.shiza.bulbasaur.condition.Condition;
import dev.shiza.bulbasaur.create.table.definition.TableDefinition;
import org.jetbrains.annotations.NotNull;

public interface Table extends Query {

  static Table table(final @NotNull String name) {
    return new TableQuery(name);
  }

  Table ifNotExists();

  Table column(final @NotNull String name, final @NotNull String definition);

  Table column(final @NotNull String name, final @NotNull TableDefinition definition);

  Table column(final @NotNull String name, final @NotNull TableDefinition... definitions);

  Table constraint(final @NotNull String name, final @NotNull Condition condition);
}
