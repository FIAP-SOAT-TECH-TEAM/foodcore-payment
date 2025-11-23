package unit.fixtures;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.soat.fiap.food.core.payment.core.application.inputs.AcquirerNotificationInput;
import com.soat.fiap.food.core.payment.core.application.inputs.StockDebitInput;
import com.soat.fiap.food.core.payment.core.domain.model.Payment;
import com.soat.fiap.food.core.payment.core.domain.vo.PaymentMethod;
import com.soat.fiap.food.core.payment.core.domain.vo.PaymentStatus;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.AcquirerPaymentDTO;
import com.soat.fiap.food.core.payment.infrastructure.in.web.api.dto.request.AcquirerNotificationRequest;

/**
 * Fixture para criação de objetos relacionados ao módulo de pagamentos,
 * facilitando a geração de cenários para testes unitários.
 * <p>
 * Inclui métodos utilitários para criar instâncias de {@link Payment},
 * {@link StockDebitInput}, {@link AcquirerNotificationInput},
 * {@link AcquirerNotificationRequest} e {@link AcquirerPaymentDTO}.
 * </p>
 */
public class PaymentFixture {

	/**
	 * Cria um pagamento válido com valores padrão.
	 *
	 * @return instância válida de {@link Payment}
	 */
	public static Payment createValidPayment() {
		return new Payment("as23as3", 1L, new BigDecimal("50.00"));
	}

	/**
	 * Cria um pagamento com status pendente.
	 *
	 * @return instância de {@link Payment} com status {@link PaymentStatus#PENDING}
	 */
	public static Payment createPendingPayment() {
		var payment = new Payment("as23as3", 1L, new BigDecimal("25.90"));
		payment.setId(UUID.randomUUID());
		payment.setStatus(PaymentStatus.PENDING);
		payment.setQrCode(
				"00020126580014br.gov.bcb.pix0136123e4567-e12b-12d1-a456-42661417400052040000530398654041.005802BR5925JOSE DA SILVA SAURO6009SAO PAULO62070503***6304");
		return payment;
	}

	/**
	 * Cria um pagamento aprovado.
	 *
	 * @return instância de {@link Payment} com status
	 *         {@link PaymentStatus#APPROVED}
	 */
	public static Payment createApprovedPayment() {
		var payment = new Payment("as23as3", 1L, new BigDecimal("35.50"));
		payment.setId(UUID.randomUUID());
		payment.setStatus(PaymentStatus.APPROVED);
		payment.setType(PaymentMethod.PIX);
		payment.setTid("12345678");
		payment.setQrCode(
				"00020126580014br.gov.bcb.pix0136123-e12b-12d1-a456-42661417400052040000530398654041.005802BR5925JOSE DA SILVA SAURO6009SAO PAULO62070503***6304");
		return payment;
	}

	/**
	 * Cria um pagamento cancelado.
	 *
	 * @return instância de {@link Payment} com status
	 *         {@link PaymentStatus#CANCELLED}
	 */
	public static Payment createCancelledPayment() {
		var payment = new Payment("as23as3", 2L, new BigDecimal("18.90"));
		payment.setId(UUID.randomUUID());
		payment.setStatus(PaymentStatus.CANCELLED);
		payment.setType(PaymentMethod.PIX);
		payment.setTid("87654321");
		return payment;
	}

	/**
	 * Cria um pagamento rejeitado.
	 *
	 * @return instância de {@link Payment} com status
	 *         {@link PaymentStatus#REJECTED}
	 */
	public static Payment createRejectedPayment() {
		var payment = new Payment("as23as3", 3L, new BigDecimal("42.00"));
		payment.setId(UUID.randomUUID());
		payment.setStatus(PaymentStatus.REJECTED);
		payment.setType(PaymentMethod.PIX);
		payment.setTid("11223344");
		payment.setQrCode(
				"00020126580014br.gov.bcb.pix0136123-e12b-12d1-a456-42661417400052040000530398654041.005802BR5925JOSE DA SILVA SAURO6009SAO PAULO62070503***6304");
		return payment;
	}

	/**
	 * Cria um pagamento expirado (tempo de expiração já passado).
	 *
	 * @return instância de {@link Payment} com status {@link PaymentStatus#PENDING}
	 *         e expiração vencida
	 */
	public static Payment createExpiredPayment() {
		var payment = new Payment("as23as3", 4L, new BigDecimal("29.90"));
		payment.setId(UUID.randomUUID());
		payment.setStatus(PaymentStatus.PENDING);
		payment.setExpiresIn(LocalDateTime.now().minusMinutes(30));
		payment.setQrCode(
				"00020126580014br.gov.bcb.pix0136123-e12b-12d1-a456-42661417400052040000530398654041.005802BR5925JOSE DA SILVA SAURO6009SAO PAULO62070503***6304");
		return payment;
	}

	/**
	 * Cria um pagamento com valor customizado.
	 *
	 * @param amount
	 *            valor do pagamento
	 * @return instância personalizada de {@link Payment}
	 */
	public static Payment createPaymentWithCustomAmount(BigDecimal amount) {
		return new Payment("as23as3", 1L, amount);
	}

	/**
	 * Cria um pagamento associado a um usuário específico.
	 *
	 * @param userId
	 *            identificador do usuário
	 * @return instância de {@link Payment} associada ao usuário informado
	 */
	public static Payment createPaymentForUser(String userId) {
		return new Payment(userId, 1L, new BigDecimal("25.00"));
	}

	/**
	 * Cria um pagamento associado a um pedido específico.
	 *
	 * @param orderId
	 *            identificador do pedido
	 * @return instância de {@link Payment} associada ao pedido informado
	 */
	public static Payment createPaymentForOrder(Long orderId) {
		return new Payment("as23as3", orderId, new BigDecimal("30.00"));
	}

	/**
	 * Cria um input válido para débito de estoque.
	 *
	 * @return instância de {@link StockDebitInput} com valores padrão
	 */
	public static StockDebitInput createValidOrderCreatedInput() {
		return new StockDebitInput(1L, "ORD-001", "as23as3", new BigDecimal("50.00"), List.of());
	}

	/**
	 * Cria um input de débito de estoque com valor total customizado.
	 *
	 * @param amount
	 *            valor total do pedido
	 * @return instância de {@link StockDebitInput} com o valor especificado
	 */
	public static StockDebitInput createOrderCreatedInputWithCustomAmount(BigDecimal amount) {
		return new StockDebitInput(1L, "ORD-002", "as23as3", amount, List.of());
	}

	/**
	 * Cria um input válido de notificação de adquirente.
	 *
	 * @return instância de {@link AcquirerNotificationInput} com valores padrão
	 */
	public static AcquirerNotificationInput createValidAcquirerNotificationInput() {
		return new AcquirerNotificationInput(1L, false, "payment", java.time.ZonedDateTime.now(), 1L, "v1",
				"payment_notification", "12345678");
	}

	/**
	 * Cria uma requisição válida de notificação de adquirente.
	 *
	 * @return instância de {@link AcquirerNotificationRequest} preenchida
	 */
	public static AcquirerNotificationRequest createValidAcquirerNotificationRequest() {
		var data = new AcquirerNotificationRequest.Data();
		data.setId("1234567890");

		var request = new AcquirerNotificationRequest();
		request.setId(1L);
		request.setLiveMode(false);
		request.setType("payment");
		request.setDateCreated(java.time.ZonedDateTime.now());
		request.setUserId(987654321L);
		request.setApiVersion("v1");
		request.setAction("payment.created");
		request.setData(data);

		return request;
	}

	/**
	 * Cria uma saída de pagamento do adquirente com status aprovado.
	 *
	 * @return instância de {@link AcquirerPaymentDTO} com status
	 *         {@link PaymentStatus#APPROVED}
	 */
	public static AcquirerPaymentDTO createValidAcquirerPaymentOutput() {
		return new AcquirerPaymentDTO("12345678", PaymentStatus.APPROVED, 1L, PaymentMethod.PIX);
	}

	/**
	 * Cria uma saída de pagamento do adquirente com status rejeitado.
	 *
	 * @return instância de {@link AcquirerPaymentDTO} com status
	 *         {@link PaymentStatus#REJECTED}
	 */
	public static AcquirerPaymentDTO createRejectedAcquirerPaymentOutput() {
		return new AcquirerPaymentDTO("87654321", PaymentStatus.REJECTED, 1L, PaymentMethod.PIX);
	}

	/**
	 * Cria uma saída de pagamento do adquirente com status cancelado.
	 *
	 * @return instância de {@link AcquirerPaymentDTO} com status
	 *         {@link PaymentStatus#CANCELLED}
	 */
	public static AcquirerPaymentDTO createCancelledAcquirerPaymentOutput() {
		return new AcquirerPaymentDTO("11223344", PaymentStatus.CANCELLED, 1L, PaymentMethod.PIX);
	}
}
