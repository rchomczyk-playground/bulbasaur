package dev.shiza.bulbasaur.delete;

import dev.shiza.bulbasaur.Query;
import dev.shiza.bulbasaur.condition.Condition;
import org.jetbrains.annotations.NotNull;

public interface Delete extends Query {

  static Delete delete() {
    return new DeleteQuery();
  }

  Delete from(final @NotNull String table);

  Delete where(final @NotNull Condition condition);
}
