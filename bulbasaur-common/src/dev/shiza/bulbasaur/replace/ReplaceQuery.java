package dev.shiza.bulbasaur.replace;

import dev.shiza.bulbasaur.replace.ReplaceQueryGenerator.InstanceHolder;
import java.util.List;
import org.jetbrains.annotations.NotNull;

final class ReplaceQuery implements Replace {

  private final List<String> columns;
  private String table;
  private ReplacePriority priority;

  ReplaceQuery(final @NotNull List<String> columns) {
    this.columns = columns;
  }

  @Override
  public Replace into(final @NotNull String table) {
    this.table = table;
    return this;
  }

  @Override
  public Replace priority(final @NotNull ReplacePriority priority) {
    this.priority = priority;
    return this;
  }

  @Override
  public String query() {
    return InstanceHolder.INSTANCE.generate(this);
  }

  public String getTable() {
    return table;
  }

  public List<String> getColumns() {
    return columns;
  }

  public ReplacePriority getPriority() {
    return priority;
  }
}
