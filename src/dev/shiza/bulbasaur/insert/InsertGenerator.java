package dev.shiza.bulbasaur.insert;

import dev.shiza.bulbasaur.QueryGenerator;

public final class InsertGenerator implements QueryGenerator<Insert> {

  @Override
  public String generate(final Insert insert) {
    final StringBuilder queryBuilder = new StringBuilder();
    queryBuilder
        .append("INSERT INTO ")
        .append(insert.table())
        .append("\n(")
        .append(String.join(", ", insert.columns()))
        .append(")");

    appendValuesClause(queryBuilder, insert);

    return queryBuilder.append(";").toString();
  }

  private void appendValuesClause(final StringBuilder queryBuilder, final Insert insert) {
    final String generatedValues = "?, ".repeat(insert.columns().size());
    final String formattedValues = generatedValues.substring(0, generatedValues.length() - 2);
    queryBuilder.append("\n").append("VALUES (").append(formattedValues).append(")");
  }
}
