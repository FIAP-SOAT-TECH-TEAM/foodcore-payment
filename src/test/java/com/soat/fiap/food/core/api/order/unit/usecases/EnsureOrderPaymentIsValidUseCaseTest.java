package com.soat.fiap.food.core.api.order.unit.usecases;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.order.core.application.usecases.EnsureOrderPaymentIsValidUseCase;
import com.soat.fiap.food.core.api.order.core.domain.exceptions.OrderNotFoundException;
import com.soat.fiap.food.core.api.order.core.domain.vo.OrderStatus;
import com.soat.fiap.food.core.api.order.core.interfaceadapters.gateways.OrderGateway;
import com.soat.fiap.food.core.api.payment.core.domain.exceptions.PaymentNotFoundException;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.gateways.PaymentGateway;
import com.soat.fiap.food.core.api.shared.fixtures.OrderFixture;
import com.soat.fiap.food.core.api.shared.fixtures.PaymentFixture;

@ExtendWith(MockitoExtension.class) @DisplayName("EnsureOrderPaymentIsValidUseCase - Testes Unitários")
class EnsureOrderPaymentIsValidUseCaseTest {

	@Mock
	private PaymentGateway paymentGateway;

	@Mock
	private OrderGateway orderGateway;

	@Test @DisplayName("Deve validar com sucesso quando pedido e pagamento existem")
	void shouldValidateSuccessfullyWhenOrderAndPaymentExist() {
		// Arrange
		var orderId = 1L;
		var order = OrderFixture.createValidOrder();
		order.setOrderStatus(OrderStatus.PREPARING);
		var payment = PaymentFixture.createValidPayment();

		when(orderGateway.findById(orderId)).thenReturn(Optional.of(order));
		when(paymentGateway.findTopByOrderIdOrderByIdDesc(orderId)).thenReturn(Optional.of(payment));

		// Act & Assert
		assertThatNoException().isThrownBy(() -> EnsureOrderPaymentIsValidUseCase.ensureOrderPaymentIsValid(orderId,
				paymentGateway, orderGateway));

		verify(orderGateway).findById(orderId);
		verify(paymentGateway).findTopByOrderIdOrderByIdDesc(orderId);
	}

	@Test @DisplayName("Deve lançar exceção quando pedido não for encontrado")
	void shouldThrowExceptionWhenOrderNotFound() {
		// Arrange
		var orderId = 999L;
		when(orderGateway.findById(orderId)).thenReturn(Optional.empty());

		// Act & Assert
		assertThatThrownBy(
				() -> EnsureOrderPaymentIsValidUseCase.ensureOrderPaymentIsValid(orderId, paymentGateway, orderGateway))
				.isInstanceOf(OrderNotFoundException.class)
				.hasMessage("Pedido não encontrado com id: 999");

		verify(orderGateway).findById(orderId);
	}

	@Test @DisplayName("Deve lançar exceção quando pagamento não existir e status não for RECEIVED")
	void shouldThrowExceptionWhenPaymentNotExistsAndStatusNotReceived() {
		// Arrange
		var orderId = 2L;
		var order = OrderFixture.createValidOrder();
		order.setOrderStatus(OrderStatus.PREPARING); // Status diferente de RECEIVED

		when(orderGateway.findById(orderId)).thenReturn(Optional.of(order));
		when(paymentGateway.findTopByOrderIdOrderByIdDesc(orderId)).thenReturn(Optional.empty());

		// Act & Assert
		assertThatThrownBy(
				() -> EnsureOrderPaymentIsValidUseCase.ensureOrderPaymentIsValid(orderId, paymentGateway, orderGateway))
				.isInstanceOf(PaymentNotFoundException.class)
				.hasMessage("O pagamento do pedido não existe");

		verify(orderGateway).findById(orderId);
		verify(paymentGateway).findTopByOrderIdOrderByIdDesc(orderId);
	}

	@Test @DisplayName("Deve validar com sucesso quando pagamento não existir mas status for RECEIVED")
	void shouldValidateSuccessfullyWhenPaymentNotExistsButStatusIsReceived() {
		// Arrange
		var orderId = 3L;
		var order = OrderFixture.createValidOrder();
		order.setOrderStatus(OrderStatus.RECEIVED); // Status RECEIVED permite ausência de pagamento

		when(orderGateway.findById(orderId)).thenReturn(Optional.of(order));
		when(paymentGateway.findTopByOrderIdOrderByIdDesc(orderId)).thenReturn(Optional.empty());

		// Act & Assert
		assertThatNoException().isThrownBy(() -> EnsureOrderPaymentIsValidUseCase.ensureOrderPaymentIsValid(orderId,
				paymentGateway, orderGateway));

		verify(orderGateway).findById(orderId);
		verify(paymentGateway).findTopByOrderIdOrderByIdDesc(orderId);
	}

	@Test @DisplayName("Deve validar com sucesso para diferentes status quando pagamento existe")
	void shouldValidateSuccessfullyForDifferentStatusWhenPaymentExists() {
		// Arrange
		var orderId = 4L;
		var order = OrderFixture.createValidOrder();
		order.setOrderStatus(OrderStatus.READY); // Status READY
		var payment = PaymentFixture.createApprovedPayment();

		when(orderGateway.findById(orderId)).thenReturn(Optional.of(order));
		when(paymentGateway.findTopByOrderIdOrderByIdDesc(orderId)).thenReturn(Optional.of(payment));

		// Act & Assert
		assertThatNoException().isThrownBy(() -> EnsureOrderPaymentIsValidUseCase.ensureOrderPaymentIsValid(orderId,
				paymentGateway, orderGateway));

		verify(orderGateway).findById(orderId);
		verify(paymentGateway).findTopByOrderIdOrderByIdDesc(orderId);
	}

	@Test @DisplayName("Deve lançar exceção quando pagamento não existir e status for COMPLETED")
	void shouldThrowExceptionWhenPaymentNotExistsAndStatusIsCompleted() {
		// Arrange
		var orderId = 5L;
		var order = OrderFixture.createValidOrder();
		order.setOrderStatus(OrderStatus.COMPLETED); // Status COMPLETED requer pagamento

		when(orderGateway.findById(orderId)).thenReturn(Optional.of(order));
		when(paymentGateway.findTopByOrderIdOrderByIdDesc(orderId)).thenReturn(Optional.empty());

		// Act & Assert
		assertThatThrownBy(
				() -> EnsureOrderPaymentIsValidUseCase.ensureOrderPaymentIsValid(orderId, paymentGateway, orderGateway))
				.isInstanceOf(PaymentNotFoundException.class)
				.hasMessage("O pagamento do pedido não existe");

		verify(orderGateway).findById(orderId);
		verify(paymentGateway).findTopByOrderIdOrderByIdDesc(orderId);
	}
}
