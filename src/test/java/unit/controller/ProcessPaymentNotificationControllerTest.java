package unit.controller;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.payment.core.application.usecases.ProcessPaymentNotificationUseCase;
import com.soat.fiap.food.core.payment.core.application.usecases.PublishPaymentApprovedEventUseCase;
import com.soat.fiap.food.core.payment.core.domain.exceptions.PaymentAlreadyProcessedException;
import com.soat.fiap.food.core.payment.core.interfaceadapters.controller.ProcessPaymentNotificationController;
import com.soat.fiap.food.core.payment.infrastructure.common.source.AcquirerSource;
import com.soat.fiap.food.core.payment.infrastructure.common.source.EventPublisherSource;
import com.soat.fiap.food.core.payment.infrastructure.common.source.PaymentDataSource;

import unit.fixtures.PaymentFixture;

/**
 * Testes unitários para {@link ProcessPaymentNotificationController}.
 */
@ExtendWith(MockitoExtension.class) @DisplayName("ProcessPaymentNotificationController - Testes Unitários")
class ProcessPaymentNotificationControllerTest {

	@Mock
	private PaymentDataSource paymentDataSource;

	@Mock
	private AcquirerSource acquirerSource;

	@Mock
	private EventPublisherSource eventPublisherSource;

	@Test @DisplayName("Deve processar notificação de pagamento com sucesso e publicar evento quando aprovado")
	void shouldProcessNotificationAndPublishApprovedEventSuccessfully() {
		// Arrange
		var request = PaymentFixture.createValidAcquirerNotificationRequest();
		var payment = PaymentFixture.createApprovedPayment();

		try (var processMock = mockStatic(ProcessPaymentNotificationUseCase.class);
				var publishMock = mockStatic(PublishPaymentApprovedEventUseCase.class)) {

			processMock.when(() -> ProcessPaymentNotificationUseCase.processPaymentNotification(any(), any(), any()))
					.thenReturn(payment);

			when(paymentDataSource.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

			// Act & Assert
			assertThatNoException().isThrownBy(() -> ProcessPaymentNotificationController
					.processPaymentNotification(request, paymentDataSource, acquirerSource, eventPublisherSource));

			processMock.verify(() -> ProcessPaymentNotificationUseCase.processPaymentNotification(any(), any(), any()));
			publishMock.verify(() -> PublishPaymentApprovedEventUseCase.publishPaymentApprovedEvent(any(), any()));
		}
	}

	@Test @DisplayName("Não deve publicar evento quando pagamento não for aprovado")
	void shouldNotPublishEventWhenPaymentIsNotApproved() {
		// Arrange
		var request = PaymentFixture.createValidAcquirerNotificationRequest();
		var payment = PaymentFixture.createPendingPayment();

		try (var processMock = mockStatic(ProcessPaymentNotificationUseCase.class);
				var publishMock = mockStatic(PublishPaymentApprovedEventUseCase.class)) {

			processMock.when(() -> ProcessPaymentNotificationUseCase.processPaymentNotification(any(), any(), any()))
					.thenReturn(payment);

			when(paymentDataSource.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

			// Act
			ProcessPaymentNotificationController.processPaymentNotification(request, paymentDataSource, acquirerSource,
					eventPublisherSource);

			// Assert
			processMock.verify(() -> ProcessPaymentNotificationUseCase.processPaymentNotification(any(), any(), any()));
			publishMock.verifyNoInteractions();
		}
	}

	@Test @DisplayName("Deve capturar PaymentAlreadyProcessedException sem lançar exceção")
	void shouldHandlePaymentAlreadyProcessedExceptionGracefully() {
		// Arrange
		var request = PaymentFixture.createValidAcquirerNotificationRequest();

		try (var processMock = mockStatic(ProcessPaymentNotificationUseCase.class)) {

			processMock.when(() -> ProcessPaymentNotificationUseCase.processPaymentNotification(any(), any(), any()))
					.thenThrow(new PaymentAlreadyProcessedException("Pagamento já processado."));

			// Act & Assert
			assertThatNoException().isThrownBy(() -> ProcessPaymentNotificationController
					.processPaymentNotification(request, paymentDataSource, acquirerSource, eventPublisherSource));

			processMock.verify(() -> ProcessPaymentNotificationUseCase.processPaymentNotification(any(), any(), any()));
		}
	}
}
