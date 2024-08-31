package dev.shiza.bulbasaur.select;

import dev.shiza.bulbasaur.QueryGenerator;
import dev.shiza.bulbasaur.condition.Condition;
import dev.shiza.bulbasaur.select.SelectQuery.Join;
import org.jetbrains.annotations.NotNull;

final class SelectQueryGenerator implements QueryGenerator<SelectQuery> {

  private SelectQueryGenerator() {}

  @Override
  public String generate(final @NotNull SelectQuery select) {
    final StringBuilder queryBuilder = new StringBuilder();
    queryBuilder.append("SELECT ");
    appendDistinctClause(queryBuilder, select);
    queryBuilder
        .append(String.join(", ", select.getFields()))
        .append("\nFROM ")
        .append(select.getTable());
    appendJoinClauses(queryBuilder, select);
    appendWhereClause(queryBuilder, select.getWhereCondition());
    appendGroupByClause(queryBuilder, select);
    appendHavingClause(queryBuilder, select.getHavingCondition());
    appendOrderByClause(queryBuilder, select);
    appendLimitClause(queryBuilder, select);
    return queryBuilder.toString();
  }

  private void appendDistinctClause(final StringBuilder queryBuilder, final SelectQuery select) {
    if (select.isDistinct()) {
      queryBuilder.append("DISTINCT ");
    }
  }

  private void appendJoinClauses(final StringBuilder queryBuilder, final SelectQuery select) {
    select.getJoins().forEach(join -> appendJoinClause(queryBuilder, join));
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

  private void appendGroupByClause(final StringBuilder queryBuilder, final SelectQuery select) {
    if (select.getGroupByFields() != null && select.getGroupByFields().length > 0) {
      queryBuilder.append("\nGROUP BY ").append(String.join(", ", select.getGroupByFields()));
    }
  }

  private void appendHavingClause(
      final StringBuilder queryBuilder, final Condition havingCondition) {
    final String generatedClause = havingCondition.generate();
    if (!generatedClause.isEmpty()) {
      queryBuilder.append("\nHAVING ").append(generatedClause);
    }
  }

  private void appendOrderByClause(final StringBuilder queryBuilder, final SelectQuery select) {
    if (select.getOrderByFields() != null && select.getOrderByFields().length > 0) {
      queryBuilder.append("\nORDER BY ").append(String.join(", ", select.getOrderByFields()));
      queryBuilder.append(select.isOrderByAscending() ? " ASC" : " DESC");
    }
  }

  private void appendLimitClause(final StringBuilder queryBuilder, final SelectQuery select) {
    if (select.getLimit() != null) {
      queryBuilder.append("\nLIMIT ").append(select.getLimit());
    }
  }

  static final class InstanceHolder {

    static final SelectQueryGenerator INSTANCE = new SelectQueryGenerator();

    private InstanceHolder() {}
  }
}
