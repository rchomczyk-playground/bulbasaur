package dev.shiza.bulbasaur.create.index;

import dev.shiza.bulbasaur.create.index.IndexQueryGenerator.InstanceHolder;
import org.jetbrains.annotations.NotNull;

final class IndexQuery implements Index {

  private final IndexType indexType;
  private boolean ifNotExists;
  private String name;
  private String table;
  private String column;

  public IndexQuery(final IndexType indexType) {
    this.indexType = indexType;
  }

  @Override
  public Index ifNotExists() {
    this.ifNotExists = true;
    return this;
  }

  @Override
  public Index name(final @NotNull String name) {
    this.name = name;
    return this;
  }

  @Override
  public Index table(final @NotNull String table) {
    this.table = table;
    return this;
  }

  @Override
  public Index column(final @NotNull String column) {
    this.column = column;
    return this;
  }

  @Override
  public String query() {
    return InstanceHolder.INSTANCE.generate(this);
  }

  public IndexType getIndexType() {
    return indexType;
  }

  public boolean isIfNotExists() {
    return ifNotExists;
  }

  public String getName() {
    return name;
  }

  public String getTable() {
    return table;
  }

  public String getColumn() {
    return column;
  }
}
