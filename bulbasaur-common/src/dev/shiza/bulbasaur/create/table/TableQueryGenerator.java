package dev.shiza.bulbasaur.create.table;

import dev.shiza.bulbasaur.QueryGenerator;
import dev.shiza.bulbasaur.create.table.TableQuery.Column;
import dev.shiza.bulbasaur.create.table.TableQuery.Constraint;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

final class TableQueryGenerator implements QueryGenerator<TableQuery> {

  private TableQueryGenerator() {}

  @Override
  public String generate(final @NotNull TableQuery table) {
    final StringBuilder queryBuilder = new StringBuilder();
    queryBuilder.append("CREATE TABLE");
    if (table.isIfNotExists()) {
      queryBuilder.append(" IF NOT EXISTS");
    }
    queryBuilder.append(" ").append(table.getName()).append("\n(\n");
    appendColumnClauses(queryBuilder, table);
    if (!table.getConstraints().isEmpty()) {
      queryBuilder.append(",\n");
      appendConstraintClauses(queryBuilder, table);
    }
    queryBuilder.append("\n)");
    return queryBuilder.toString();
  }

  private void appendColumnClauses(final StringBuilder queryBuilder, final TableQuery table) {
    queryBuilder.append(
        table.getColumns().stream()
            .map(this::getGeneratedColumnClause)
            .collect(Collectors.joining(",\n")));
  }

  private void appendConstraintClauses(final StringBuilder queryBuilder, final TableQuery table) {
    queryBuilder.append(
        table.getConstraints().stream()
            .map(this::getGeneratedConstraintClause)
            .collect(Collectors.joining(",\n")));
  }

  private String getGeneratedColumnClause(final Column column) {
    return column.name() + " " + column.declaration();
  }

  private String getGeneratedConstraintClause(final Constraint constraint) {
    return "CONSTRAINT\n" + constraint.name() + "_constraint" + "\n" + constraint.declaration();
  }

  static final class InstanceHolder {

    static final TableQueryGenerator INSTANCE = new TableQueryGenerator();

    private InstanceHolder() {}
  }
}
