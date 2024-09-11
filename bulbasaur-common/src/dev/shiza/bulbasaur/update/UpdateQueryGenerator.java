package dev.shiza.bulbasaur.update;

import dev.shiza.bulbasaur.QueryGenerator;
import dev.shiza.bulbasaur.condition.Condition;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

final class UpdateQueryGenerator implements QueryGenerator<UpdateQuery> {

  private UpdateQueryGenerator() {}

  @Override
  public String generate(final @NotNull UpdateQuery update) {
    final StringBuilder queryBuilder = new StringBuilder();
    queryBuilder.append("UPDATE ").append(update.table());
    appendSetClauses(queryBuilder, update);
    appendWhereClause(queryBuilder, update.whereCondition());
    return queryBuilder.toString();
  }

  private void appendSetClauses(final StringBuilder queryBuilder, final UpdateQuery update) {
    queryBuilder
        .append("\nSET ")
        .append(
            update.columns().stream()
                .map(column -> column + " = " + "?")
                .collect(Collectors.joining(", ")));
  }

  private void appendWhereClause(final StringBuilder queryBuilder, final Condition condition) {
    final String generatedClause = condition.generate();
    if (!generatedClause.isEmpty()) {
      queryBuilder.append("\nWHERE ").append(generatedClause);
    }
  }

  static final class InstanceHolder {

    static final UpdateQueryGenerator INSTANCE = new UpdateQueryGenerator();

    private InstanceHolder() {}
  }
}
