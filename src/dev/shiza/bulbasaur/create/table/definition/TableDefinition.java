package dev.shiza.bulbasaur.create.table.definition;

@FunctionalInterface
public interface TableDefinition {

  String generate();

  default TableDefinition and(final TableDefinition definition) {
    final String generatedDefinition = this.generate();
    if (generatedDefinition.isEmpty()) {
      return definition;
    }

    return () -> generatedDefinition + " " + definition.generate();
  }
}
