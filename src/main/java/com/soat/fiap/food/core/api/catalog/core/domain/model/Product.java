package com.soat.fiap.food.core.api.catalog.core.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogException;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.ProductException;
import com.soat.fiap.food.core.api.catalog.core.domain.vo.Details;
import com.soat.fiap.food.core.api.catalog.core.domain.vo.ImageUrl;
import com.soat.fiap.food.core.api.shared.core.domain.exceptions.BusinessException;
import com.soat.fiap.food.core.api.shared.core.domain.vo.AuditInfo;

import lombok.Getter;
import lombok.Setter;

/**
 * Entidade de domínio que representa um produto.
 */
@Getter @Setter
public class Product {

	private Long id;
	private Details details;
	private BigDecimal price;
	private ImageUrl imageUrl;
	private Integer displayOrder;
	private AuditInfo auditInfo = new AuditInfo();
	private Category category;
	private Stock stock = new Stock();
	private boolean active = isActive();

	/**
	 * Construtor da classe Product.
	 *
	 * @param details
	 *            Detalhes do produto (nome e descrição)
	 * @param price
	 *            Preço do produto
	 * @param imageUrl
	 *            URL da imagem associada ao produto
	 * @param displayOrder
	 *            Ordem de exibição do produto
	 * @throws NullPointerException
	 *             se {@code details} ou {@code price} forem nulos
	 * @throws ProductException
	 *             se {@code price} for menor ou igual a zero
	 * @throws CatalogException
	 *             se {@code displayOrder} for menor que zero
	 */
	public Product(Details details, BigDecimal price, ImageUrl imageUrl, Integer displayOrder) {
		validate(details, price, displayOrder);
		this.details = details;
		this.price = price;
		this.imageUrl = imageUrl;
		this.displayOrder = displayOrder;
	}

	/**
	 * Valida os campos obrigatórios do produto.
	 *
	 * @param details
	 *            Detalhes do produto
	 * @param price
	 *            Preço do produto
	 * @param displayOrder
	 *            Ordem de exibição
	 * @throws NullPointerException
	 *             se {@code details} ou {@code price} forem nulos
	 * @throws ProductException
	 *             se {@code price} for menor ou igual a zero
	 * @throws ProductException
	 *             se {@code displayOrder} for menor ou igal a zero
	 */
	private void validate(Details details, BigDecimal price, Integer displayOrder) {
		Objects.requireNonNull(details, "Os detalhes do produto não podem ser nulos");
		Objects.requireNonNull(price, "O preço do produto não pode ser nulo");

		if (displayOrder != null && displayOrder <= 0) {
			throw new ProductException("A ordem de exibição do produto deve ser maior que 0");
		}
		if (price.compareTo(BigDecimal.ZERO) < 0) {
			throw new ProductException("O preço deve ser positivo");
		}
	}

	/**
	 * Retorna o ID da categoria associada ao produto.
	 *
	 * @return ID da categoria
	 */
	public Long getCategoryId() {
		return this.getCategory().getId();
	}

	/**
	 * Retorna a URL da imagem do produto.
	 *
	 * @return URL da imagem
	 */
	public String getImageUrlValue() {
		return this.imageUrl != null ? this.imageUrl.imageUrl() : null;
	}

	/**
	 * Define a url da imagem do produto.
	 */
	public void setImageUrlValue(String imagePath) {
		this.setImageUrl(new ImageUrl(imagePath));
		this.markUpdatedNow();
	}

	/**
	 * Verifica se a URL da imagem do produto está vazia.
	 *
	 * @return {@code true} se a URL estiver vazia; caso contrário, {@code false}
	 */
	public Boolean imageUrlIsEmpty() {
		return this.imageUrl.imageUrl().isEmpty();
	}

	/**
	 * Retorna a quantidade atual em estoque do produto.
	 *
	 * @return quantidade de estoque
	 */
	public Integer getStockQuantity() {
		return this.stock.getQuantity();
	}

	/**
	 * Define a quantidade em estoque do produto.
	 *
	 * @param quantity
	 *            nova quantidade de estoque
	 */
	public void setStockQuantity(Integer quantity) {
		this.stock.setQuantity(quantity);
		stock.markUpdatedNow();
	}

	/**
	 * Atualiza a data da última modificação do estoque para o momento atual.
	 */
	void markStockUpdateNow() {
		this.stock.markUpdatedNow();
	}

	/**
	 * Retorna o nome do produto.
	 *
	 * @return nome do produto
	 */
	public String getName() {
		return this.details.name();
	}

	/**
	 * Retorna a descrição do produto.
	 *
	 * @return descrição do produto
	 */
	public String getDescription() {
		return this.details.description();
	}

	/**
	 * Define o preço do produto.
	 *
	 * @param price
	 *            novo preço
	 * @throws ProductException
	 *             se {@code price} for menor ou igual a zero
	 */
	void setPrice(BigDecimal price) {
		if (price.compareTo(BigDecimal.ZERO) < 0) {
			throw new ProductException("O preço deve ser positivo");
		}

		this.price = price;
	}

	/**
	 * Define a ordem de exibição do produto.
	 *
	 * @param displayOrder
	 *            nova ordem de exibição
	 * @throws ProductException
	 *             se {@code displayOrder} for menor ou igal a zero
	 */
	void setDisplayOrder(Integer displayOrder) {
		if (displayOrder != null && displayOrder <= 0) {
			throw new ProductException("A ordem de exibição do produto deve ser maior que 0");
		}

		this.displayOrder = displayOrder;
	}

	/**
	 * Define a entidade produto do estoque.
	 *
	 * @param product
	 *            produto do estoque
	 */
	void setProductStock(Product product) {
		this.getStock().setProduct(product);
	}

	/**
	 * Verifica se o produto está disponível para venda.
	 *
	 * @return true se o produto estiver com preço válido e estoque positivo
	 */
	public boolean isActive() {
		this.active = price != null && price.compareTo(BigDecimal.ZERO) > 0 && stock != null && stock.getQuantity() > 0;
		return this.active;
	}

	/**
	 * Verifica se a categoria do produto está ativa.
	 *
	 * @return true se a categoria estiver ativa
	 */
	public boolean categoryisActive() {
		return category.isActive();
	}

	/**
	 * Obtem a data de criação de um produto.
	 */
	public LocalDateTime getCreatedAt() {
		return auditInfo.getCreatedAt();
	}

	/**
	 * Obtem a data de última atualização de um produto.
	 */
	public LocalDateTime getUpdatedAt() {
		return auditInfo.getUpdatedAt();
	}

	/**
	 * Atualiza o campo updatedAt com o horário atual.
	 *
	 * @throws BusinessException
	 *             se o horário atual for menor ou igual ao createdAt
	 */
	void markUpdatedNow() {
		this.auditInfo.setUpdatedAt(LocalDateTime.now());
	}
}
