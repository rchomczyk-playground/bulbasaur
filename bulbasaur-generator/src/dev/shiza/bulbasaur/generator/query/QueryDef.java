package dev.shiza.bulbasaur.generator.query;

import dev.shiza.bulbasaur.generator.condition.ConditionDef;
import dev.shiza.bulbasaur.generator.entity.EntityDef;

public record QueryDef(Query query, EntityDef entity, ConditionDef condition) {}
