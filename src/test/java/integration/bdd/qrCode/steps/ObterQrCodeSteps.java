package integration.bdd.qrCode.steps;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import integration.bdd.common.config.CucumberSpringConfiguration;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;

/**
 * Steps BDD responsáveis por validar a recuperação bem-sucedida do QrCode de
 * pagamento por ID do pedido.
 * <p>
 * Executa chamadas reais HTTP contra o endpoint:
 *
 * <pre>
 * GET /{orderId}/qrCode
 * </pre>
 * </p>
 */
public class ObterQrCodeSteps extends CucumberSpringConfiguration {

	private Response response;
	private String clientId;

	/**
	 * Define o cliente do pedido
	 *
	 * @param userId
	 *            ID do cliente do pedido.
	 */
	@Quando("que o ID do cliente é {string}")
	public void oIdDoClienteE(String userId) {
		clientId = userId;
	}

	/**
	 * Executa uma requisição GET para recuperar o QrCode de um pedido específico.
	 *
	 * @param orderId
	 *            ID do pedido utilizado na URL.
	 */
	@Quando("o cliente buscar o QrCode de pagamento pelo ID do pedido {string}")
	public void oClienteBuscarQrCodePeloId(String orderId) {
		response = given().header("Auth-Subject", clientId)
				.header("Auth-Role", "CUSTOMER")
				.when()
				.get("/" + orderId + "/qrCode")
				.then()
				.extract()
				.response();
	}

	/**
	 * Valida o código de status HTTP retornado pela API.
	 *
	 * @param statusCode
	 *            código esperado, ex: 200.
	 */
	@Entao("o sistema deve retornar o status {int}")
	public void oSistemaDeveRetornarStatus(int statusCode) {
		response.then().statusCode(statusCode);
	}

	/**
	 * Verifica se o corpo JSON contém o campo informado com o valor esperado.
	 *
	 * @param campo
	 *            nome do campo JSON.
	 * @param valor
	 *            valor esperado.
	 */
	@Entao("o corpo da resposta deve conter o campo {string} igual a {string}")
	public void corpoDeveConterCampoComValor(String campo, String valor) {
		response.then().body(campo, equalTo(valor));
	}

	/**
	 * Verifica se o corpo da resposta contém o campo informado.
	 *
	 * @param campo
	 *            nome do campo JSON esperado.
	 */
	@Entao("o corpo da resposta deve conter o campo {string}")
	public void corpoDeveConterCampo(String campo) {
		response.then().body("$", hasKey(campo));
	}

	/**
	 * Verifica se o campo especificado está presente no JSON de resposta.
	 *
	 * @param campo
	 *            nome do campo JSON esperado.
	 */
	@Entao("o campo {string} deve estar presente")
	public void campoDeveEstarPresente(String campo) {
		response.then().body("$", hasKey(campo));
	}
}
