package dev.shiza.bulbasaur.select;

import static dev.shiza.bulbasaur.select.SelectDsl.select;
import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.shiza.bulbasaur.condition.Conditions;
import org.junit.jupiter.api.Test;

class SelectGeneratorTests {

  @Test
  void generateUpdateQueryWithJoins() {
    final String expectedQuery =
        """
        SELECT name
        FROM accounts
        LEFT JOIN users ON accounts.user_id = users.id
        LEFT JOIN currencies ON accounts.currency_id = currencies.id
        WHERE user_id = ? AND currency_id = ? AND balance >= ?;
        """
            .trim();
    final String generatedQuery =
        select("name")
            .from("accounts")
            .join("users", Conditions.eq("accounts.user_id", "users.id"))
            .join("currencies", Conditions.eq("accounts.currency_id", "currencies.id"))
            .where(
                Conditions.eq("user_id")
                    .and(Conditions.eq("currency_id"))
                    .and(Conditions.gte("balance")))
            .query();
    assertEquals(expectedQuery, generatedQuery);
  }

  @Test
  void generateSelectQueryWithGroupByAndHaving() {
    final String expectedQuery =
        """
        SELECT *
        FROM payments
        GROUP BY user_id
        HAVING rank >= ?
        ORDER BY rank DESC
        LIMIT 10;
        """
            .trim();
    final String generatedQuery =
        select()
            .from("payments")
            .groupBy("user_id")
            .having(Conditions.gte("rank"))
            .orderBy("rank")
            .descending()
            .limit(10)
            .query();
    assertEquals(expectedQuery, generatedQuery);
  }
}
