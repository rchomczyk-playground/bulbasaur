package dev.shiza.bulbasaur.select;

import static java.util.Collections.singletonList;

import dev.shiza.bulbasaur.Query;
import dev.shiza.bulbasaur.condition.Condition;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public interface Select extends Query {

  static Select select(final @NotNull String... fields) {
    return select(List.of(fields));
  }

  static Select select(final @NotNull List<String> fields) {
    return new SelectQuery(fields.isEmpty() ? SelectVariables.WILDCARD : fields);
  }

  Select distinct();

  Select from(final @NotNull String table);

  Select join(final @NotNull String table, final Condition condition);

  Select where(final @NotNull Condition condition);

  Select groupBy(final @NotNull String... fields);

  Select having(final @NotNull Condition condition);

  Select orderBy(final @NotNull String... fields);

  Select ascending();

  Select descending();

  Select limit(final int limit);

  final class SelectVariables {

    private static final List<String> WILDCARD = singletonList("*");

    private SelectVariables() {}
  }
}
