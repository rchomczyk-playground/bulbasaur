package dev.shiza.bulbasaur.statement;

public final class PreparedStatementUtils {

  private PreparedStatementUtils() {}

  public static String getEscapedParameters(final int length) {
    return cutSuffix("?, ".repeat(length));
  }

  private static String cutSuffix(final String source) {
    return source.substring(0, source.length() - 2);
  }
}
