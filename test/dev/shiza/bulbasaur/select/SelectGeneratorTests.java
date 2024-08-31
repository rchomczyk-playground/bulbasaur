package dev.shiza.bulbasaur.select;

import static dev.shiza.bulbasaur.condition.Conditions.between;
import static dev.shiza.bulbasaur.condition.Conditions.eq;
import static dev.shiza.bulbasaur.condition.Conditions.gt;
import static dev.shiza.bulbasaur.condition.Conditions.like;
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
        WHERE user_id = ? AND currency_id = ? AND balance >= ?
        """
            .trim();
    final String generatedQuery =
        select("name")
            .from("accounts")
            .join("users", eq("accounts.user_id", "users.id"))
            .join("currencies", eq("accounts.currency_id", "currencies.id"))
            .where(eq("user_id").and(eq("currency_id")).and(Conditions.gte("balance")))
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
        LIMIT 10
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

  @Test
  void generateDistinctSelectQueryWithLikeAndBetween() {
    final String expectedQuery =
        """
        SELECT DISTINCT name
        FROM products
        WHERE name LIKE a% AND price BETWEEN 150 AND 300
        LIMIT 10
        """
            .trim();
    final String generatedQuery =
        select("name")
            .distinct()
            .from("products")
            .where(like("name", "a%").and(between("price", 150, 300)))
            .limit(10)
            .query();

    assertEquals(expectedQuery, generatedQuery);
  }

  @Test
  void generateComplexSelectQuery() {
    final Select counterQuery =
        select("COUNT(*) + 1")
            .from("auroramc_economy_accounts AS counter_query")
            .join(
                "auroramc_registry_users AS counter_users",
                eq("counter_users.id", "counter_query.user_id"))
            .where(
                gt("counter_query.balance", "primary_query.balance")
                    .or(
                        eq("counter_query.balance", "primary_query.balance")
                            .and(gt("counter_users.username", "primary_users.username"), true),
                        true)
                    .and(eq("counter_query.currency_id")));
    final Select primaryQuery =
        select(
                "unique_id",
                "username",
                "currency_id",
                "balance",
                "(" + counterQuery.query() + ") AS position")
            .from("auroramc_economy_accounts AS primary_query")
            .join(
                "auroramc_registry_users AS primary_users",
                eq("primary_users.id", "primary_query.user_id"))
            .where(eq("currency_id").and(eq("user_id")))
            .orderBy("position");

    final String expectedQuery =
        """
        SELECT unique_id, username, currency_id, balance, (SELECT COUNT(*) + 1
        FROM auroramc_economy_accounts AS counter_query
        LEFT JOIN auroramc_registry_users AS counter_users ON counter_users.id = counter_query.user_id
        WHERE (counter_query.balance > primary_query.balance OR (counter_query.balance = primary_query.balance AND counter_users.username > primary_users.username)) AND counter_query.currency_id = ?) AS position
        FROM auroramc_economy_accounts AS primary_query
        LEFT JOIN auroramc_registry_users AS primary_users ON primary_users.id = primary_query.user_id
        WHERE currency_id = ? AND user_id = ?
        ORDER BY position ASC
        """
            .trim();
    final String generatedQuery = primaryQuery.query();

    assertEquals(expectedQuery, generatedQuery);
  }
}
