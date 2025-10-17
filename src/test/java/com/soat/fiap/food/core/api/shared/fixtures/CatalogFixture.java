package com.soat.fiap.food.core.api.shared.fixtures;

import java.math.BigDecimal;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.CatalogInput;
import com.soat.fiap.food.core.api.catalog.core.application.inputs.CategoryInput;
import com.soat.fiap.food.core.api.catalog.core.application.inputs.ProductInput;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Catalog;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Category;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Product;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Stock;
import com.soat.fiap.food.core.api.catalog.core.domain.vo.Details;
import com.soat.fiap.food.core.api.catalog.core.domain.vo.ImageUrl;

/**
 * Fixture para criação de objetos do módulo Catalog para testes unitários
 */
public class CatalogFixture {

	public static Catalog createValidCatalog() {
		return new Catalog("Catálogo Principal");
	}

	public static Catalog createValidCatalog(String name) {
		return new Catalog(name);
	}

	public static Catalog createCatalogWithCategories() {
		var catalog = new Catalog("Catálogo com Categorias");

		var category1 = createValidCategory();
		category1.setId(1L);
		catalog.addCategory(category1);

		var category2 = createBeverageCategory();
		category2.setId(2L);
		catalog.addCategory(category2);

		return catalog;
	}

	public static Catalog createCatalogWithProducts() {
		var catalog = new Catalog("Catálogo com Produtos");

		var category = createValidCategory();
		category.setId(1L);
		catalog.addCategory(category);

		var product = createValidProduct();
		product.setId(1L);
		catalog.addProductToCategory(1L, product);

		return catalog;
	}

	public static Catalog createEmptyCatalog() {
		return new Catalog("Catálogo Vazio");
	}

	public static Catalog createCatalogWithInactiveCategory() {
		var catalog = new Catalog("Catálogo com Categoria Inativa");

		var category = createInactiveCategory();
		category.setId(1L);
		catalog.addCategory(category);

		return catalog;
	}

	public static Catalog createCatalogWithMultipleProducts() {
		var catalog = new Catalog("Catálogo Completo");

		var category1 = createValidCategory();
		category1.setId(1L);
		catalog.addCategory(category1);

		var category2 = createBeverageCategory();
		category2.setId(2L);
		catalog.addCategory(category2);

		var product1 = createValidProduct();
		product1.setId(1L);
		catalog.addProductToCategory(1L, product1);

		var product2 = createBeverageProduct();
		product2.setId(2L);
		catalog.addProductToCategory(2L, product2);

		var product3 = createExpensiveProduct();
		product3.setId(3L);
		catalog.addProductToCategory(1L, product3);

		return catalog;
	}

	public static Category createValidCategory() {
		return new Category(new Details("Lanches", "Deliciosos lanches artesanais"),
				new ImageUrl("https://example.com/images/lanches.jpg"), 1, true);
	}

	public static Category createBeverageCategory() {
		return new Category(new Details("Bebidas", "Bebidas refrescantes e sucos naturais"),
				new ImageUrl("https://example.com/images/bebidas.jpg"), 2, true);
	}

	public static Category createDessertCategory() {
		return new Category(new Details("Sobremesas", "Doces e sobremesas irresistíveis"),
				new ImageUrl("https://example.com/images/sobremesas.jpg"), 3, true);
	}

	public static Category createInactiveCategory() {
		return new Category(new Details("Categoria Inativa", "Categoria desabilitada para testes"),
				new ImageUrl("https://example.com/images/inactive.jpg"), 4, false);
	}

	public static Product createValidProduct() {
		Product product = new Product(new Details("Big Mac",
				"Delicioso hambúrguer com dois hambúrgueres, alface, queijo, molho especial, cebola e picles em um pão com gergelim"),
				new BigDecimal("25.90"), new ImageUrl("https://example.com/images/bigmac.jpg"), 10);

		Category category = createValidCategory();
		category.setId(1L);
		product.setCategory(category);

		var stock = new Stock(10);
		product.setStock(stock);

		return product;
	}

	public static Product createBeverageProduct() {
		Product product = new Product(new Details("Coca-Cola", "Refrigerante Coca-Cola 350ml gelado"),
				new BigDecimal("8.50"), new ImageUrl("https://example.com/images/coca.jpg"), 20);

		Category category = createBeverageCategory();
		category.setId(2L);
		product.setCategory(category);

		// Configurar estoque para que o produto seja considerado ativo
		var stock = new Stock(20);
		product.setStock(stock);

		return product;
	}

	public static Product createDessertProduct() {
		Product product = new Product(
				new Details("Torta de Chocolate", "Deliciosa torta de chocolate com cobertura cremosa"),
				new BigDecimal("18.90"), new ImageUrl("https://example.com/images/torta.jpg"), 5);

		Category category = createDessertCategory();
		category.setId(3L);
		product.setCategory(category);

		return product;
	}

	public static Product createExpensiveProduct() {
		Product product = new Product(
				new Details("Combo Premium", "Combo premium com hambúrguer gourmet, batata premium e bebida especial"),
				new BigDecimal("45.00"), new ImageUrl("https://example.com/images/combo-premium.jpg"), 3);

		Category category = createValidCategory();
		category.setId(1L);
		product.setCategory(category);

		// Configurar estoque para que o produto seja considerado ativo
		var stock = new Stock(3);
		product.setStock(stock);

		return product;
	}

	public static Product createProductWithoutImage() {
		Product product = new Product(
				new Details("Hambúrguer Simples", "Hambúrguer tradicional com carne, queijo e salada"),
				new BigDecimal("15.90"), null, 8);

		Category category = createValidCategory();
		category.setId(1L);
		product.setCategory(category);

		return product;
	}

	public static Product createInactiveProduct() {
		Product product = new Product(new Details("Produto Inativo", "Produto desabilitado para testes"),
				new BigDecimal("12.50"), new ImageUrl("https://example.com/images/inactive-product.jpg"), 0);
		product.setActive(false);

		Category category = createValidCategory();
		category.setId(1L);
		product.setCategory(category);

		return product;
	}

	public static Product createOutOfStockProduct() {
		Product product = new Product(new Details("Produto Sem Estoque", "Produto temporariamente indisponível"),
				new BigDecimal("22.90"), new ImageUrl("https://example.com/images/out-of-stock.jpg"), 0);

		Category category = createValidCategory();
		category.setId(1L);
		product.setCategory(category);

		return product;
	}

	// Input DTOs para testes
	public static CatalogInput createValidCatalogInput() {
		return new CatalogInput("Novo Catálogo");
	}

	public static CatalogInput createConflictingCatalogInput() {
		return new CatalogInput("Catálogo Principal");
	}

	public static CategoryInput createValidCategoryInput() {
		return new CategoryInput(1L, "Nova Categoria", "Descrição da nova categoria", true, 1);
	}

	public static ProductInput createValidProductInput() {
		return new ProductInput(1L, "Novo Produto", "Descrição do novo produto", new BigDecimal("19.90"), 15, 1);
	}
}
