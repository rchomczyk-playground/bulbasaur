package dev.shiza.bulbasaur;

import dev.shiza.bulbasaur.insert.Insert;
import dev.shiza.bulbasaur.insert.InsertGenerator;
import dev.shiza.bulbasaur.select.Select;
import dev.shiza.bulbasaur.select.SelectGenerator;
import dev.shiza.bulbasaur.update.Update;
import dev.shiza.bulbasaur.update.UpdateGenerator;

public final class QueryGenerators {

  private static final QueryGenerator<Insert> INSERT_GENERATOR = new InsertGenerator();
  private static final QueryGenerator<Select> SELECT_GENERATOR = new SelectGenerator();
  private static final QueryGenerator<Update> UPDATE_GENERATOR = new UpdateGenerator();

  private QueryGenerators() {}

  public static QueryGenerator<Insert> insert() {
    return INSERT_GENERATOR;
  }

  public static QueryGenerator<Select> select() {
    return SELECT_GENERATOR;
  }

  public static QueryGenerator<Update> update() {
    return UPDATE_GENERATOR;
  }
}
