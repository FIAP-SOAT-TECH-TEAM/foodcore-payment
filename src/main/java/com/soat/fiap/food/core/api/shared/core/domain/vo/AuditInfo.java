package com.soat.fiap.food.core.api.shared.core.domain.vo;

import java.time.LocalDateTime;
import java.util.Objects;

import com.soat.fiap.food.core.api.shared.core.domain.exceptions.BusinessException;

import lombok.Getter;

/**
 * Objeto de valor que representa informações de auditoria - createdAt: data e
 * hora de criação - updatedAt: data e hora da última atualização
 */
@Getter
public class AuditInfo {
	private final LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public AuditInfo() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now().plusNanos(1);
	}

	public AuditInfo(LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.createdAt = Objects.requireNonNull(createdAt);
		setUpdatedAt(updatedAt);
	}

	/**
	 * Autualiza o campo updatedAt
	 *
	 * @param updatedAt
	 *            LocalDateTime a ser atualizado
	 * @throws NullPointerException
	 *             se o updatedAt for nulo
	 * @throws BusinessException
	 *             se o updatedAt for menor ou igual a createdAt
	 */
	public void setUpdatedAt(LocalDateTime updatedAt) {
		validate(updatedAt);
		this.updatedAt = updatedAt;
	}

	/**
	 * Valida o campo updatedAt
	 *
	 * @param updatedAt
	 *            data e hora da última atualização
	 * @throws NullPointerException
	 *             se o updatedAt for nulo
	 * @throws BusinessException
	 *             se o updatedAt for menor ou igual a createdAt
	 */
	private void validate(LocalDateTime updatedAt) {
		Objects.requireNonNull(updatedAt, "UpdatedAt não pode ser nulo");

		if (updatedAt.isBefore(createdAt)) {
			throw new BusinessException("UpdatedAt não pode ser menor ou igual a CreatedAt");
		} else if (updatedAt.isEqual(createdAt)) {
			this.updatedAt = LocalDateTime.now().plusNanos(1);
		}
	}
}
