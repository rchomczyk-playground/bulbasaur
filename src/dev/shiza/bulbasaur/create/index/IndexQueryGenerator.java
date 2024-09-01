package dev.shiza.bulbasaur.create.index;

import dev.shiza.bulbasaur.QueryGenerator;
import org.jetbrains.annotations.NotNull;

final class IndexQueryGenerator implements QueryGenerator<IndexQuery> {

  @Override
  public String generate(final @NotNull IndexQuery index) {
    final StringBuilder queryBuilder = new StringBuilder();
    queryBuilder.append("CREATE ").append(index.getIndexType().name()).append(" INDEX");
    if (index.isIfNotExists()) {
      queryBuilder.append(" IF NOT EXISTS");
    }
    queryBuilder
        .append("\n")
        .append(index.getName() != null ? index.getName() : getGeneratedIndexName(index))
        .append("\nON\n")
        .append(index.getTable())
        .append("\n(")
        .append(index.getColumn())
        .append(")");
    return queryBuilder.toString();
  }

  private String getGeneratedIndexName(final IndexQuery index) {
    return "%s_index_on_%s".formatted(index.getTable(), index.getColumn());
  }

  static final class InstanceHolder {

    static final IndexQueryGenerator INSTANCE = new IndexQueryGenerator();

    private InstanceHolder() {}
  }
}
