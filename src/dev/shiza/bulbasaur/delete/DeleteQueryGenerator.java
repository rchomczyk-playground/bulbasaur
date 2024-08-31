package dev.shiza.bulbasaur.delete;

import dev.shiza.bulbasaur.QueryGenerator;
import dev.shiza.bulbasaur.condition.Condition;
import org.jetbrains.annotations.NotNull;

final class DeleteQueryGenerator implements QueryGenerator<DeleteQuery> {

  private DeleteQueryGenerator() {}

  @Override
  public String generate(final @NotNull DeleteQuery delete) {
    final StringBuilder queryBuilder = new StringBuilder();
    queryBuilder.append("DELETE\nFROM ").append(delete.getTable());
    appendWhereClause(queryBuilder, delete.getWhereCondition());
    return queryBuilder.toString();
  }

  private void appendWhereClause(final StringBuilder queryBuilder, final Condition condition) {
    final String generatedClause = condition.generate();
    if (!generatedClause.isEmpty()) {
      queryBuilder.append("\nWHERE ").append(generatedClause);
    }
  }

  static final class InstanceHolder {

    static final DeleteQueryGenerator INSTANCE = new DeleteQueryGenerator();

    private InstanceHolder() {}
  }
}
