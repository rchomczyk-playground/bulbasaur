package dev.shiza.bulbasaur.generator.entity;

import dev.shiza.bulbasaur.generator.annotation.processor.AnnotationProcessor;
import dev.shiza.bulbasaur.generator.column.Column;
import dev.shiza.bulbasaur.generator.column.ColumnDef;
import dev.shiza.bulbasaur.generator.table.Table;
import dev.shiza.bulbasaur.generator.table.TableDef;
import dev.shiza.bulbasaur.generator.table.constraint.ConstraintDef;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic.Kind;

public final class EntityAnnotationProcessor implements AnnotationProcessor {

  private final Map<String, EntityDef> entityDefs;

  public EntityAnnotationProcessor(final Map<String, EntityDef> entityDefs) {
    this.entityDefs = entityDefs;
  }

  @Override
  public void process(final ProcessingEnvironment processingEnv, final TypeElement typeElement) {
    final Table table = typeElement.getAnnotation(Table.class);
    if (table == null) {
      processingEnv
          .getMessager()
          .printMessage(Kind.ERROR, "Classes annotated with @Entity must have @Table");
      return;
    }

    final TableDef tableDef =
        TableDef.from(
            table,
            Arrays.stream(table.constraints())
                .map(ConstraintDef::from)
                .toArray(ConstraintDef[]::new));
    final ColumnDef[] columnDefs = getColumnDefs(processingEnv, typeElement);

    entityDefs.put(typeElement.getSimpleName().toString(), new EntityDef(tableDef, columnDefs));
  }

  private ColumnDef[] getColumnDefs(
      final ProcessingEnvironment processingEnv, final TypeElement classElement) {
    final List<ColumnDef> columnDefs = new ArrayList<>();
    for (final Element enclosedElement : classElement.getEnclosedElements()) {
      if (enclosedElement instanceof VariableElement fieldElement) {
        final Column column = fieldElement.getAnnotation(Column.class);
        if (column == null) {
          processingEnv
              .getMessager()
              .printMessage(Kind.ERROR, "Field has to be annotated with @Column");
          return new ColumnDef[0];
        }

        columnDefs.add(ColumnDef.from(column, fieldElement.getSimpleName().toString()));
      }
    }

    return columnDefs.toArray(new ColumnDef[0]);
  }
}
