package dev.shiza.bulbasaur.generator.condition;

public record ConditionDef(String generatedCondition) {

  public static ConditionDef from(final Condition condition) {
    return new ConditionDef(condition.value());
  }
}
