package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways;

import java.util.List;
import java.util.Optional;

import com.soat.fiap.food.core.api.catalog.core.domain.model.Catalog;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.CatalogDTO;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.mappers.CatalogDTOMapper;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.CatalogDataSource;

/**
 * Gateway para persistência do agregado Catálogo.
 */
public class CatalogGateway {

	private final CatalogDataSource catalogDataSource;

	public CatalogGateway(CatalogDataSource catalogDataSource) {
		this.catalogDataSource = catalogDataSource;
	}

	/**
	 * Salva o agregado Catálogo.
	 *
	 * @param catalog
	 *            Agregado Catálogo a ser salvo
	 * @return Agregado salvo com identificadores atualizados
	 */
	public Catalog save(Catalog catalog) {
		CatalogDTO dto = CatalogDTOMapper.toDTO(catalog);
		CatalogDTO savedDTO = catalogDataSource.save(dto);
		return CatalogDTOMapper.toDomain(savedDTO);
	}

	/**
	 * Busca um catálogo pelo ID.
	 *
	 * @param id
	 *            ID do catálogo
	 * @return Optional contendo o catálogo ou vazio se não encontrado
	 */
	public Optional<Catalog> findById(Long id) {

		return catalogDataSource.findById(id).map(CatalogDTOMapper::toDomain);
	}

	/**
	 * Lista todos os catálogos persistidos.
	 *
	 * @return Lista de catálogos
	 */
	public List<Catalog> findAll() {
		return catalogDataSource.findAll().stream().map(CatalogDTOMapper::toDomain).toList();
	}

	/**
	 * Verifica se existe um catalogo com um determinado ID.
	 *
	 * @param id
	 *            ID do catálogo
	 * @return true se existir um catálogo com um determinado ID, false caso
	 *         contrário
	 */
	public boolean existsById(Long id) {
		return catalogDataSource.existsById(id);
	}

	/**
	 * Verifica se existe um catalogo com um determinado nome.
	 *
	 * @param name
	 *            Nome do catálogo
	 * @return true se existir um catálogo com um determinado nome, false caso
	 *         contrário
	 */
	public boolean existsByName(String name) {
		return catalogDataSource.existsByName(name);
	}

	/**
	 * Verifica se existe outro catálogo com o mesmo nome, mas com ID diferente.
	 *
	 * @param name
	 *            Nome do catálogo
	 * @param id
	 *            ID do catálogo que está sendo atualizado
	 * @return true se existir outro catálogo com o mesmo nome e ID diferente, false
	 *         caso contrário
	 */
	public boolean existsByNameAndIdNot(String name, Long id) {
		return catalogDataSource.existsByNameAndIdNot(name, id);
	}

	/**
	 * Verifica se existe pelo menos uma categoria associada ao catálogo com o ID
	 * informado.
	 *
	 * @param catalogId
	 *            ID do catálogo
	 * @return true se houver ao menos uma categoria associada, false caso contrário
	 */
	public boolean existsCategoryByCatalogId(Long catalogId) {
		return catalogDataSource.existsCategoryByCatalogId(catalogId);
	}

	/**
	 * Remove um catálogo com base em seu ID.
	 *
	 * @param id
	 *            ID do catálogo a ser removido
	 */
	public void delete(Long id) {
		catalogDataSource.delete(id);
	}

	/**
	 * Retorna um catalogo pelo ID do produto
	 *
	 * @param productId
	 *            ID do produto
	 */
	public Optional<Catalog> findByProductId(Long productId) {
		return catalogDataSource.findByProductId(productId).map(CatalogDTOMapper::toDomain);
	}
}
