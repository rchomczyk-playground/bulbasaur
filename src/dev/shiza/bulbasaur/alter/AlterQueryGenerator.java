package dev.shiza.bulbasaur.alter;

import dev.shiza.bulbasaur.QueryGenerator;
import org.jetbrains.annotations.NotNull;

final class AlterQueryGenerator implements QueryGenerator<AlterQuery> {

  @Override
  public String generate(final @NotNull AlterQuery alter) {
    return "ALTER TABLE\n" + alter.getTable() + "\n" + alter.getOperation().query();
  }

  static final class InstanceHolder {

    static final AlterQueryGenerator INSTANCE = new AlterQueryGenerator();

    private InstanceHolder() {}
  }
}
