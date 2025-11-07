package integration.bdd.common.hooks;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.soat.fiap.food.core.payment.infrastructure.out.persistence.cosmosdb.nosql.entity.PaymentEntity;
import integration.bdd.common.config.CucumberSpringConfiguration;
import io.cucumber.java.After;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Classe responsável por definir Hooks comuns relacionados a Seed de banco de dados
 */
public class SeedHooks extends CucumberSpringConfiguration {

	private static final Logger log = LoggerFactory.getLogger(SeedHooks.class);

	@Autowired
	protected CosmosRepository<PaymentEntity, String> cosmosRepository;

	/**
	 * Hook executado antes de cada cenário Cucumber.
	 * Remove todos os documentos da coleção para garantir
	 * um estado limpo de banco.
	 */
	@After
	public void limparBanco() {
		log.debug("Resetando banco de dados");
		cosmosRepository.deleteAll();
	}
}
