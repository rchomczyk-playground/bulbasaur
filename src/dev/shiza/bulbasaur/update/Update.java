package dev.shiza.bulbasaur.update;

import dev.shiza.bulbasaur.Query;
import dev.shiza.bulbasaur.condition.Condition;

public interface Update extends Query {

  static Update update(final String table) {
    return new UpdateQuery(table);
  }

  Update set(final String column);

  Update where(final Condition condition);
}
