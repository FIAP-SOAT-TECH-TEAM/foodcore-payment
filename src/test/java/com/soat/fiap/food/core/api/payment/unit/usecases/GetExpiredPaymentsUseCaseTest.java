package com.soat.fiap.food.core.api.payment.unit.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.payment.core.application.usecases.GetExpiredPaymentsUseCase;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.gateways.PaymentGateway;
import com.soat.fiap.food.core.api.shared.fixtures.PaymentFixture;

@ExtendWith(MockitoExtension.class) @DisplayName("GetExpiredPaymentsUseCase - Testes Unitários")
class GetExpiredPaymentsUseCaseTest {

	@Mock
	private PaymentGateway paymentGateway;

	@Test @DisplayName("Deve retornar pagamentos expirados")
	void shouldReturnExpiredPayments() {
		// Arrange
		var expiredPayments = List.of(PaymentFixture.createValidPayment(), PaymentFixture.createValidPayment());

		when(paymentGateway.findExpiredPaymentsWithoutApprovedOrCancelled(any(LocalDateTime.class)))
				.thenReturn(expiredPayments);

		// Act
		var result = GetExpiredPaymentsUseCase.getExpiredPayments(paymentGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result).hasSize(2);
		assertThat(result.get(0)).isEqualTo(expiredPayments.get(0));
		assertThat(result.get(1)).isEqualTo(expiredPayments.get(1));

		verify(paymentGateway).findExpiredPaymentsWithoutApprovedOrCancelled(any(LocalDateTime.class));
	}

	@Test
	@DisplayName("Deve retornar lista vazia quando não há pagamentos expirados")
	void shouldReturnEmptyListWhenNoExpiredPayments() {
		// Arrange
		when(paymentGateway.findExpiredPaymentsWithoutApprovedOrCancelled(any(LocalDateTime.class)))
				.thenReturn(List.of());

		// Act
		var result = GetExpiredPaymentsUseCase.getExpiredPayments(paymentGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result).isEmpty();

		verify(paymentGateway).findExpiredPaymentsWithoutApprovedOrCancelled(any(LocalDateTime.class));
	}

	@Test @DisplayName("Deve retornar único pagamento expirado")
	void shouldReturnSingleExpiredPayment() {
		// Arrange
		var payment = PaymentFixture.createValidPayment();
		var expiredPayments = List.of(payment);

		when(paymentGateway.findExpiredPaymentsWithoutApprovedOrCancelled(any(LocalDateTime.class)))
				.thenReturn(expiredPayments);

		// Act
		var result = GetExpiredPaymentsUseCase.getExpiredPayments(paymentGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result).hasSize(1);
		assertThat(result.get(0)).isEqualTo(payment);

		verify(paymentGateway).findExpiredPaymentsWithoutApprovedOrCancelled(any(LocalDateTime.class));
	}

	@Test
	@DisplayName("Deve chamar o gateway apenas uma vez")
	void shouldCallGatewayOnlyOnce() {
		// Arrange
		when(paymentGateway.findExpiredPaymentsWithoutApprovedOrCancelled(any(LocalDateTime.class)))
				.thenReturn(List.of());

		// Act
		GetExpiredPaymentsUseCase.getExpiredPayments(paymentGateway);

		// Assert
		verify(paymentGateway, times(1)).findExpiredPaymentsWithoutApprovedOrCancelled(any(LocalDateTime.class));
	}
}
