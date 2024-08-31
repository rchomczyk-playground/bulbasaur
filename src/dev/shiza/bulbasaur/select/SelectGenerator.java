package dev.shiza.bulbasaur.select;

import dev.shiza.bulbasaur.QueryGenerator;
import dev.shiza.bulbasaur.condition.Condition;
import dev.shiza.bulbasaur.select.Select.Join;

public final class SelectGenerator implements QueryGenerator<Select> {

  @Override
  public String generate(final Select select) {
    final StringBuilder queryBuilder = new StringBuilder();
    queryBuilder
        .append("SELECT ")
        .append(String.join(", ", select.fields()))
        .append("\nFROM ")
        .append(select.table());

    appendJoinClauses(queryBuilder, select);
    appendWhereClause(queryBuilder, select.whereCondition());
    appendGroupByClause(queryBuilder, select);
    appendHavingClause(queryBuilder, select.havingCondition());
    appendOrderByClause(queryBuilder, select);
    appendLimitClause(queryBuilder, select);

    return queryBuilder.append(";").toString();
  }

  private void appendJoinClauses(final StringBuilder queryBuilder, final Select select) {
    select.joins().forEach(join -> appendJoinClause(queryBuilder, join));
  }

  private void appendJoinClause(final StringBuilder queryBuilder, final Join join) {
    queryBuilder
        .append("\nLEFT JOIN ")
        .append(join.target())
        .append(" ON ")
        .append(join.condition().generate());
  }

  private void appendWhereClause(final StringBuilder queryBuilder, final Condition condition) {
    final String generatedClause = condition.generate();
    if (!generatedClause.isEmpty()) {
      queryBuilder.append("\nWHERE ").append(generatedClause);
    }
  }

  private void appendGroupByClause(final StringBuilder queryBuilder, final Select select) {
    if (select.groupByFields() != null && select.groupByFields().length > 0) {
      queryBuilder.append("\nGROUP BY ").append(String.join(", ", select.groupByFields()));
    }
  }

  private void appendHavingClause(
      final StringBuilder queryBuilder, final Condition havingCondition) {
    final String generatedClause = havingCondition.generate();
    if (!generatedClause.isEmpty()) {
      queryBuilder.append("\nHAVING ").append(generatedClause);
    }
  }

  private void appendOrderByClause(final StringBuilder queryBuilder, final Select select) {
    if (select.orderByFields() != null && select.orderByFields().length > 0) {
      queryBuilder.append("\nORDER BY ").append(String.join(", ", select.orderByFields()));
      queryBuilder.append(select.orderByAscending() ? " ASC" : " DESC");
    }
  }

  private void appendLimitClause(final StringBuilder queryBuilder, final Select select) {
    if (select.limit() != null) {
      queryBuilder.append("\nLIMIT ").append(select.limit());
    }
  }
}
