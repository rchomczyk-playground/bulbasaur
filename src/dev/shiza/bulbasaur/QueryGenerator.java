package dev.shiza.bulbasaur;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface QueryGenerator<T> {

  String generate(final @NotNull T generate);
}
