package dev.shiza.bulbasaur.condition;

@FunctionalInterface
public interface Condition {

  String generate();

  default Condition and(final Condition condition) {
    final String generatedCondition = this.generate();
    if (generatedCondition.isEmpty()) {
      return condition;
    }

    return () -> generatedCondition + " AND " + condition.generate();
  }
}
