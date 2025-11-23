package unit.fixtures;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.soat.fiap.food.core.payment.core.domain.events.PaymentApprovedEvent;
import com.soat.fiap.food.core.payment.core.domain.events.PaymentExpiredEvent;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events.StockDebitEventDto;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events.StockDebitItemEventDto;

/**
 * Fixture utilitária para criação de eventos do módulo Payment, utilizada
 * exclusivamente em testes unitários.
 * <p>
 * Fornece métodos para gerar eventos de pagamento e eventos de débito de
 * estoque, facilitando a construção de cenários de testes complexos.
 */
public class EventFixture {

	/**
	 * Cria um {@link PaymentApprovedEvent} válido com valores padrão.
	 *
	 * @return instância configurada de {@link PaymentApprovedEvent}
	 */
	public static PaymentApprovedEvent createPaymentApprovedEvent() {
		return new PaymentApprovedEvent(UUID.randomUUID(), 1L, BigDecimal.valueOf(100.00), "PIX", LocalDateTime.now());
	}

	/**
	 * Cria um {@link PaymentApprovedEvent} personalizado, permitindo configurar ID
	 * do pedido, valor e método de pagamento.
	 *
	 * @param orderId
	 *            ID do pedido associado ao pagamento
	 * @param amount
	 *            valor total aprovado
	 * @param paymentMethod
	 *            método utilizado para pagamento (ex: "PIX", "CREDIT_CARD")
	 * @return instância de {@link PaymentApprovedEvent}
	 */
	public static PaymentApprovedEvent createPaymentApprovedEvent(Long orderId, BigDecimal amount,
			String paymentMethod) {
		return new PaymentApprovedEvent(UUID.randomUUID(), orderId, amount, paymentMethod, LocalDateTime.now());
	}

	/**
	 * Cria um {@link PaymentExpiredEvent} válido utilizando valores padrão.
	 *
	 * @return instância configurada de {@link PaymentExpiredEvent}
	 */
	public static PaymentExpiredEvent createPaymentExpiredEvent() {
		return new PaymentExpiredEvent(UUID.randomUUID(), 1L, LocalDateTime.now().minusMinutes(15));
	}

	/**
	 * Cria um {@link PaymentExpiredEvent} personalizado, permitindo configurar o ID
	 * do pedido e o instante da expiração.
	 *
	 * @param orderId
	 *            ID do pedido associado ao pagamento
	 * @param expiredIn
	 *            instante em que o pagamento foi expirado
	 * @return instância de {@link PaymentExpiredEvent}
	 */
	public static PaymentExpiredEvent createPaymentExpiredEvent(Long orderId, LocalDateTime expiredIn) {
		return new PaymentExpiredEvent(UUID.randomUUID(), orderId, expiredIn);
	}

	/**
	 * Cria um {@link StockDebitEventDto} contendo um único item padrão.
	 * <p>
	 * Utilizado em testes que validam o comportamento de débitos simples de
	 * estoque.
	 *
	 * @param orderId
	 *            ID do pedido
	 * @param orderNumber
	 *            número do pedido
	 * @param userId
	 *            ID do usuário responsável (pode ser {@code null})
	 * @param totalAmount
	 *            valor total do pedido
	 * @return instância de {@link StockDebitEventDto}
	 */
	public static StockDebitEventDto createStockDebitEvent(Long orderId, String orderNumber, String userId,
			BigDecimal totalAmount) {

		StockDebitItemEventDto item = new StockDebitItemEventDto();
		item.setProductId(1L);
		item.setName("Produto Teste");
		item.setQuantity(1);
		item.setUnitPrice(totalAmount);
		item.setSubtotal(totalAmount);
		item.setObservations("Observação teste");

		StockDebitEventDto event = new StockDebitEventDto();
		event.setOrderId(orderId);
		event.setOrderNumber(orderNumber);
		event.setUserId(userId);
		event.setTotalAmount(totalAmount);
		event.setItems(List.of(item));

		return event;
	}

	/**
	 * Cria um {@link StockDebitEventDto} contendo múltiplos itens, distribuindo o
	 * valor total proporcionalmente entre eles.
	 * <p>
	 * Utilizado para cenários onde diversos produtos precisam ser debitados do
	 * estoque.
	 *
	 * @param orderId
	 *            ID do pedido
	 * @param orderNumber
	 *            número do pedido
	 * @param userId
	 *            ID do usuário responsável (pode ser {@code null})
	 * @param totalAmount
	 *            valor total do pedido
	 * @return instância de {@link StockDebitEventDto} com múltiplos itens
	 */
	public static StockDebitEventDto createStockDebitEventWithMultipleItems(Long orderId, String orderNumber,
			String userId, BigDecimal totalAmount) {

		int quantity1 = 1;
		int quantity2 = 3;
		int quantity3 = 7;
		int totalQuantity = quantity1 + quantity2 + quantity3;

		BigDecimal unitPrice1 = totalAmount.multiply(BigDecimal.valueOf(quantity1))
				.divide(BigDecimal.valueOf(totalQuantity), 2, RoundingMode.HALF_UP);

		BigDecimal unitPrice2 = totalAmount.multiply(BigDecimal.valueOf(quantity2))
				.divide(BigDecimal.valueOf(totalQuantity), 2, RoundingMode.HALF_UP);

		BigDecimal unitPrice3 = totalAmount.multiply(BigDecimal.valueOf(quantity3))
				.divide(BigDecimal.valueOf(totalQuantity), 2, RoundingMode.HALF_UP);

		StockDebitItemEventDto item1 = new StockDebitItemEventDto();
		item1.setProductId(1L);
		item1.setName("Produto Teste 1");
		item1.setQuantity(quantity1);
		item1.setUnitPrice(unitPrice1);
		item1.setSubtotal(unitPrice1.multiply(BigDecimal.valueOf(quantity1)));
		item1.setObservations("Observação teste 1");

		StockDebitItemEventDto item2 = new StockDebitItemEventDto();
		item2.setProductId(2L);
		item2.setName("Produto Teste 2");
		item2.setQuantity(quantity2);
		item2.setUnitPrice(unitPrice2);
		item2.setSubtotal(unitPrice2.multiply(BigDecimal.valueOf(quantity2)));
		item2.setObservations("Observação teste 2");

		StockDebitItemEventDto item3 = new StockDebitItemEventDto();
		item3.setProductId(3L);
		item3.setName("Produto Teste 3");
		item3.setQuantity(quantity3);
		item3.setUnitPrice(unitPrice3);
		item3.setSubtotal(unitPrice3.multiply(BigDecimal.valueOf(quantity3)));
		item3.setObservations("Observação teste 3");

		StockDebitEventDto event = new StockDebitEventDto();
		event.setOrderId(orderId);
		event.setOrderNumber(orderNumber);
		event.setUserId(userId);
		event.setTotalAmount(totalAmount);
		event.setItems(List.of(item1, item2, item3));

		return event;
	}
}
