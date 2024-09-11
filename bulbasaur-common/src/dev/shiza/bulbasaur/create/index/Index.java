package dev.shiza.bulbasaur.create.index;

import dev.shiza.bulbasaur.Query;
import org.jetbrains.annotations.NotNull;

public interface Index extends Query {

  static Index index(final @NotNull IndexType type) {
    return new IndexQuery(type);
  }

  Index ifNotExists();

  Index name(final @NotNull String name);

  Index table(final @NotNull String table);

  Index column(final @NotNull String column);
}
