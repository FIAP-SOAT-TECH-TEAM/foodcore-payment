package com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.entity;

import java.util.ArrayList;
import java.util.List;

import com.soat.fiap.food.core.api.shared.core.domain.vo.AuditInfo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade JPA para catálogo
 */
@Entity @Table(name = "catalog") @Getter @Setter
public class CatalogEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 100, nullable = false, unique = true)
	private String name;

	@Embedded
	private AuditInfo auditInfo = new AuditInfo();

	@OneToMany(mappedBy = "catalog", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.DETACH}, orphanRemoval = true)
	private List<CategoryEntity> categories = new ArrayList<>();
}
