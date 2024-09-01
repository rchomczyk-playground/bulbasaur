package dev.shiza.bulbasaur.alter;

import dev.shiza.bulbasaur.Query;
import dev.shiza.bulbasaur.column.ColumnDefinition;
import org.jetbrains.annotations.NotNull;

public interface Alter extends Query {

  static Alter alter(final @NotNull String table) {
    return new AlterQuery(table);
  }

  Alter add(final @NotNull String column, final @NotNull String definition);

  Alter add(final @NotNull String column, final @NotNull ColumnDefinition definition);

  Alter add(final @NotNull String column, final @NotNull ColumnDefinition... definitions);

  Alter drop(final @NotNull String column);

  Alter rename(final @NotNull String oldColumn, final @NotNull String newColumn);
}
