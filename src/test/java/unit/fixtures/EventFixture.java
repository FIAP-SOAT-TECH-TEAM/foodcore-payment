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
 * Fixture para criação de eventos do módulo Payment para testes unitários
 */
public class EventFixture {

	/**
	 * Cria um PaymentApprovedEvent válido
	 *
	 * @return instância de PaymentApprovedEvent
	 */
	public static PaymentApprovedEvent createPaymentApprovedEvent() {
		return new PaymentApprovedEvent(UUID.randomUUID(), 1L, BigDecimal.valueOf(100.00), "PIX", LocalDateTime.now());
	}

	/**
	 * Cria um PaymentApprovedEvent com valores customizados
	 *
	 * @param orderId
	 *            ID do pedido
	 * @param amount
	 *            Valor do pagamento
	 * @param paymentMethod
	 *            Método de pagamento
	 * @return instância de PaymentApprovedEvent
	 */
	public static PaymentApprovedEvent createPaymentApprovedEvent(Long orderId, BigDecimal amount,
			String paymentMethod) {
		return new PaymentApprovedEvent(UUID.randomUUID(), orderId, amount, paymentMethod, LocalDateTime.now());
	}

	/**
	 * Cria um PaymentExpiredEvent válido
	 *
	 * @return instância de PaymentExpiredEvent
	 */
	public static PaymentExpiredEvent createPaymentExpiredEvent() {
		return new PaymentExpiredEvent(UUID.randomUUID(), 1L, LocalDateTime.now().minusMinutes(15));
	}

	/**
	 * Cria um PaymentExpiredEvent com valores customizados
	 *
	 * @param orderId
	 *            ID do pedido
	 * @param expiredIn
	 *            Data de expiração do pagamento
	 * @return instância de PaymentExpiredEvent
	 */
	public static PaymentExpiredEvent createPaymentExpiredEvent(Long orderId, LocalDateTime expiredIn) {
		return new PaymentExpiredEvent(UUID.randomUUID(), orderId, expiredIn);
	}

	/**
	 * Cria um StockDebitEventDto com um item padrão para testes de débito de
	 * estoque.
	 *
	 * @param orderId
	 *            ID do pedido
	 * @param orderNumber
	 *            Número do pedido
	 * @param userId
	 *            ID do usuário (pode ser null)
	 * @param totalAmount
	 *            Valor total do pedido
	 * @return instância de StockDebitEventDto
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
	 * Cria um StockDebitEventDto com múltiplos itens para testes de débito de
	 * estoque.
	 *
	 * @param orderId
	 *            ID do pedido
	 * @param orderNumber
	 *            Número do pedido
	 * @param userId
	 *            ID do usuário (pode ser null)
	 * @param totalAmount
	 *            Valor total do pedido
	 * @return instância de StockDebitEventDto
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
