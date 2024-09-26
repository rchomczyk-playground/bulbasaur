package dev.shiza.bulbasaur.generator.table;

import dev.shiza.bulbasaur.generator.table.constraint.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Table {

  String name();

  String[] primaryKeys() default {};

  Constraint[] constraints() default {};
}
