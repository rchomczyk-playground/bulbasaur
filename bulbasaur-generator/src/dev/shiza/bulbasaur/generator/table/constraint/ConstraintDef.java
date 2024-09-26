package dev.shiza.bulbasaur.generator.table.constraint;

public record ConstraintDef(String name, String definition) {

  public static ConstraintDef from(final Constraint constraint) {
    return new ConstraintDef(constraint.name(), constraint.definition());
  }
}