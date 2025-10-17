package com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.bff.controller.web.api.catalog.*;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.CatalogDataSource;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.requests.CatalogRequest;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.responses.CatalogResponse;

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
public class CatalogController {

	private final CatalogDataSource catalogDataSource;

	public CatalogController(CatalogDataSource catalogDataSource) {
		this.catalogDataSource = catalogDataSource;
	}

	@GetMapping
	@Operation(summary = "Listar todos os catálogos", description = "Retorna uma lista com todos os catálogos cadastrados", tags = {
			"Catálogos"})
	@ApiResponse(responseCode = "200", description = "Lista de catálogos retornada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = CatalogResponse.class))))
	@Tag(name = "Catálogos", description = "Operações para gerenciamento de catálogos de categorias de produtos")
	@Transactional(readOnly = true)
	public ResponseEntity<List<CatalogResponse>> getAllCatalogs() {
		log.debug("Requisição para listar todos os catálogos");
		return ResponseEntity.ok(GetAllCatalogsController.getAllCatalogs(catalogDataSource));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar catálogo por ID", description = "Retorna um catálogo específico pelo seu ID", tags = {
			"Catálogos"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Catálogo encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CatalogResponse.class))),
			@ApiResponse(responseCode = "404", description = "Catálogo não encontrado", content = @Content)})
	@Tag(name = "Catálogos", description = "Operações para gerenciamento de catálogos de categorias de produtos")
	@Transactional(readOnly = true)
	public ResponseEntity<CatalogResponse> getCatalogById(
			@Parameter(description = "ID do catálogo", example = "1", required = true) @PathVariable Long id) {
		log.debug("Requisição para buscar catálogo por ID: {}", id);
		return ResponseEntity.ok(GetCatalogByIdController.getCatalogById(id, catalogDataSource));
	}

	@PostMapping
	@Operation(summary = "Criar novo catálogo", description = "Cria um novo catálogo com os dados fornecidos", tags = {
			"Catálogos"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Catálogo criado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CatalogResponse.class))),
			@ApiResponse(responseCode = "409", description = "Catálogo com nome já existente", content = @Content),
			@ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)})
	@Tag(name = "Catálogos", description = "Operações para gerenciamento de catálogos de categorias de produtos")
	@Transactional
	public ResponseEntity<CatalogResponse> createCatalog(@Valid @RequestBody CatalogRequest request) {
		log.debug("Requisição para criar novo catálogo");

		var response = SaveCatalogController.saveCatalog(request, catalogDataSource);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualizar catálogo", description = "Atualiza os dados de um catálogo existente pelo seu ID", tags = {
			"Catálogos"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Catálogo atualizado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CatalogResponse.class))),
			@ApiResponse(responseCode = "404", description = "Catálogo não encontrado", content = @Content),
			@ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
			@ApiResponse(responseCode = "409", description = "Catálogo com nome já existente", content = @Content),})
	@Tag(name = "Catálogos", description = "Operações para gerenciamento de catálogos de categorias de produtos")
	@Transactional
	public ResponseEntity<CatalogResponse> updateCatalog(
			@Parameter(description = "ID do catálogo", example = "1", required = true) @PathVariable Long id,
			@Valid @RequestBody CatalogRequest request) {
		log.debug("Requisição para atualizar catálogo: {}", id);
		var response = UpdateCatalogController.updateCatalog(id, request, catalogDataSource);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Excluir catálogo", description = "Exclui um catálogo pelo seu ID", tags = {"Catálogos"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Catálogo excluído com sucesso", content = @Content),
			@ApiResponse(responseCode = "404", description = "Catálogo não encontrado", content = @Content),
			@ApiResponse(responseCode = "409", description = "Catálogo possui categorias associadas", content = @Content)})
	@Tag(name = "Catálogos", description = "Operações para gerenciamento de catálogos de categorias de produtos")
	@Transactional
	public ResponseEntity<Void> deleteCatalog(
			@Parameter(description = "ID do catálogo", example = "1", required = true) @PathVariable Long id) {
		log.debug("Requisição para excluir catálogo: {}", id);
		DeleteCatalogController.deleteCatalog(id, catalogDataSource);
		return ResponseEntity.noContent().build();
	}
}
