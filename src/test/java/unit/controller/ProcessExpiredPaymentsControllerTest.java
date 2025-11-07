package unit.controller;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThatNoException;

import java.util.List;
import java.util.Collections;

import com.soat.fiap.food.core.payment.core.application.usecases.GetExpiredPaymentsUseCase;
import com.soat.fiap.food.core.payment.core.application.usecases.PublishPaymentExpiredEventUseCase;
import com.soat.fiap.food.core.payment.core.application.usecases.UpdatePaymentStatusUseCase;
import com.soat.fiap.food.core.payment.core.domain.vo.PaymentStatus;
import com.soat.fiap.food.core.payment.core.interfaceadapters.bff.controller.web.api.ProcessExpiredPaymentsController;
import com.soat.fiap.food.core.payment.infrastructure.common.source.EventPublisherSource;
import com.soat.fiap.food.core.payment.infrastructure.common.source.PaymentDataSource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import unit.fixtures.PaymentFixture;

/**
 * Testes unitários para {@link ProcessExpiredPaymentsControllerTest}.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ProcessExpiredPaymentsController - Testes Unitários")
class ProcessExpiredPaymentsControllerTest {

	@Mock
	private PaymentDataSource paymentDataSource;

	@Mock
	private EventPublisherSource eventPublisherSource;

	@Test
	@DisplayName("Deve processar pagamentos expirados e publicar eventos de expiração com sucesso")
	void shouldProcessExpiredPaymentsAndPublishEventsSuccessfully() {
		// Arrange
		var expiredPayment = PaymentFixture.createExpiredPayment();
		var updatedPayment = PaymentFixture.createCancelledPayment();
		updatedPayment.setQrCode(expiredPayment.getQrCode().value());

		try (var getExpiredMock = mockStatic(GetExpiredPaymentsUseCase.class);
			 var updateStatusMock = mockStatic(UpdatePaymentStatusUseCase.class);
			 var publishEventMock = mockStatic(PublishPaymentExpiredEventUseCase.class)) {

			getExpiredMock.when(() ->
							GetExpiredPaymentsUseCase.getExpiredPayments(any()))
					.thenReturn(List.of(expiredPayment));

			updateStatusMock.when(() ->
							UpdatePaymentStatusUseCase.updatePaymentStatus(expiredPayment, PaymentStatus.CANCELLED))
					.thenReturn(updatedPayment);

			when(paymentDataSource.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

			// Act & Assert
			assertThatNoException().isThrownBy(() ->
					ProcessExpiredPaymentsController.processExpiredPayments(paymentDataSource, eventPublisherSource)
			);

			// Verify
			getExpiredMock.verify(() ->
					GetExpiredPaymentsUseCase.getExpiredPayments(any()));
			updateStatusMock.verify(() ->
					UpdatePaymentStatusUseCase.updatePaymentStatus(expiredPayment, PaymentStatus.CANCELLED));
			publishEventMock.verify(() ->
					PublishPaymentExpiredEventUseCase.publishPaymentExpiredEvent(any(), any()));
		}
	}

	@Test
	@DisplayName("Não deve fazer nada quando não houver pagamentos expirados")
	void shouldDoNothingWhenNoExpiredPaymentsFound() {
		// Arrange
		try (var getExpiredMock = mockStatic(GetExpiredPaymentsUseCase.class);
			 var updateStatusMock = mockStatic(UpdatePaymentStatusUseCase.class);
			 var publishEventMock = mockStatic(PublishPaymentExpiredEventUseCase.class)) {

			getExpiredMock.when(() ->
							GetExpiredPaymentsUseCase.getExpiredPayments(any()))
					.thenReturn(Collections.emptyList());

			// Act
			ProcessExpiredPaymentsController.processExpiredPayments(paymentDataSource, eventPublisherSource);

			// Assert
			getExpiredMock.verify(() ->
					GetExpiredPaymentsUseCase.getExpiredPayments(any()));
			updateStatusMock.verifyNoInteractions();
			publishEventMock.verifyNoInteractions();
			verifyNoInteractions(paymentDataSource);
		}
	}
}
