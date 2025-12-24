package com.soat.fiap.food.core.payment.infrastructure.out.persistence.cosmosdb.nosql.seeder;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soat.fiap.food.core.payment.infrastructure.out.common.PaymentPopulator;
import com.soat.fiap.food.core.payment.infrastructure.out.persistence.cosmosdb.nosql.entity.PaymentEntity;
import com.soat.fiap.food.core.payment.infrastructure.out.persistence.cosmosdb.nosql.repository.CosmosDbPaymentRepository;

import lombok.RequiredArgsConstructor;

/**
 * Classe responsável por popular a coleção de pagamentos no Cosmos DB a partir
 * de um arquivo JSON localizado em resources/seeder/data.json.
 * <p>
 * Também fornece método para limpar toda a coleção.
 */
@Component @RequiredArgsConstructor
public class CosmosDBPopulator implements PaymentPopulator {

	/** Repositório para persistir os pagamentos no Cosmos DB */
	private final CosmosDbPaymentRepository repository;

	/** ObjectMapper para converter JSON em objetos Java */
	private final ObjectMapper objectMapper;

	/**
	 * Popula a coleção de pagamentos caso esteja vazia. Lê os dados do arquivo
	 * "resources/seeder/data.json" e salva no repositório.
	 *
	 * @throws IOException
	 *             caso ocorra erro ao ler o arquivo JSON
	 */
	@Override
	public void populate() throws IOException {
		if (repository.count() > 0) {
			return;
		}

		var resource = new ClassPathResource("seeder/data.json");
		List<PaymentEntity> payments = objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {
		});

		repository.saveAll(payments);
	}

	/**
	 * Remove todos os registros da coleção de pagamentos. Útil para testes ou reset
	 * de dados.
	 */
	@Override
	public void reset() {
		repository.deleteAll();
	}
}
