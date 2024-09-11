package dev.shiza.bulbasaur.delete;

import dev.shiza.bulbasaur.condition.Condition;
import dev.shiza.bulbasaur.delete.DeleteQueryGenerator.InstanceHolder;
import org.jetbrains.annotations.NotNull;

final class DeleteQuery implements Delete {

  private String table;
  private Condition whereCondition;

  DeleteQuery() {}

  @Override
  public DeleteQuery from(final @NotNull String table) {
    this.table = table;
    return this;
  }

  @Override
  public DeleteQuery where(final @NotNull Condition condition) {
    this.whereCondition = condition;
    return this;
  }

  @Override
  public String query() {
    return InstanceHolder.INSTANCE.generate(this);
  }

  public String getTable() {
    return table;
  }

  public Condition getWhereCondition() {
    return whereCondition;
  }
}
