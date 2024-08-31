package dev.shiza.bulbasaur.select;

import static java.util.Collections.singletonList;

import dev.shiza.bulbasaur.condition.Condition;
import dev.shiza.bulbasaur.condition.Conditions;
import dev.shiza.bulbasaur.select.SelectQueryGenerator.InstanceHolder;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

final class SelectQuery implements Select {

  public static final List<String> WILDCARD = singletonList("*");
  private final List<String> fields;
  private final List<Join> joins = new ArrayList<>();
  private boolean distinct = false;
  private String table;
  private Condition whereCondition = Conditions.empty();
  private Condition havingCondition = Conditions.empty();
  private String[] groupByFields;
  private String[] orderByFields;
  private boolean orderByAscending = true;
  private Integer limit;

  SelectQuery(final List<String> fields) {
    this.fields = fields.isEmpty() ? WILDCARD : fields;
  }

  @Override
  public SelectQuery distinct() {
    this.distinct = true;
    return this;
  }

  @Override
  public SelectQuery from(final @NotNull String table) {
    this.table = table;
    return this;
  }

  @Override
  public SelectQuery join(final @NotNull String table, final @NotNull Condition condition) {
    this.joins.add(new Join(table, condition));
    return this;
  }

  @Override
  public SelectQuery where(final @NotNull Condition condition) {
    this.whereCondition = this.whereCondition.and(condition);
    return this;
  }

  @Override
  public SelectQuery groupBy(final @NotNull String... fields) {
    this.groupByFields = fields;
    return this;
  }

  @Override
  public SelectQuery having(final @NotNull Condition condition) {
    this.havingCondition = condition;
    return this;
  }

  @Override
  public SelectQuery orderBy(final @NotNull String... fields) {
    this.orderByFields = fields;
    return this;
  }

  @Override
  public SelectQuery ascending() {
    this.orderByAscending = true;
    return this;
  }

  @Override
  public SelectQuery descending() {
    this.orderByAscending = false;
    return this;
  }

  @Override
  public SelectQuery limit(final int limit) {
    this.limit = limit;
    return this;
  }

  @Override
  public String query() {
    return InstanceHolder.INSTANCE.generate(this);
  }

  public boolean isDistinct() {
    return distinct;
  }

  public String getTable() {
    return table;
  }

  public List<String> getFields() {
    return fields;
  }

  public List<Join> getJoins() {
    return joins;
  }

  public Condition getWhereCondition() {
    return whereCondition;
  }

  public Condition getHavingCondition() {
    return havingCondition;
  }

  public String[] getGroupByFields() {
    return groupByFields;
  }

  public String[] getOrderByFields() {
    return orderByFields;
  }

  public boolean isOrderByAscending() {
    return orderByAscending;
  }

  public Integer getLimit() {
    return limit;
  }

  record Join(String target, Condition condition) {}
}
