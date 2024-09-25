package dev.shiza.bulbasaur.insert;

import dev.shiza.bulbasaur.QueryGenerator;
import dev.shiza.bulbasaur.statement.PreparedStatementUtils;
import org.jetbrains.annotations.NotNull;

final class InsertQueryGenerator implements QueryGenerator<InsertQuery> {

  private InsertQueryGenerator() {}

  @Override
  public String generate(final @NotNull InsertQuery insert) {
    final StringBuilder queryBuilder = new StringBuilder();
    queryBuilder
        .append("INSERT INTO ")
        .append(insert.getTable())
        .append("\n(")
        .append(String.join(", ", insert.getColumns()))
        .append(")");
    appendValuesClause(queryBuilder, insert);
    return queryBuilder.toString();
  }

  private void appendValuesClause(final StringBuilder queryBuilder, final InsertQuery insert) {
    queryBuilder
        .append("\n")
        .append("VALUES (")
        .append(PreparedStatementUtils.getEscapedParameters(insert.getColumns().size()))
        .append(")");
  }

  static final class InstanceHolder {

    static final InsertQueryGenerator INSTANCE = new InsertQueryGenerator();

    private InstanceHolder() {}
  }
}
