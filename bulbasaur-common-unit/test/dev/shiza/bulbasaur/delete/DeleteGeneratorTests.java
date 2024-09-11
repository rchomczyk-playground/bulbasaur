package dev.shiza.bulbasaur.delete;

import static dev.shiza.bulbasaur.condition.Conditions.eq;
import static dev.shiza.bulbasaur.delete.Delete.delete;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DeleteGeneratorTests {

  @Test
  void generateDeleteQuery() {
    final String generatedQuery =
        delete().from("auroramc_economy_accounts").where(eq("id")).query();
    final String expectedQuery =
        """
        DELETE
        FROM auroramc_economy_accounts
        WHERE id = ?
        """
            .trim();
    assertEquals(expectedQuery, generatedQuery);
  }
}
