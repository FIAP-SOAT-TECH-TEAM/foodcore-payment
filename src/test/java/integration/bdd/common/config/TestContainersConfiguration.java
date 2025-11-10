package integration.bdd.common.config;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.time.Duration;
import java.util.Map;

import org.junit.jupiter.api.io.TempDir;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.CosmosDBEmulatorContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.azure.spring.cloud.autoconfigure.implementation.context.AzureGlobalPropertiesAutoConfiguration;
import com.azure.spring.cloud.autoconfigure.implementation.cosmos.AzureCosmosAutoConfiguration;

/**
 * Configuração base para testes de integração utilizando o Azure Cosmos DB
 * Emulator com o suporte do Testcontainers e integração automática com o Spring
 * Boot.
 * <p>
 * Esta classe configura e inicializa um contêiner do Cosmos DB Emulator, gera
 * um keystore temporário para autenticação SSL e ajusta as propriedades do
 * sistema para permitir a comunicação segura entre o cliente e o emulador.
 * </p>
 *
 * <p>
 * <b>Recursos relacionados:</b>
 * </p>
 * <ul>
 * <li><a href=
 * "https://learn.microsoft.com/en-us/azure/developer/java/spring-framework/testcontainers-support?tabs=test-for-cosmos">
 * Testcontainers Support for Spring - Azure Docs</a></li>
 * <li><a href="https://testcontainers.com/modules/cosmodb/"> Testcontainers
 * CosmosDB Module</a></li>
 * <li><a href=
 * "https://github.com/testcontainers/testcontainers-java/issues/5152"> GitHub
 * Issue: Testcontainers CosmosDB</a></li>
 * </ul>
 *
 * @see <a href=
 *      "https://docs.spring.io/spring-boot/reference/testing/testcontainers.html">
 *      Spring Testcontainers Documentation</a>
 */
@Testcontainers
@ImportAutoConfiguration(classes = {AzureGlobalPropertiesAutoConfiguration.class, AzureCosmosAutoConfiguration.class})
public abstract class TestContainersConfiguration {

	/** Diretório temporário usado para armazenar o arquivo de keystore. */
	@TempDir
	protected static File tempFolder;

	/** Contêiner do Azure Cosmos DB Emulator configurado para uso em testes. */
	@Container @ServiceConnection
	static CosmosDBEmulatorContainer cosmos = new CosmosDBEmulatorContainer(
			DockerImageName.parse("mcr.microsoft.com/cosmosdb/linux/azure-cosmos-emulator:latest"))
			.withEnv(Map.of("AZURE_COSMOS_EMULATOR_IP_ADDRESS_OVERRIDE", "127.0.0.1",
					"AZURE_COSMOS_EMULATOR_PARTITION_COUNT", "1", "AZURE_COSMOS_EMULATOR_ENABLE_DATA_PERSISTENCE",
					"false"))
			.waitingFor(Wait.forHttps("/_explorer/emulator.pem").forStatusCode(200).allowInsecure())
			.withStartupTimeout(Duration.ofMinutes(3));

	static {
		cosmos.start();

		Path keyStoreFile = new File(tempFolder, "azure-cosmos-emulator.keystore").toPath();
		KeyStore keyStore = cosmos.buildNewKeyStore();
		try {
			keyStore.store(Files.newOutputStream(keyStoreFile.toFile().toPath()),
					cosmos.getEmulatorKey().toCharArray());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		System.setProperty("javax.net.ssl.trustStore", keyStoreFile.toString());
		System.setProperty("javax.net.ssl.trustStorePassword", cosmos.getEmulatorKey());
		System.setProperty("javax.net.ssl.trustStoreType", "PKCS12");
	}
}
