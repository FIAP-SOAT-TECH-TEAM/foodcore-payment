package com.soat.fiap.food.core.api.shared.infrastructure.common.mapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mapstruct.Qualifier;

/**
 * Anotação personalizada utilizada como {@link Qualifier} do MapStruct para
 * marcar métodos auxiliares de mapeamento que devem ser ignorados
 * automaticamente pelo processo de mapeamento principal.
 * <p>
 * Normalmente aplicada em métodos `default` de mappers que inicializam
 * manualmente o {@link CycleAvoidingMappingContext}.
 */
@Qualifier @Target(ElementType.METHOD) @Retention(value = RetentionPolicy.CLASS)
public @interface DoIgnore {
}
