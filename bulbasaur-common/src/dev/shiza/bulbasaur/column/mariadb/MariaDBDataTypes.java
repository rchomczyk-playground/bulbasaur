package dev.shiza.bulbasaur.column.mariadb;

import dev.shiza.bulbasaur.column.ColumnDefinition;
import dev.shiza.bulbasaur.column.ColumnDefinitions;
import java.util.Arrays;
import java.util.stream.Collectors;

public final class MariaDBDataTypes {

  private MariaDBDataTypes() {}

  public static ColumnDefinition uuid() {
    return ColumnDefinitions.dataType("UUID");
  }

  public static ColumnDefinition bool() {
    return ColumnDefinitions.dataType("BOOLEAN");
  }

  public static ColumnDefinition tinyint() {
    return ColumnDefinitions.dataType("TINYINT");
  }

  public static ColumnDefinition integer() {
    return ColumnDefinitions.dataType("INT");
  }

  public static ColumnDefinition bigint() {
    return ColumnDefinitions.dataType("BIGINT");
  }

  public static ColumnDefinition decimal(final int length, final int precision) {
    return ColumnDefinitions.dataType("DECIMAL(" + length + ", " + precision + ")");
  }

  public static ColumnDefinition numeric() { // synonym for double
    return ColumnDefinitions.dataType("NUMERIC");
  }

  public static ColumnDefinition varchar(final int length) {
    return ColumnDefinitions.dataType("VARCHAR(" + length + ")");
  }

  public static ColumnDefinition tinyText() {
    return ColumnDefinitions.dataType("TINYTEXT");
  }

  public static ColumnDefinition text() {
    return ColumnDefinitions.dataType("TEXT");
  }

  public static ColumnDefinition type(final Class<? extends Enum<?>> enumType) { // synonym for enum
    final String allValues =
        Arrays.stream(enumType.getEnumConstants())
            .map(Enum::name)
            .map(name -> "'" + name + "'")
            .collect(Collectors.joining(","));
    return ColumnDefinitions.dataType("ENUM(" + allValues + ")");
  }

  public static ColumnDefinition mediumText() {
    return ColumnDefinitions.dataType("MEDIUMTEXT");
  }

  public static ColumnDefinition longText() {
    return ColumnDefinitions.dataType("LONGTEXT");
  }

  public static ColumnDefinition json() { // synonym for long text
    return longText();
  }

  public static ColumnDefinition tinyBlob() {
    return ColumnDefinitions.dataType("TINYBLOB");
  }

  public static ColumnDefinition blob() {
    return ColumnDefinitions.dataType("BLOB");
  }

  public static ColumnDefinition mediumBlob() {
    return ColumnDefinitions.dataType("MEDIUMBLOB");
  }

  public static ColumnDefinition longBlob() {
    return ColumnDefinitions.dataType("LONGBLOB");
  }

  public static ColumnDefinition inet4() {
    return ColumnDefinitions.dataType("INET4");
  }

  public static ColumnDefinition inet6() {
    return ColumnDefinitions.dataType("INET6");
  }

  public static ColumnDefinition year() {
    return ColumnDefinitions.dataType("YEAR");
  }

  public static ColumnDefinition time() {
    return ColumnDefinitions.dataType("TIME");
  }

  public static ColumnDefinition date() {
    return ColumnDefinitions.dataType("DATE");
  }

  public static ColumnDefinition datetime() {
    return ColumnDefinitions.dataType("DATETIME");
  }

  public static ColumnDefinition timestamp() {
    return ColumnDefinitions.dataType("TIMESTAMP");
  }
}
