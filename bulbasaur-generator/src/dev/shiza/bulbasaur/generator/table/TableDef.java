package dev.shiza.bulbasaur.generator.table;

import dev.shiza.bulbasaur.generator.table.constraint.ConstraintDef;
import java.util.Arrays;
import java.util.Objects;

public record TableDef(String name, String[] primaryKeys, ConstraintDef[] constraints) {

  public static TableDef from(final Table table, final ConstraintDef[] constraintDefs) {
    return new TableDef(table.name(), table.primaryKeys(), constraintDefs);
  }

  @Override
  public String toString() {
    return "TableDef{"
        + "name='"
        + name
        + '\''
        + ", primaryKeys="
        + Arrays.toString(primaryKeys)
        + ", constraints="
        + Arrays.toString(constraints)
        + '}';
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final TableDef tableDef = (TableDef) o;
    return Objects.equals(name, tableDef.name)
        && Objects.deepEquals(primaryKeys, tableDef.primaryKeys)
        && Objects.deepEquals(constraints, tableDef.constraints);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, Arrays.hashCode(primaryKeys), Arrays.hashCode(constraints));
  }
}
