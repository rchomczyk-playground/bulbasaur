package dev.shiza.bulbasaur.generator.bytecode.generator;

import dev.shiza.bulbasaur.generator.query.generator.GeneratedQuery;
import java.util.List;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

public interface BytecodeGenerator {

  static BytecodeGenerator create(final String namePrefix, final String nameSuffix) {
    return new BytecodeGeneratorImpl(namePrefix, nameSuffix);
  }

  void generate(
      final ProcessingEnvironment processingEnv,
      final TypeElement typeElement,
      final List<GeneratedQuery> queries);
}
