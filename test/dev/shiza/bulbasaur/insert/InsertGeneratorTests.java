package dev.shiza.bulbasaur.insert;

import static dev.shiza.bulbasaur.insert.InsertDsl.insert;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class InsertGeneratorTests {

  @Test
  void generateInsertQuery() {
    final String expectedQuery =
        """
        INSERT INTO users
        (uuid, name, registration_address, registration_time, last_visit_address, last_visit_time)
        VALUES (?, ?, ?, ?, ?, ?)
        """
            .trim();
    final String generatedQuery =
        insert(
                "uuid",
                "name",
                "registration_address",
                "registration_time",
                "last_visit_address",
                "last_visit_time")
            .into("users")
            .query();
    assertEquals(expectedQuery, generatedQuery);
  }
}
