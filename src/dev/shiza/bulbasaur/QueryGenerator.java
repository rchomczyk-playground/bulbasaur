package dev.shiza.bulbasaur;

@FunctionalInterface
public interface QueryGenerator<T> {

  String generate(T generate);
}
