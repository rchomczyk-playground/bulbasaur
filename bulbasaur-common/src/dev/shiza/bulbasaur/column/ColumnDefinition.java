package dev.shiza.bulbasaur.column;

@FunctionalInterface
public interface ColumnDefinition {

  String generate();

  default ColumnDefinition and(final ColumnDefinition definition) {
    final String generatedDefinition = this.generate();
    if (generatedDefinition.isEmpty()) {
      return definition;
    }

    return () -> generatedDefinition + " " + definition.generate();
  }
}
