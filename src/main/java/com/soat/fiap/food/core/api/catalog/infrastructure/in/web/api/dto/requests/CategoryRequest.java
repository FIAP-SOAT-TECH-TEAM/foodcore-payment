package com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para requisições de criação/atualização de categorias
 */
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Schema(name = "CategoryRequest", description = "DTO para criação ou atualização de categorias de produtos")
public class CategoryRequest {

	@NotNull(message = "O ID do catalogo da categoria é obrigatório") @Schema(description = "Id da categoria", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
	private Long catalogId;

	@NotBlank(message = "O nome da categoria é obrigatório")
	@Schema(description = "Nome da categoria", example = "Lanches", requiredMode = Schema.RequiredMode.REQUIRED)
	private String name;

	@NotBlank(message = "A descrição da categoria é obrigatória")
	@Schema(description = "Descrição da categoria", example = "Variedade de hambúrgueres e sanduíches", requiredMode = Schema.RequiredMode.REQUIRED)
	private String description;

	@NotNull(message = "O status da categoria é obrigatório") @Schema(description = "Status da categoria", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
	private boolean active;

	@Schema(description = "Ordem de exibição da categoria", example = "1")
	private Integer displayOrder;

}
