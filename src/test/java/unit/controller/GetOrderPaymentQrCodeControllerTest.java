package unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.payment.core.application.usecases.GetLatestPaymentByOrderIdUseCase;
import com.soat.fiap.food.core.payment.core.domain.vo.QrCode;
import com.soat.fiap.food.core.payment.core.interfaceadapters.bff.controller.web.api.GetOrderPaymentQrCodeController;
import com.soat.fiap.food.core.payment.core.interfaceadapters.gateways.PaymentGateway;
import com.soat.fiap.food.core.payment.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.shared.core.interfaceadapters.gateways.AccessManagerGateway;
import com.soat.fiap.food.core.shared.infrastructure.common.source.AccessManagerSource;

import unit.fixtures.PaymentFixture;

/**
 * Testes unitários para {@link GetOrderPaymentQrCodeController}.
 */
@ExtendWith(MockitoExtension.class) @DisplayName("GetOrderPaymentQrCodeController - Testes Unitários")
class GetOrderPaymentQrCodeControllerTest {

	@Mock
	private PaymentDataSource paymentDataSource;

	@Mock
	private AccessManagerSource accessManagerSource;

	@Test @DisplayName("Deve retornar QrCodeResponse com sucesso ao obter pagamento mais recente")
	void shouldReturnQrCodeResponseSuccessfully() {
		// Arrange
		var orderId = 1L;
		var payment = PaymentFixture.createPendingPayment();

		try (var useCaseMock = mockStatic(GetLatestPaymentByOrderIdUseCase.class)) {

			useCaseMock.when(() -> GetLatestPaymentByOrderIdUseCase.getLatestPaymentByOrderId(eq(orderId),
					any(PaymentGateway.class), any(AccessManagerGateway.class))).thenReturn(payment);

			// Act
			var result = GetOrderPaymentQrCodeController.getOrderPaymentQrCode(orderId, paymentDataSource,
					accessManagerSource);

			// Assert
			assertThat(result).isNotNull();
			assertThat(result.getOrderId()).isEqualTo(String.valueOf(orderId));
			assertThat(result.getQrCode()).isNotNull();
			assertThat(result.getExpiresIn()).isAfter(LocalDateTime.now());

			useCaseMock.verify(() -> GetLatestPaymentByOrderIdUseCase.getLatestPaymentByOrderId(eq(orderId),
					any(PaymentGateway.class), any(AccessManagerGateway.class)));
		}
	}

	@Test @DisplayName("Deve chamar GetLatestPaymentByOrderIdUseCase com parâmetros corretos")
	void shouldCallUseCaseWithCorrectParameters() {
		// Arrange
		var orderId = 2002L;
		var payment = PaymentFixture.createPendingPayment();

		try (var useCaseMock = mockStatic(GetLatestPaymentByOrderIdUseCase.class)) {

			useCaseMock.when(() -> GetLatestPaymentByOrderIdUseCase.getLatestPaymentByOrderId(eq(orderId),
					any(PaymentGateway.class), any(AccessManagerGateway.class))).thenReturn(payment);

			// Act
			GetOrderPaymentQrCodeController.getOrderPaymentQrCode(orderId, paymentDataSource, accessManagerSource);

			// Assert
			useCaseMock.verify(() -> GetLatestPaymentByOrderIdUseCase.getLatestPaymentByOrderId(eq(orderId),
					any(PaymentGateway.class), any(AccessManagerGateway.class)));
		}
	}

	@Test @DisplayName("Deve retornar null quando UseCase retornar null")
	void shouldReturnNullWhenUseCaseReturnsNull() {
		// Arrange
		var orderId = 3003L;

		try (var useCaseMock = mockStatic(GetLatestPaymentByOrderIdUseCase.class)) {

			useCaseMock.when(() -> GetLatestPaymentByOrderIdUseCase.getLatestPaymentByOrderId(eq(orderId),
					any(PaymentGateway.class), any(AccessManagerGateway.class))).thenReturn(null);

			// Act
			var result = GetOrderPaymentQrCodeController.getOrderPaymentQrCode(orderId, paymentDataSource,
					accessManagerSource);

			// Assert
			assertThat(result).isNull();

			useCaseMock.verify(() -> GetLatestPaymentByOrderIdUseCase.getLatestPaymentByOrderId(eq(orderId),
					any(PaymentGateway.class), any(AccessManagerGateway.class)));
		}
	}

	@Test @DisplayName("Deve preencher corretamente o QrCodeResponse usando o PaymentPresenter real")
	void shouldMapToQrCodeResponseCorrectly() {
		// Arrange
		var orderId = 1L;
		var payment = PaymentFixture.createApprovedPayment();

		try (var useCaseMock = mockStatic(GetLatestPaymentByOrderIdUseCase.class)) {

			useCaseMock.when(() -> GetLatestPaymentByOrderIdUseCase.getLatestPaymentByOrderId(eq(orderId),
					any(PaymentGateway.class), any(AccessManagerGateway.class))).thenReturn(payment);

			// Act
			var result = GetOrderPaymentQrCodeController.getOrderPaymentQrCode(orderId, paymentDataSource,
					accessManagerSource);

			// Assert
			assertThat(result).isNotNull();
			assertThat(result.getOrderId()).isEqualTo(String.valueOf(orderId));
			assertThat(result.getQrCode()).isInstanceOf(QrCode.class);
			assertThat(result.getExpiresIn()).isAfter(LocalDateTime.now());

			useCaseMock.verify(() -> GetLatestPaymentByOrderIdUseCase.getLatestPaymentByOrderId(eq(orderId),
					any(PaymentGateway.class), any(AccessManagerGateway.class)));
		}
	}
}
