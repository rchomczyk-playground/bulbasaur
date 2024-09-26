package dev.shiza.bulbasaur.generator.annotation.processor;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

public interface AnnotationProcessor {

  void process(final ProcessingEnvironment processingEnv, final TypeElement typeElement);
}
