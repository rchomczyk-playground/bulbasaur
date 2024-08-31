package dev.shiza.bulbasaur.update;

public final class UpdateDsl {

  private UpdateDsl() {}

  public static Update update(final String table) {
    return new Update(table);
  }
}
