package dev.shiza.bulbasaur.update;

import dev.shiza.bulbasaur.condition.Condition;
import dev.shiza.bulbasaur.condition.Conditions;
import dev.shiza.bulbasaur.update.UpdateQueryGenerator.InstanceHolder;
import java.util.ArrayList;
import java.util.List;

final class UpdateQuery implements Update {

  private final String table;
  private final List<String> columns = new ArrayList<>();
  private Condition whereCondition = Conditions.empty();

  UpdateQuery(final String table) {
    this.table = table;
  }

  @Override
  public Update set(final String column) {
    this.columns.add(column);
    return this;
  }

  @Override
  public Update where(final Condition condition) {
    this.whereCondition = condition;
    return this;
  }

  @Override
  public String query() {
    return InstanceHolder.INSTANCE.generate(this);
  }

  public String table() {
    return table;
  }

  public List<String> columns() {
    return columns;
  }

  public Condition whereCondition() {
    return whereCondition;
  }
}
