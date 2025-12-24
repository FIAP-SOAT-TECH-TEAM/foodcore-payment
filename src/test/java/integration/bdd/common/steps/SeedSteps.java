package integration.bdd.common.steps;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soat.fiap.food.core.payment.infrastructure.out.persistence.cosmosdb.nosql.entity.PaymentEntity;

import integration.bdd.common.config.CucumberSpringConfiguration;
import io.cucumber.java.pt.Dado;

/**
 * Classe responsável por definir Steps comuns relacionados a Seed de banco de
 * dados
 */
public class SeedSteps extends CucumberSpringConfiguration {

	private static final Logger log = LoggerFactory.getLogger(SeedSteps.class);

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	protected CosmosRepository<PaymentEntity, String> cosmosRepository;

	/**
	 * Carrega pagamentos de um arquivo JSON e os insere no banco.
	 *
	 * @throws IOException
	 *             se ocorrer erro na leitura do arquivo de dados.
	 */
	@Dado("que existam pagamentos")
	public void queExistamPagamentos() throws IOException {
		log.debug("Inserindo pagamentos no banco de dados");

		var resource = new ClassPathResource("seeder/data.json");
		List<PaymentEntity> payments = objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {
		});
		cosmosRepository.saveAll(payments);
	}
}
