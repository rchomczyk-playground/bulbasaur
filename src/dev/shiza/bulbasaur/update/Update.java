package dev.shiza.bulbasaur.update;

import dev.shiza.bulbasaur.QueryGenerators;
import dev.shiza.bulbasaur.condition.Condition;
import dev.shiza.bulbasaur.condition.Conditions;
import java.util.ArrayList;
import java.util.List;

public final class Update {

  private final String table;
  private final List<String> columns = new ArrayList<>();
  private Condition whereCondition = Conditions.empty();

  Update(final String table) {
    this.table = table;
  }

  public Update set(final String column) {
    this.columns.add(column);
    return this;
  }

  public Update where(final Condition condition) {
    this.whereCondition = condition;
    return this;
  }

  public String query() {
    return QueryGenerators.update().generate(this);
  }

  String table() {
    return table;
  }

  List<String> columns() {
    return columns;
  }

  Condition whereCondition() {
    return whereCondition;
  }
}
