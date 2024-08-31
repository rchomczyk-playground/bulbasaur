package dev.shiza.bulbasaur.insert;

import java.util.List;

public final class InsertDsl {

  private InsertDsl() {}

  public static Insert insert(final String... columns) {
    return new Insert(List.of(columns));
  }
}
