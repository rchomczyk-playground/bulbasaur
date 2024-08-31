package dev.shiza.bulbasaur.select;

import static java.util.Collections.singletonList;

import dev.shiza.bulbasaur.QueryGenerators;
import dev.shiza.bulbasaur.condition.Condition;
import dev.shiza.bulbasaur.condition.Conditions;
import java.util.ArrayList;
import java.util.List;

public final class Select {

  public static final List<String> WILDCARD = singletonList("*");
  private final List<String> fields;
  private final List<Join> joins = new ArrayList<>();
  private String table;
  private Condition whereCondition = Conditions.empty();
  private Condition havingCondition = Conditions.empty();
  private String[] groupByFields;
  private String[] orderByFields;
  private boolean orderByAscending = true;
  private Integer limit;

  Select(final List<String> fields) {
    this.fields = fields.isEmpty() ? WILDCARD : fields;
  }

  public Select from(final String table) {
    this.table = table;
    return this;
  }

  public Select join(final String table, final Condition condition) {
    this.joins.add(new Join(table, condition));
    return this;
  }

  public Select where(final Condition condition) {
    this.whereCondition = this.whereCondition.and(condition);
    return this;
  }

  public Select groupBy(final String... fields) {
    this.groupByFields = fields;
    return this;
  }

  public Select having(final Condition condition) {
    this.havingCondition = condition;
    return this;
  }

  public Select orderBy(final String... fields) {
    this.orderByFields = fields;
    return this;
  }

  public Select ascending() {
    this.orderByAscending = true;
    return this;
  }

  public Select descending() {
    this.orderByAscending = false;
    return this;
  }

  public Select limit(final int limit) {
    this.limit = limit;
    return this;
  }

  public String query() {
    return QueryGenerators.select().generate(this);
  }

  String table() {
    return table;
  }

  List<String> fields() {
    return fields;
  }

  List<Join> joins() {
    return joins;
  }

  Condition whereCondition() {
    return whereCondition;
  }

  Condition havingCondition() {
    return havingCondition;
  }

  String[] groupByFields() {
    return groupByFields;
  }

  String[] orderByFields() {
    return orderByFields;
  }

  boolean orderByAscending() {
    return orderByAscending;
  }

  Integer limit() {
    return limit;
  }

  record Join(String target, Condition condition) {}
}
