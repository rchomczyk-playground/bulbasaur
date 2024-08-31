package dev.shiza.bulbasaur.insert;

import dev.shiza.bulbasaur.QueryGenerators;
import java.util.List;

public final class Insert {

  private final List<String> columns;
  private String table;

  Insert(final List<String> columns) {
    this.columns = columns;
  }

  public Insert into(final String table) {
    this.table = table;
    return this;
  }

  public String query() {
    return QueryGenerators.insert().generate(this);
  }

  String table() {
    return table;
  }

  List<String> columns() {
    return columns;
  }
}
