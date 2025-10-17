package com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.controller.web.api.category.*;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.CatalogDataSource;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.requests.CategoryRequest;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.responses.CategoryResponse;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.FileUploadDTO;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.ImageDataSource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController @RequestMapping("/catalogs") @Slf4j
public class CategoryController {

	private final CatalogDataSource catalogDataSource;
	private final ImageDataSource imageDataSource;

	public CategoryController(CatalogDataSource catalogDataSource, ImageDataSource imageDataSource) {
		this.catalogDataSource = catalogDataSource;
		this.imageDataSource = imageDataSource;
	}

	@PostMapping(value = "/categories", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Criar nova categoria", description = "Cria uma nova categoria vinculada a um catálogo existente", tags = {
			"Categorias"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Categoria criada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CategoryResponse.class))),
			@ApiResponse(responseCode = "404", description = "Catálogo não encontrado", content = @Content),
			@ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
			@ApiResponse(responseCode = "409", description = "Categoria com nome já existente no catálogo", content = @Content)})
	@Tag(name = "Categorias", description = "Operações para gerenciamento de categorias de produtos") @Transactional
	public ResponseEntity<CategoryResponse> createCategory(@RequestPart("data") @Valid CategoryRequest request,
			@RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
		log.debug("Requisição para criar nova categoria no catálogo: {}", request.getCatalogId());

		FileUploadDTO fileUpload = null;

		if (imageFile != null && !imageFile.isEmpty()) {
			fileUpload = new FileUploadDTO(imageFile.getOriginalFilename(), imageFile.getBytes());
		}

		CategoryResponse response = SaveCategoryController.saveCategory(request, fileUpload, catalogDataSource,
				imageDataSource);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/{catalogId}/categories")
	@Operation(summary = "Listar categorias do catálogo", description = "Retorna todas as categorias associadas a um catálogo", tags = {
			"Categorias"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class)))),
			@ApiResponse(responseCode = "404", description = "Catálogo não encontrado", content = @Content)})
	@Tag(name = "Categorias", description = "Operações para gerenciamento de categorias de produtos")
	@Transactional(readOnly = true)
	public ResponseEntity<List<CategoryResponse>> getAllCategories(
			@Parameter(description = "ID do catálogo", example = "1", required = true) @PathVariable Long catalogId) {
		List<CategoryResponse> responseList = GetAllCategoriesController.getAllCategories(catalogId, catalogDataSource);
		return ResponseEntity.ok(responseList);
	}

	@GetMapping("/{catalogId}/categories/{categoryId}")
	@Operation(summary = "Buscar categoria por ID", description = "Retorna uma categoria específica de um catálogo pelo ID da categoria", tags = {
			"Categorias"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CategoryResponse.class))),
			@ApiResponse(responseCode = "404", description = "Categoria ou catálogo não encontrado", content = @Content)})
	@Tag(name = "Categorias", description = "Operações para gerenciamento de categorias de produtos")
	@Transactional(readOnly = true)
	public ResponseEntity<CategoryResponse> getCategoryById(
			@Parameter(description = "ID do catálogo", example = "1", required = true) @PathVariable Long catalogId,
			@Parameter(description = "ID da categoria", example = "10", required = true)
			@PathVariable Long categoryId) {
		CategoryResponse response = GetCategoryByIdController.getCategoryById(catalogId, categoryId, catalogDataSource);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{catalogId}/categories/{categoryId}")
	@Operation(summary = "Atualizar categoria", description = "Atualiza uma categoria vinculada a um catálogo existente", tags = {
			"Categorias"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CategoryResponse.class))),
			@ApiResponse(responseCode = "404", description = "Catálogo não encontrado", content = @Content),
			@ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
			@ApiResponse(responseCode = "409", description = "Categoria com nome já existente no catálogo", content = @Content)})
	@Tag(name = "Categorias", description = "Operações para gerenciamento de categorias de produtos") @Transactional
	public ResponseEntity<CategoryResponse> updateCategory(
			@Parameter(description = "ID atual do catálogo da categoria", example = "2", required = true)
			@PathVariable Long catalogId,
			@Parameter(description = "ID da categoria", example = "1", required = true) @PathVariable Long categoryId,
			@Valid @RequestBody CategoryRequest request) {
		log.debug("Requisição para atualizar categoria no catálogo: {}", request.getCatalogId());
		CategoryResponse response = UpdateCategoryController.updateCategory(catalogId, categoryId, request,
				catalogDataSource);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{catalogId}/categories/{categoryId}")
	@Operation(summary = "Excluir categoria", description = "Exclui uma categoria específica de um catálogo", tags = {
			"Categorias"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Categoria excluída com sucesso", content = @Content),
			@ApiResponse(responseCode = "404", description = "Categoria ou catálogo não encontrado", content = @Content),
			@ApiResponse(responseCode = "409", description = "Categoria possui produtos associados", content = @Content)})
	@Tag(name = "Categorias", description = "Operações para gerenciamento de categorias de produtos") @Transactional
	public ResponseEntity<Void> deleteCategory(
			@Parameter(description = "ID do catálogo", example = "1", required = true) @PathVariable Long catalogId,
			@Parameter(description = "ID da categoria", example = "10", required = true)
			@PathVariable Long categoryId) {
		DeleteCategoryController.deleteCategory(catalogId, categoryId, catalogDataSource);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/{catalogId}/categories/{categoryId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Atualizar imagem do category", description = "Atualiza apenas a imagem de um categoryexistente", tags = {
			"Categorias"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Imagem da categoria atualizada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CategoryResponse.class))),
			@ApiResponse(responseCode = "404", description = "Catálogo ou categoria não encontrado", content = @Content),
			@ApiResponse(responseCode = "400", description = "Imagem inválida", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro ao processar imagem", content = @Content)})
	@Tag(name = "Categorias", description = "Operações para gerenciamento de categorias de produtos") @Transactional
	public CategoryResponse updateCategoryImage(
			@Parameter(description = "ID do catálogo", example = "1", required = true) @PathVariable Long catalogId,
			@Parameter(description = "ID da categoria", example = "10", required = true) @PathVariable Long categoryId,
			@Parameter(description = "Arquivo da nova imagem", required = true)
			@RequestPart("imageFile") MultipartFile imageFile) throws IOException {
		log.debug("Requisição para atualizar imagem da categoria {} do catálogo {}", categoryId, catalogId);

		FileUploadDTO fileUpload = null;

		if (imageFile != null && !imageFile.isEmpty()) {
			fileUpload = new FileUploadDTO(imageFile.getOriginalFilename(), imageFile.getBytes());
		}

		return UpdateCategoryImageController.updateProductImage(catalogId, categoryId, fileUpload, catalogDataSource,
				imageDataSource);

	}

}
