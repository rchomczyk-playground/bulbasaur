package dev.shiza.bulbasaur.condition;

@FunctionalInterface
public interface Condition {

  String generate();

  default Condition and(final Condition condition, final boolean parentheses) {
    final String generatedCondition = this.generate();
    if (generatedCondition.isEmpty()) {
      return condition;
    }

    final String generatedExpression = generatedCondition + " AND " + condition.generate();
    return () -> parentheses ? "(" + generatedExpression + ")" : generatedExpression;
  }

  default Condition and(final Condition condition) {
    return and(condition, false);
  }

  default Condition or(final Condition condition, final boolean parentheses) {
    final String generatedCondition = this.generate();
    if (generatedCondition.isEmpty()) {
      return condition;
    }

    final String generatedExpression = generatedCondition + " OR " + condition.generate();
    return () -> parentheses ? "(" + generatedExpression + ")" : generatedExpression;
  }

  default Condition or(final Condition condition) {
    return or(condition, false);
  }
}
