package dev.shiza.bulbasaur.generator.query.generator;

import dev.shiza.bulbasaur.create.table.Table;
import dev.shiza.bulbasaur.delete.Delete;
import dev.shiza.bulbasaur.generator.column.ColumnDef;
import dev.shiza.bulbasaur.generator.column.ColumnScope;
import dev.shiza.bulbasaur.generator.entity.EntityDef;
import dev.shiza.bulbasaur.generator.query.Query;
import dev.shiza.bulbasaur.generator.query.QueryDef;
import dev.shiza.bulbasaur.generator.table.TableDef;
import dev.shiza.bulbasaur.generator.table.constraint.ConstraintDef;
import dev.shiza.bulbasaur.insert.Insert;
import dev.shiza.bulbasaur.replace.Replace;
import dev.shiza.bulbasaur.select.Select;
import dev.shiza.bulbasaur.update.Update;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public final class QueryGeneratorUtils {

  private static final Map<Query, Function<QueryDef, String>> QUERY_GENERATORS =
      Map.of(
          Query.SELECT, QueryGeneratorUtils::selectQuery,
          Query.INSERT, QueryGeneratorUtils::insertQuery,
          Query.UPDATE, QueryGeneratorUtils::updateQuery,
          Query.DELETE, QueryGeneratorUtils::deleteQuery,
          Query.REPLACE, QueryGeneratorUtils::replaceQuery);

  private QueryGeneratorUtils() {}

  public static String generateQuery(final QueryDef queryDef) {
    final Function<QueryDef, String> queryGenerator = QUERY_GENERATORS.get(queryDef.query());
    if (queryGenerator == null) {
      throw new QueryGenerationException(
          "Query generator for %s query is missing.".formatted(queryDef.query()));
    }

    return queryGenerator.apply(queryDef);
  }

  public static String generateCreateTableQuery(final EntityDef entityDef) {
    final TableDef tableDef = entityDef.table();
    final Table table = Table.table(tableDef.name());
    table.primaryKeys(tableDef.primaryKeys());

    for (final ConstraintDef constraintDef : tableDef.constraints()) {
      table.constraint(constraintDef.name(), constraintDef.definition());
    }

    final ColumnDef[] columnDefs = entityDef.columns();
    for (final ColumnDef columnDef : columnDefs) {
      table.column(columnDef.name(), columnDef.definition());
    }

    return table.query();
  }

  private static String selectQuery(final QueryDef queryDef) {
    final Select select = Select.select(allFields(queryDef));
    select.from(queryDef.entity().table().name());
    select.where(() -> queryDef.condition().generatedCondition());
    return select.query();
  }

  private static String insertQuery(final QueryDef queryDef) {
    final Insert insert = Insert.insert(fields(queryDef, ColumnScope.UPSERT));
    insert.into(queryDef.entity().table().name());
    return insert.query();
  }

  private static String updateQuery(final QueryDef queryDef) {
    final Update update = Update.update(queryDef.entity().table().name());
    for (final String columnName : fields(queryDef)) {
      update.set(columnName);
    }
    update.where(() -> queryDef.condition().generatedCondition());
    return update.query();
  }

  private static String deleteQuery(final QueryDef queryDef) {
    final Delete delete = Delete.delete();
    delete.from(queryDef.entity().table().name());
    delete.where(() -> queryDef.condition().generatedCondition());
    return delete.query();
  }

  private static String replaceQuery(final QueryDef queryDef) {
    final Replace replace = Replace.replace(fields(queryDef, ColumnScope.UPSERT));
    replace.into(queryDef.entity().table().name());
    return replace.query();
  }

  private static String[] allFields(final QueryDef queryDef) {
    return Arrays.stream(queryDef.entity().columns()).map(ColumnDef::name).toArray(String[]::new);
  }

  private static String[] fields(final QueryDef queryDef) {
    return fields(queryDef, ColumnScope.GLOBAL);
  }

  private static String[] fields(final QueryDef queryDef, final ColumnScope expectedScope) {
    return Arrays.stream(queryDef.entity().columns())
        .filter(
            columnDef ->
                columnDef.scope() == expectedScope || columnDef.scope() == ColumnScope.GLOBAL)
        .map(ColumnDef::name)
        .toArray(String[]::new);
  }
}
