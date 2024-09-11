package dev.shiza.bulbasaur.insert;

import dev.shiza.bulbasaur.insert.InsertQueryGenerator.InstanceHolder;
import java.util.List;
import org.jetbrains.annotations.NotNull;

final class InsertQuery implements Insert {

  private final List<String> columns;
  private String table;

  InsertQuery(final @NotNull List<String> columns) {
    this.columns = columns;
  }

  @Override
  public InsertQuery into(final @NotNull String table) {
    this.table = table;
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
}
