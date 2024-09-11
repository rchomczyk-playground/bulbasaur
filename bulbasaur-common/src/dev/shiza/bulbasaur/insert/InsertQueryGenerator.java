package dev.shiza.bulbasaur.insert;

import dev.shiza.bulbasaur.QueryGenerator;
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
    final String generatedValues = "?, ".repeat(insert.getColumns().size());
    final String formattedValues = generatedValues.substring(0, generatedValues.length() - 2);
    queryBuilder.append("\n").append("VALUES (").append(formattedValues).append(")");
  }

  static final class InstanceHolder {

    static final InsertQueryGenerator INSTANCE = new InsertQueryGenerator();

    private InstanceHolder() {}
  }
}
