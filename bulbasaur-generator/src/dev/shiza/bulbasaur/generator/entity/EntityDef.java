package dev.shiza.bulbasaur.generator.entity;

import dev.shiza.bulbasaur.generator.column.ColumnDef;
import dev.shiza.bulbasaur.generator.table.TableDef;
import java.util.Arrays;
import java.util.Objects;

public record EntityDef(TableDef table, ColumnDef[] columns) {

  @Override
  public String toString() {
    return "EntityDef{" + "table=" + table + ", columns=" + Arrays.toString(columns) + '}';
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final EntityDef entityDef = (EntityDef) o;
    return Objects.equals(table, entityDef.table) && Objects.deepEquals(columns, entityDef.columns);
  }

  @Override
  public int hashCode() {
    return Objects.hash(table, Arrays.hashCode(columns));
  }
}
