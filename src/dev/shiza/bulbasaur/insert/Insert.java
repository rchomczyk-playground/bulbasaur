package dev.shiza.bulbasaur.insert;

import dev.shiza.bulbasaur.Query;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public interface Insert extends Query {

  static Insert insert(final @NotNull String... columns) {
    return insert(List.of(columns));
  }

  static Insert insert(final @NotNull List<String> columns) {
    return new InsertQuery(columns);
  }

  Insert into(final String table);
}
