package com.soat.fiap.food.core.api.payment.unit.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.payment.core.application.usecases.GetLatestPaymentByOrderIdUseCase;
import com.soat.fiap.food.core.api.payment.core.domain.exceptions.PaymentNotFoundException;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.gateways.PaymentGateway;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.AccessManagerGateway;
import com.soat.fiap.food.core.api.shared.fixtures.PaymentFixture;

@ExtendWith(MockitoExtension.class) @DisplayName("GetLatestPaymentByOrderIdUseCase - Testes Unitários")
class GetLatestPaymentByOrderIdUseCaseTest {

	@Mock
	private PaymentGateway paymentGateway;

	@Mock
	private AccessManagerGateway accessManagerGateway;

	@Test @DisplayName("Deve retornar último pagamento quando existe")
	void shouldReturnLatestPaymentWhenExists() {
		// Arrange
		Long orderId = 1L;
		var payment = PaymentFixture.createValidPayment();
		when(paymentGateway.findTopByOrderIdOrderByIdDesc(orderId)).thenReturn(Optional.of(payment));

		// Act
		var result = assertDoesNotThrow(() -> GetLatestPaymentByOrderIdUseCase.getLatestPaymentByOrderId(orderId,
				paymentGateway, accessManagerGateway));

		// Assert
		assertThat(result).isNotNull();
		assertThat(result).isEqualTo(payment);
		verify(paymentGateway).findTopByOrderIdOrderByIdDesc(orderId);
		verify(accessManagerGateway).validateAccess(payment.getUserId());
	}

	@Test @DisplayName("Deve lançar exceção quando não existe pagamento")
	void shouldThrowExceptionWhenPaymentDoesNotExist() {
		// Arrange
		Long orderId = 999L;
		when(paymentGateway.findTopByOrderIdOrderByIdDesc(orderId)).thenReturn(Optional.empty());

		// Act & Assert
		assertThatThrownBy(() -> GetLatestPaymentByOrderIdUseCase.getLatestPaymentByOrderId(orderId, paymentGateway,
				accessManagerGateway)).isInstanceOf(PaymentNotFoundException.class);

		verify(paymentGateway).findTopByOrderIdOrderByIdDesc(orderId);
		verify(accessManagerGateway, never()).validateAccess(any());
	}

	@Test @DisplayName("Deve validar acesso do usuário")
	void shouldValidateUserAccess() {
		// Arrange
		Long orderId = 42L;
		var payment = PaymentFixture.createApprovedPayment();
		when(paymentGateway.findTopByOrderIdOrderByIdDesc(orderId)).thenReturn(Optional.of(payment));

		// Act
		assertDoesNotThrow(() -> GetLatestPaymentByOrderIdUseCase.getLatestPaymentByOrderId(orderId, paymentGateway,
				accessManagerGateway));

		// Assert
		verify(paymentGateway).findTopByOrderIdOrderByIdDesc(orderId);
		verify(accessManagerGateway).validateAccess(payment.getUserId());
	}

	@Test @DisplayName("Deve processar pagamento expirado corretamente")
	void shouldProcessExpiredPaymentCorrectly() {
		// Arrange
		Long orderId = 3L;
		var payment = PaymentFixture.createExpiredPayment();
		when(paymentGateway.findTopByOrderIdOrderByIdDesc(orderId)).thenReturn(Optional.of(payment));

		// Act
		var result = GetLatestPaymentByOrderIdUseCase.getLatestPaymentByOrderId(orderId, paymentGateway,
				accessManagerGateway);

		// Assert
		assertThat(result).isEqualTo(payment);
		assertThat(result.getAmount()).isEqualTo(payment.getAmount());
	}
}
