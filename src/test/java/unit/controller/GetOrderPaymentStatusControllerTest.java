package unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.soat.fiap.food.core.payment.core.application.usecases.GetLatestPaymentByOrderIdUseCase;
import com.soat.fiap.food.core.payment.core.interfaceadapters.bff.controller.web.api.GetOrderPaymentStatusController;
import com.soat.fiap.food.core.payment.core.interfaceadapters.gateways.PaymentGateway;
import com.soat.fiap.food.core.payment.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.shared.core.interfaceadapters.gateways.AccessManagerGateway;
import com.soat.fiap.food.core.shared.infrastructure.common.source.AccessManagerSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import unit.fixtures.PaymentFixture;

/**
 * Testes unitários para {@link GetOrderPaymentStatusController}.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("GetOrderPaymentStatusController - Testes Unitários")
class GetOrderPaymentStatusControllerTest {

	@Mock
	private PaymentDataSource paymentDataSource;

	@Mock
	private AccessManagerSource accessManagerSource;

	@Test
	@DisplayName("Deve retornar PaymentStatusResponse com sucesso ao obter pagamento mais recente")
	void shouldReturnPaymentStatusResponseSuccessfully() {
		// Arrange
		var orderId = 1L;
		var payment = PaymentFixture.createApprovedPayment();

		try (var useCaseMock = mockStatic(GetLatestPaymentByOrderIdUseCase.class)) {

			useCaseMock.when(() -> GetLatestPaymentByOrderIdUseCase
							.getLatestPaymentByOrderId(eq(orderId), any(PaymentGateway.class), any(AccessManagerGateway.class)))
					.thenReturn(payment);

			// Act
			var result = GetOrderPaymentStatusController
					.getOrderPaymentStatus(orderId, paymentDataSource, accessManagerSource);

			// Assert
			assertThat(result).isNotNull();
			assertThat(result.getOrderId()).isEqualTo(orderId);
			assertThat(result.getStatus()).isEqualTo(payment.getStatus());

			useCaseMock.verify(() -> GetLatestPaymentByOrderIdUseCase
					.getLatestPaymentByOrderId(eq(orderId), any(PaymentGateway.class), any(AccessManagerGateway.class)));
		}
	}

	@Test
	@DisplayName("Deve chamar GetLatestPaymentByOrderIdUseCase com parâmetros corretos")
	void shouldCallUseCaseWithCorrectParameters() {
		// Arrange
		var orderId = 20L;
		var payment = PaymentFixture.createPendingPayment();

		try (var useCaseMock = mockStatic(GetLatestPaymentByOrderIdUseCase.class)) {

			useCaseMock.when(() -> GetLatestPaymentByOrderIdUseCase
							.getLatestPaymentByOrderId(eq(orderId), any(PaymentGateway.class), any(AccessManagerGateway.class)))
					.thenReturn(payment);

			// Act
			GetOrderPaymentStatusController
					.getOrderPaymentStatus(orderId, paymentDataSource, accessManagerSource);

			// Assert
			useCaseMock.verify(() -> GetLatestPaymentByOrderIdUseCase
					.getLatestPaymentByOrderId(eq(orderId), any(PaymentGateway.class), any(AccessManagerGateway.class)));
		}
	}

	@Test
	@DisplayName("Deve retornar null quando UseCase retornar null")
	void shouldReturnNullWhenUseCaseReturnsNull() {
		// Arrange
		var orderId = 30L;

		try (var useCaseMock = mockStatic(GetLatestPaymentByOrderIdUseCase.class)) {

			useCaseMock.when(() -> GetLatestPaymentByOrderIdUseCase
							.getLatestPaymentByOrderId(eq(orderId), any(PaymentGateway.class), any(AccessManagerGateway.class)))
					.thenReturn(null);

			// Act
			var result = GetOrderPaymentStatusController
					.getOrderPaymentStatus(orderId, paymentDataSource, accessManagerSource);

			// Assert
			assertThat(result).isNull();

			useCaseMock.verify(() -> GetLatestPaymentByOrderIdUseCase
					.getLatestPaymentByOrderId(eq(orderId), any(PaymentGateway.class), any(AccessManagerGateway.class)));
		}
	}

	@Test
	@DisplayName("Deve mapear corretamente o Payment para PaymentStatusResponse usando PaymentPresenter real")
	void shouldMapToPaymentStatusResponseCorrectly() {
		// Arrange
		var orderId = 2L;
		var payment = PaymentFixture.createCancelledPayment();

		try (var useCaseMock = mockStatic(GetLatestPaymentByOrderIdUseCase.class)) {

			useCaseMock.when(() -> GetLatestPaymentByOrderIdUseCase
							.getLatestPaymentByOrderId(eq(orderId), any(PaymentGateway.class), any(AccessManagerGateway.class)))
					.thenReturn(payment);

			// Act
			var result = GetOrderPaymentStatusController
					.getOrderPaymentStatus(orderId, paymentDataSource, accessManagerSource);

			// Assert
			assertThat(result).isNotNull();
			assertThat(result.getOrderId()).isEqualTo(orderId);
			assertThat(result.getStatus()).isEqualTo(payment.getStatus());

			useCaseMock.verify(() -> GetLatestPaymentByOrderIdUseCase
					.getLatestPaymentByOrderId(eq(orderId), any(PaymentGateway.class), any(AccessManagerGateway.class)));
		}
	}
}
