package com.soat.fiap.food.core.payment.unit.datasource;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.google.gson.Gson;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events.PaymentApprovedEventDto;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events.PaymentExpiredEventDto;
import com.soat.fiap.food.core.payment.infrastructure.out.event.publisher.azsvcbus.AzSvcBusEventPublisher;

/**
 * Testes unitários para {@link AzSvcBusEventPublisher}.
 * <p>
 * Valida a publicação de eventos de pagamento aprovado e expirado
 * no Azure Service Bus sem lançar exceções.
 * </p>
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AzSvcBusEventPublisher - Testes Unitários")
class AzSvcBusEventPublisherTest {

	@Mock
	private ServiceBusSenderClient paymentApprovedSender;

	@Mock
	private ServiceBusSenderClient paymentExpiredSender;

	@Mock
	private Gson gson;

	private AzSvcBusEventPublisher eventPublisher;

	@BeforeEach
	void setUp() {
		eventPublisher = new AzSvcBusEventPublisher(paymentApprovedSender, paymentExpiredSender, gson);
	}

	@Test
	@DisplayName("Deve publicar evento de pagamento aprovado com sucesso")
	void shouldPublishPaymentApprovedEventSuccessfully() {
		// Arrange
		var event = new PaymentApprovedEventDto();
		event.setPaymentId(UUID.randomUUID());
		event.setAmount(new BigDecimal("100.0"));

		when(gson.toJson(event))
				.thenReturn("{\"paymentId\":\"" + event.getPaymentId() + "\",\"amount\":100.0}");

		// Act & Assert
		assertThatNoException()
				.isThrownBy(() -> eventPublisher.publishPaymentApprovedEvent(event));

		// Assert
		verify(paymentApprovedSender).sendMessage(any(ServiceBusMessage.class));
	}

	@Test
	@DisplayName("Deve publicar evento de pagamento expirado com sucesso")
	void shouldPublishPaymentExpiredEventSuccessfully() {
		// Arrange
		var event = new PaymentExpiredEventDto();
		event.setPaymentId(UUID.randomUUID());

		when(gson.toJson(event))
				.thenReturn("{\"paymentId\":\"" + event.getPaymentId() + "\"}");

		// Act & Assert
		assertThatNoException()
				.isThrownBy(() -> eventPublisher.publishPaymentExpiredEvent(event));

		// Assert
		verify(paymentExpiredSender).sendMessage(any(ServiceBusMessage.class));
	}

	@Test
	@DisplayName("Deve publicar múltiplos eventos com sucesso")
	void shouldPublishMultipleEventsSuccessfully() {
		// Arrange
		var approvedEvent1 = new PaymentApprovedEventDto();
		approvedEvent1.setPaymentId(UUID.randomUUID());
		approvedEvent1.setAmount(new BigDecimal("50.0"));

		var approvedEvent2 = new PaymentApprovedEventDto();
		approvedEvent2.setPaymentId(UUID.randomUUID());
		approvedEvent2.setAmount(new BigDecimal("75.0"));

		var expiredEvent = new PaymentExpiredEventDto();
		expiredEvent.setPaymentId(UUID.randomUUID());

		when(gson.toJson(approvedEvent1))
				.thenReturn("{\"paymentId\":\"" + approvedEvent1.getPaymentId() + "\",\"amount\":50.0}");
		when(gson.toJson(approvedEvent2))
				.thenReturn("{\"paymentId\":\"" + approvedEvent2.getPaymentId() + "\",\"amount\":75.0}");
		when(gson.toJson(expiredEvent))
				.thenReturn("{\"paymentId\":\"" + expiredEvent.getPaymentId() + "\"}");

		// Act & Assert
		assertThatNoException()
				.isThrownBy(() -> {
					eventPublisher.publishPaymentApprovedEvent(approvedEvent1);
					eventPublisher.publishPaymentApprovedEvent(approvedEvent2);
					eventPublisher.publishPaymentExpiredEvent(expiredEvent);
				});

		// Assert
		verify(paymentApprovedSender, times(2))
				.sendMessage(any(ServiceBusMessage.class));
		verify(paymentExpiredSender)
				.sendMessage(any(ServiceBusMessage.class));
	}
}
