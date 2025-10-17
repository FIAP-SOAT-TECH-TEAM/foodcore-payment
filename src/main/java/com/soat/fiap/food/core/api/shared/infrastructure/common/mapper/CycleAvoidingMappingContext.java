package com.soat.fiap.food.core.api.shared.infrastructure.common.mapper;

import java.util.IdentityHashMap;
import java.util.Map;

import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

/**
 * Classe de contexto utilizada pelos mappers do MapStruct para evitar ciclos de
 * mapeamento infinitos durante a conversão de entidades com relacionamentos
 * bidirecionais.
 * <p>
 * Essa classe armazena instâncias já mapeadas utilizando um
 * {@link IdentityHashMap}, garantindo que referências previamente convertidas
 * sejam reutilizadas ao invés de reprocessadas, prevenindo assim loops
 * recursivos.
 */
public class CycleAvoidingMappingContext {
	private final Map<Object, Object> knownInstances;

	public CycleAvoidingMappingContext() {
		knownInstances = new IdentityHashMap<>();
	}

	@BeforeMapping
	public <T> T getMappedInstance(Object source, @TargetType Class<T> targetType) {
		return targetType.cast(knownInstances.get(source));
	}

	@BeforeMapping
	public void storeMappedInstance(Object source, @MappingTarget Object target) {
		knownInstances.put(source, target);
	}
}
