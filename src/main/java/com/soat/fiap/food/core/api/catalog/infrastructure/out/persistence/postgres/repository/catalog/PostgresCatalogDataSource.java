package com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.repository.catalog;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.CatalogDTO;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.CatalogDataSource;
import com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.entity.CatalogEntity;
import com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.entity.CategoryEntity;
import com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.entity.ProductEntity;
import com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.mapper.CatalogEntityMapper;

/**
 * Implementação concreta: DataSource para persistência do agregado Catálogo.
 */
@Component
public class PostgresCatalogDataSource implements CatalogDataSource {

	private final SpringDataCatalogRepository springDataCatalogRepository;
	private final CatalogEntityMapper catalogEntityMapper;

	public PostgresCatalogDataSource(SpringDataCatalogRepository springDataCatalogRepository,
			CatalogEntityMapper catalogEntityMapper) {
		this.springDataCatalogRepository = springDataCatalogRepository;
		this.catalogEntityMapper = catalogEntityMapper;
	}

	/**
	 * Salva o agregado Catálogo.
	 *
	 * @param catalogDTO
	 *            DTO do Catálogo a ser salvo
	 * @return Agregado salvo com identificadores atualizados
	 */
	@Override @Transactional
	public CatalogDTO save(CatalogDTO catalogDTO) {
		CatalogEntity entity = catalogEntityMapper.toEntity(catalogDTO);

		// Configura o relacionamento entre as entidades
		if (entity.getCategories() != null) {
			for (CategoryEntity category : entity.getCategories()) {
				category.setCatalog(entity);

				if (category.getProducts() != null) {
					for (ProductEntity product : category.getProducts()) {
						product.setCategory(category);

						if (product.getStock() != null) {
							product.getStock().setProduct(product);
						}
					}
				}
			}

		}

		CatalogEntity saved = springDataCatalogRepository.save(entity);
		return catalogEntityMapper.toDTO(saved);
	}

	/**
	 * Busca um catálogo pelo ID.
	 *
	 * @param id
	 *            ID do catálogo
	 * @return Optional contendo o catálogo ou vazio se não encontrado
	 */
	@Override @Transactional(readOnly = true)
	public Optional<CatalogDTO> findById(Long id) {
		return springDataCatalogRepository.findById(id).map(catalogEntityMapper::toDTO);
	}

	/**
	 * Busca um catálogo pelo nome.
	 *
	 * @param name
	 *            nome do catálogo
	 * @return Optional contendo o catálogo ou vazio se não encontrado
	 */
	@Override @Transactional(readOnly = true)
	public Optional<CatalogDTO> findByName(String name) {
		return springDataCatalogRepository.findByName(name).map(catalogEntityMapper::toDTO);
	}

	/**
	 * Lista todos os catálogos persistidos.
	 *
	 * @return Lista de catálogos
	 */

	@Override @Transactional(readOnly = true)
	public List<CatalogDTO> findAll() {
		List<CatalogEntity> catalogsEntities = springDataCatalogRepository.findAll();
		return catalogsEntities.stream().map(catalogEntityMapper::toDTO).toList();
	}

	/**
	 * Verifica se existe um catalogo com um determinado ID.
	 *
	 * @param id
	 *            ID do catálogo
	 * @return true se existir um catálogo com um determinado ID, false caso
	 *         contrário
	 */
	@Override @Transactional(readOnly = true)
	public boolean existsById(Long id) {
		return springDataCatalogRepository.existsById(id);
	}

	/**
	 * Verifica se existe um catalogo com um determinado nome.
	 *
	 * @param name
	 *            Nome do catálogo
	 * @return true se existir um catálogo com um determinado nome, false caso
	 *         contrário
	 */
	@Override @Transactional(readOnly = true)
	public boolean existsByName(String name) {
		return springDataCatalogRepository.existsByName(name);
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
	@Override @Transactional(readOnly = true)
	public boolean existsByNameAndIdNot(String name, Long id) {
		return springDataCatalogRepository.existsByNameAndIdNot(name, id);
	}

	/**
	 * Verifica se existe pelo menos uma categoria associada ao catálogo com o ID
	 * informado.
	 *
	 * @param catalogId
	 *            ID do catálogo
	 * @return true se houver ao menos uma categoria associada, false caso contrário
	 */
	@Override @Transactional(readOnly = true)
	public boolean existsCategoryByCatalogId(Long catalogId) {
		return springDataCatalogRepository.existsCategoryByCatalogId(catalogId);
	}

	/**
	 * Remove um catálogo com base em seu ID.
	 *
	 * @param id
	 *            ID do catálogo a ser removido
	 */
	@Override @Transactional
	public void delete(Long id) {
		springDataCatalogRepository.deleteById(id);
	}

	/**
	 * Retorna um catalogo pelo ID do produto
	 *
	 * @param productId
	 *            ID do produto
	 */
	@Override @Transactional(readOnly = true)
	public Optional<CatalogDTO> findByProductId(Long productId) {
		return springDataCatalogRepository.findByProductId(productId).map(catalogEntityMapper::toDTO);
	}
}
