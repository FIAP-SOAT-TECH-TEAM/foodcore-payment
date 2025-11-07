package unit.gateway;

import com.soat.fiap.food.core.payment.core.domain.events.PaymentApprovedEvent;
import com.soat.fiap.food.core.payment.core.domain.events.PaymentExpiredEvent;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.mappers.PaymentApprovedEventMapper;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.mappers.PaymentExpiredEventMapper;
import com.soat.fiap.food.core.payment.core.interfaceadapters.gateways.EventPublisherGateway;
import com.soat.fiap.food.core.payment.infrastructure.common.source.EventPublisherSource;
import unit.fixtures.EventFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.verify;

/**
 * Testes unitários para {@link EventPublisherGateway}.
 * <p>
 * Valida a delegação correta para {@link EventPublisherSource}
 * ao publicar eventos de pagamento aprovados e expirados.
 * </p>
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("EventPublisherGateway - Testes Unitários")
class EventPublisherGatewayTest {

	@Mock
	private EventPublisherSource eventPublisherSource;

	@Test
	@DisplayName("Deve publicar evento PaymentApprovedEvent com sucesso")
	void shouldPublishPaymentApprovedEventSuccessfully() {
		var gateway = new EventPublisherGateway(eventPublisherSource);
		PaymentApprovedEvent event = EventFixture.createPaymentApprovedEvent();

		assertThatNoException().isThrownBy(() -> gateway.publishPaymentApprovedEvent(event));

		verify(eventPublisherSource).publishPaymentApprovedEvent(
				PaymentApprovedEventMapper.toDto(event)
		);
	}

	@Test
	@DisplayName("Deve publicar evento PaymentExpiredEvent com sucesso")
	void shouldPublishPaymentExpiredEventSuccessfully() {
		var gateway = new EventPublisherGateway(eventPublisherSource);
		PaymentExpiredEvent event = EventFixture.createPaymentExpiredEvent();

		assertThatNoException().isThrownBy(() -> gateway.publishPaymentExpiredEvent(event));

		verify(eventPublisherSource).publishPaymentExpiredEvent(
				PaymentExpiredEventMapper.toDto(event)
		);
	}

	@Test
	@DisplayName("Deve delegar corretamente múltiplos eventos para EventPublisherSource")
	void shouldDelegateCorrectlyForMultipleEvents() {
		var gateway = new EventPublisherGateway(eventPublisherSource);
		PaymentApprovedEvent approvedEvent = EventFixture.createPaymentApprovedEvent(789L,
				java.math.BigDecimal.valueOf(150.00), "PIX");
		PaymentExpiredEvent expiredEvent = EventFixture.createPaymentExpiredEvent(321L,
				java.time.LocalDateTime.now());

		gateway.publishPaymentApprovedEvent(approvedEvent);
		gateway.publishPaymentExpiredEvent(expiredEvent);

		verify(eventPublisherSource).publishPaymentApprovedEvent(
				PaymentApprovedEventMapper.toDto(approvedEvent)
		);
		verify(eventPublisherSource).publishPaymentExpiredEvent(
				PaymentExpiredEventMapper.toDto(expiredEvent)
		);
	}
}
