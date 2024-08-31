package dev.shiza.bulbasaur.select;

import java.util.List;

public final class SelectDsl {

  private SelectDsl() {}

  public static Select select(final String... fields) {
    return new Select(List.of(fields));
  }
}
