package dev.shiza.bulbasaur.generator.bytecode.generator;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import dev.shiza.bulbasaur.generator.query.generator.GeneratedQuery;
import java.io.IOException;
import java.util.List;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

final class BytecodeGeneratorImpl implements BytecodeGenerator {

  private static final String NEW_LINE = "\n";
  private final String namePrefix;
  private final String nameSuffix;

  BytecodeGeneratorImpl(final String namePrefix, final String nameSuffix) {
    this.namePrefix = namePrefix;
    this.nameSuffix = nameSuffix;
  }

  @Override
  public void generate(
      final ProcessingEnvironment processingEnv,
      final TypeElement typeElement,
      final List<GeneratedQuery> queries) {
    saveGeneratedClass(
        processingEnv,
        TypeSpec.classBuilder(getGeneratedClassName(typeElement.getSimpleName().toString()))
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .addMethod(MethodSpec.constructorBuilder().addModifiers(Modifier.PRIVATE).build())
            .addFields(queries.stream().map(this::getGeneratedField).toList())
            .build(),
        typeElement);
  }

  private FieldSpec getGeneratedField(final GeneratedQuery generatedQuery) {
    return FieldSpec.builder(String.class, generatedQuery.fieldName())
        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
        .initializer("$S", getNormalizedQuery(generatedQuery))
        .build();
  }

  private String getGeneratedClassName(final String baseClassName) {
    return namePrefix + baseClassName + nameSuffix;
  }

  private String getNormalizedQuery(final GeneratedQuery generatedQuery) {
    return generatedQuery.query().replace(NEW_LINE, " ");
  }

  private void saveGeneratedClass(
      final ProcessingEnvironment processingEnv,
      final TypeSpec typeSpec,
      final TypeElement element) {
    try {
      JavaFile.builder(
              processingEnv.getElementUtils().getPackageOf(element).getQualifiedName().toString(),
              typeSpec)
          .build()
          .writeTo(processingEnv.getFiler());
    } catch (final IOException exception) {
      throw new BytecodeGenerationException(
          "Could not generate bytecode, because of unexpected exception.", exception);
    }
  }
}
