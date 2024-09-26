package dev.shiza.bulbasaur.generator.column;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface Column {

  String name() default "";

  String definition();

  ColumnScope scope() default ColumnScope.GLOBAL;
}
