package dev.shiza.bulbasaur.replace;

import dev.shiza.bulbasaur.QueryGenerator;
import dev.shiza.bulbasaur.statement.PreparedStatementUtils;
import org.jetbrains.annotations.NotNull;

final class ReplaceQueryGenerator implements QueryGenerator<ReplaceQuery> {

  private ReplaceQueryGenerator() {}

  @Override
  public String generate(final @NotNull ReplaceQuery replace) {
    final StringBuilder queryBuilder = new StringBuilder();
    queryBuilder.append("REPLACE ");
    appendPriorityClause(queryBuilder, replace);
    queryBuilder.append("\nINTO ").append(replace.getTable());
    appendValuesClause(queryBuilder, replace);
    return queryBuilder.toString();
  }

  private void appendPriorityClause(final StringBuilder queryBuilder, final ReplaceQuery replace) {
    if (replace.getPriority() != null) {
      queryBuilder.append(replace.getPriority());
    }
  }

  private void appendValuesClause(final StringBuilder queryBuilder, final ReplaceQuery replace) {
    queryBuilder
        .append("\n")
        .append("VALUES (")
        .append(PreparedStatementUtils.getEscapedParameters(replace.getColumns().size()))
        .append(")");
  }

  static final class InstanceHolder {

    static final ReplaceQueryGenerator INSTANCE = new ReplaceQueryGenerator();

    private InstanceHolder() {}
  }
}
