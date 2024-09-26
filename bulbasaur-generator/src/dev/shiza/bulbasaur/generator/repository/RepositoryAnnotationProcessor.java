package dev.shiza.bulbasaur.generator.repository;

import dev.shiza.bulbasaur.generator.annotation.processor.AnnotationProcessor;
import dev.shiza.bulbasaur.generator.bytecode.generator.BytecodeGenerator;
import dev.shiza.bulbasaur.generator.condition.Condition;
import dev.shiza.bulbasaur.generator.condition.ConditionDef;
import dev.shiza.bulbasaur.generator.entity.EntityDef;
import dev.shiza.bulbasaur.generator.query.NativeQuery;
import dev.shiza.bulbasaur.generator.query.NativeQueryDef;
import dev.shiza.bulbasaur.generator.query.Query;
import dev.shiza.bulbasaur.generator.query.QueryDef;
import dev.shiza.bulbasaur.generator.query.generator.GeneratedQuery;
import dev.shiza.bulbasaur.generator.query.generator.QueryGeneratorUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

public final class RepositoryAnnotationProcessor implements AnnotationProcessor {

  private static final Pattern CAMEL_CASE = Pattern.compile("(?=[A-Z])");
  private static final int METHOD_BASE_LENGTH = 2;
  private static final ConditionDef EMPTY_CONDITION = new ConditionDef("");
  private final BytecodeGenerator bytecodeGenerator;
  private final Map<String, EntityDef> entityDefs;

  public RepositoryAnnotationProcessor(
      final BytecodeGenerator bytecodeGenerator, final Map<String, EntityDef> entityDefs) {
    this.bytecodeGenerator = bytecodeGenerator;
    this.entityDefs = entityDefs;
  }

  @Override
  public void process(final ProcessingEnvironment processingEnv, final TypeElement typeElement) {
    final List<GeneratedQuery> queries = new ArrayList<>();
    for (final Element enclosedElement : typeElement.getEnclosedElements()) {
      if (enclosedElement.getKind() != ElementKind.METHOD) {
        processingEnv
            .getMessager()
            .printMessage(Kind.ERROR, "Repositories can only contain methods.");
        return;
      }

      final ExecutableElement executableElement = (ExecutableElement) enclosedElement;
      final String executableElementName =
          toUpperSnakeCase(executableElement.getSimpleName().toString());

      final NativeQueryDef nativeQueryDef = getNativeQueryDef(processingEnv, executableElement);
      if (nativeQueryDef != null) {
        queries.add(new GeneratedQuery(executableElementName, nativeQueryDef.nativeQuery()));
        continue;
      }

      final QueryDef queryDef = getQueryDef(processingEnv, enclosedElement, executableElement);
      if (queryDef != null) {
        queries.add(
            new GeneratedQuery(executableElementName, QueryGeneratorUtils.generateQuery(queryDef)));
      }
    }

    final String[] segments = CAMEL_CASE.split(typeElement.getSimpleName());
    if (segments.length > 0) {
      final String entity = segments[0];
      final EntityDef entityDef = entityDefs.get(entity);
      if (entityDef == null) {
        processingEnv
            .getMessager()
            .printMessage(
                Kind.ERROR,
                "Class named %s contains non existent entity."
                    .formatted(typeElement.getSimpleName()));
        return;
      }

      queries.add(
          new GeneratedQuery(
              toUpperSnakeCase("create%sSchema".formatted(entity)),
              QueryGeneratorUtils.generateCreateTableQuery(entityDef)));
    }

    bytecodeGenerator.generate(
        processingEnv,
        typeElement,
        queries.stream().sorted(Comparator.comparing(GeneratedQuery::query)).toList());
  }

  private NativeQueryDef getNativeQueryDef(
      final ProcessingEnvironment processingEnv, final ExecutableElement executableElement) {
    final NativeQuery nativeQueryRef = executableElement.getAnnotation(NativeQuery.class);
    if (nativeQueryRef != null) {
      final String nativeQuery = nativeQueryRef.value();
      final String[] segments = nativeQuery.split(" ");
      if (segments.length < 1) {
        processingEnv
            .getMessager()
            .printMessage(Kind.ERROR, "Could not read segments from specified native query.");
        return null;
      }

      final Query query = Query.from(segments[0]);
      if (query == null) {
        processingEnv
            .getMessager()
            .printMessage(Kind.ERROR, "Could not read query type from specified native query.");
        return null;
      }

      return new NativeQueryDef(query, nativeQuery);
    }

    return null;
  }

  private QueryDef getQueryDef(
      final ProcessingEnvironment processingEnv,
      final Element enclosedElement,
      final ExecutableElement executableElement) {
    if (executableElement.getAnnotation(NativeQuery.class) != null) {
      return null;
    }

    final String[] segments = CAMEL_CASE.split(enclosedElement.getSimpleName().toString());
    if (segments.length < METHOD_BASE_LENGTH) {
      processingEnv
          .getMessager()
          .printMessage(
              Kind.ERROR,
              "Method named %s could not be split in segments."
                  .formatted(executableElement.getSimpleName()));
      return null;
    }

    final EntityDef entityDef = entityDefs.get(segments[1]);
    if (entityDef == null) {
      processingEnv
          .getMessager()
          .printMessage(
              Kind.ERROR,
              "Method named %s contains non existent entity."
                  .formatted(executableElement.getSimpleName()));
      return null;
    }

    final Query query = Query.from(segments[0]);
    if (query == null) {
      processingEnv
          .getMessager()
          .printMessage(
              Kind.ERROR, "Method named %s contains malformed query.".formatted(segments[0]));
      return null;
    }

    final Condition condition = executableElement.getAnnotation(Condition.class);
    final ConditionDef conditionDef =
        Optional.ofNullable(condition).map(ConditionDef::from).orElse(EMPTY_CONDITION);
    return new QueryDef(query, entityDef, conditionDef);
  }

  private String toUpperSnakeCase(final String camelCase) {
    final StringBuilder result = new StringBuilder();
    for (int index = 0; index < camelCase.length(); index++) {
      final char currentChar = camelCase.charAt(index);
      if (Character.isUpperCase(currentChar) && index != 0) {
        result.append('_');
      }
      result.append(Character.toUpperCase(currentChar));
    }
    return result.toString();
  }
}
