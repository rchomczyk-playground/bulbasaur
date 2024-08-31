package dev.shiza.bulbasaur.update;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.shiza.bulbasaur.condition.Conditions;
import org.junit.jupiter.api.Test;

class UpdateGeneratorTests {

  @Test
  void generateUpdateQuery() {
    final String expectedQuery =
        """
        UPDATE users
        SET username = ?, registration_address = ?, registration_time = ?, last_online_address = ?, last_online_time = ?;
        """
            .trim();
    final String generatedQuery =
        UpdateDsl.update("users")
            .set("username")
            .set("registration_address")
            .set("registration_time")
            .set("last_online_address")
            .set("last_online_time")
            .query();
    assertEquals(expectedQuery, generatedQuery);
  }

  @Test
  void generateUpdateQueryWithWhere() {
    final String expectedQuery =
        """
        UPDATE users
        SET username = ?, registration_address = ?, registration_time = ?, last_online_address = ?, last_online_time = ?
        WHERE unique_id = ?;
        """
            .trim();
    final String generatedQuery =
        UpdateDsl.update("users")
            .set("username")
            .set("registration_address")
            .set("registration_time")
            .set("last_online_address")
            .set("last_online_time")
            .where(Conditions.eq("unique_id"))
            .query();
    assertEquals(expectedQuery, generatedQuery);
  }
}
