package dev.shiza.bulbasaur.generator;

import dev.shiza.bulbasaur.generator.annotation.processor.AnnotationProcessor;
import dev.shiza.bulbasaur.generator.bytecode.generator.BytecodeGenerator;
import dev.shiza.bulbasaur.generator.entity.Entity;
import dev.shiza.bulbasaur.generator.entity.EntityAnnotationProcessor;
import dev.shiza.bulbasaur.generator.entity.EntityDef;
import dev.shiza.bulbasaur.generator.repository.Repository;
import dev.shiza.bulbasaur.generator.repository.RepositoryAnnotationProcessor;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

@SupportedAnnotationTypes({
  "dev.shiza.bulbasaur.generator.column.Column",
  "dev.shiza.bulbasaur.entity.Entity",
  "dev.shiza.bulbasaur.generator.condition.Condition",
  "dev.shiza.bulbasaur.query.NativeQuery",
  "dev.shiza.bulbasaur.repository.Repository",
  "dev.shiza.bulbasaur.table.constraint.Constraint",
  "dev.shiza.bulbasaur.table.Table"
})
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class BulbasaurProcessor extends AbstractProcessor {

  private String namePrefix;
  private String nameSuffix;

  @Override
  public synchronized void init(final ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    this.namePrefix = processingEnv.getOptions().getOrDefault("bulbasaurNamePrefix", "Sql");
    this.nameSuffix = processingEnv.getOptions().getOrDefault("bulbasaurNameSuffix", "Queries");
  }

  @Override
  public boolean process(
      final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
    final Map<String, EntityDef> entityDefs = new HashMap<>();
    process(roundEnv, Entity.class, ElementKind.CLASS, new EntityAnnotationProcessor(entityDefs));
    process(
        roundEnv,
        Repository.class,
        ElementKind.INTERFACE,
        new RepositoryAnnotationProcessor(
            BytecodeGenerator.create(namePrefix, nameSuffix), entityDefs));
    return true;
  }

  private void process(
      final RoundEnvironment roundEnv,
      final Class<? extends Annotation> annotationType,
      final ElementKind expectedKind,
      final AnnotationProcessor elementConsumer) {
    for (final Element element : roundEnv.getElementsAnnotatedWith(annotationType)) {
      if (element.getKind() != expectedKind) {
        processingEnv
            .getMessager()
            .printMessage(
                Kind.ERROR,
                "Only elements with %s kind can be annotated with @%s"
                    .formatted(expectedKind.name(), annotationType.getName()));
        return;
      }

      elementConsumer.process(processingEnv, (TypeElement) element);
    }
  }
}
