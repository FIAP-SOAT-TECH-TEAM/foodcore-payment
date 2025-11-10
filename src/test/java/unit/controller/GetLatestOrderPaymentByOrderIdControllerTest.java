package unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.payment.core.application.usecases.GetLatestPaymentByOrderIdUseCase;
import com.soat.fiap.food.core.payment.core.interfaceadapters.bff.controller.web.api.GetLatestOrderPaymentByOrderIdController;
import com.soat.fiap.food.core.payment.core.interfaceadapters.bff.presenter.web.api.PaymentPresenter;
import com.soat.fiap.food.core.payment.core.interfaceadapters.gateways.PaymentGateway;
import com.soat.fiap.food.core.payment.infrastructure.common.source.PaymentDataSource;

import unit.fixtures.PaymentFixture;

/**
 * Testes unitários para {@link GetLatestOrderPaymentByOrderIdController}.
 */
@ExtendWith(MockitoExtension.class) @DisplayName("GetLatestOrderPaymentByOrderIdController - Testes Unitários")
class GetLatestOrderPaymentByOrderIdControllerTest {

	@Mock
	private PaymentDataSource paymentDataSource;

	@Test @DisplayName("Deve retornar PaymentResponse com sucesso ao obter pagamento mais recente")
	void shouldReturnPaymentResponseSuccessfully() {
		// Arrange
		var orderId = 123L;
		var payment = PaymentFixture.createApprovedPayment();
		var paymentResponse = PaymentPresenter.toPaymentResponse(payment);

		try (var useCaseMock = mockStatic(GetLatestPaymentByOrderIdUseCase.class)) {

			useCaseMock.when(() -> GetLatestPaymentByOrderIdUseCase.getLatestPaymentByOrderId(eq(orderId),
					any(PaymentGateway.class), isNull())).thenReturn(payment);

			// Act
			var result = GetLatestOrderPaymentByOrderIdController.getLatestOrderPaymentByOrderId(orderId,
					paymentDataSource);

			// Assert
			assertThat(result).isNotNull();
			assertThat(result).isEqualTo(paymentResponse);

			useCaseMock.verify(() -> GetLatestPaymentByOrderIdUseCase.getLatestPaymentByOrderId(eq(orderId),
					any(PaymentGateway.class), isNull()));
		}
	}

	@Test @DisplayName("Deve chamar GetLatestPaymentByOrderIdUseCase com orderId correto")
	void shouldCallUseCaseWithCorrectOrderId() {
		// Arrange
		var orderId = 456L;
		var payment = PaymentFixture.createPendingPayment();

		try (var useCaseMock = mockStatic(GetLatestPaymentByOrderIdUseCase.class)) {

			useCaseMock.when(() -> GetLatestPaymentByOrderIdUseCase.getLatestPaymentByOrderId(eq(orderId),
					any(PaymentGateway.class), isNull())).thenReturn(payment);

			// Act
			GetLatestOrderPaymentByOrderIdController.getLatestOrderPaymentByOrderId(orderId, paymentDataSource);

			// Assert
			useCaseMock.verify(() -> GetLatestPaymentByOrderIdUseCase.getLatestPaymentByOrderId(eq(orderId),
					any(PaymentGateway.class), isNull()));
		}
	}

	@Test @DisplayName("Deve retornar null quando PaymentPresenter retornar null")
	void shouldReturnNullWhenPresenterReturnsNull() {
		// Arrange
		var orderId = 789L;
		var payment = PaymentFixture.createRejectedPayment();

		try (var useCaseMock = mockStatic(GetLatestPaymentByOrderIdUseCase.class);
				var presenterMock = mockStatic(PaymentPresenter.class)) {

			useCaseMock.when(() -> GetLatestPaymentByOrderIdUseCase.getLatestPaymentByOrderId(eq(orderId),
					any(PaymentGateway.class), isNull())).thenReturn(payment);

			presenterMock.when(() -> PaymentPresenter.toPaymentResponse(payment)).thenReturn(null);

			// Act
			var result = GetLatestOrderPaymentByOrderIdController.getLatestOrderPaymentByOrderId(orderId,
					paymentDataSource);

			// Assert
			assertThat(result).isNull();
		}
	}
}
