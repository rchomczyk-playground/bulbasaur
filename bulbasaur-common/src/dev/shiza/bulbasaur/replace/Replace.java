package dev.shiza.bulbasaur.replace;

import dev.shiza.bulbasaur.Query;
import java.util.List;
import org.jetbrains.annotations.ApiStatus.Experimental;
import org.jetbrains.annotations.NotNull;

@Experimental
public interface Replace extends Query {

  static Replace replace(final @NotNull String... columns) {
    return replace(List.of(columns));
  }

  static Replace replace(final @NotNull List<String> columns) {
    return new ReplaceQuery(columns);
  }

  Replace into(final @NotNull String table);

  Replace priority(final @NotNull ReplacePriority priority);
}
