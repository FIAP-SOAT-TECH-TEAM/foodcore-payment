package com.soat.fiap.food.core.api.payment.unit.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.payment.core.application.usecases.ProcessPaymentNotificationUseCase;
import com.soat.fiap.food.core.api.payment.core.domain.exceptions.PaymentNotFoundException;
import com.soat.fiap.food.core.api.payment.core.domain.vo.PaymentStatus;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.gateways.AcquirerGateway;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.gateways.PaymentGateway;
import com.soat.fiap.food.core.api.shared.fixtures.PaymentFixture;

@ExtendWith(MockitoExtension.class) @DisplayName("ProcessPaymentNotificationUseCase - Testes Unitários")
class ProcessPaymentNotificationUseCaseTest {

	@Mock
	private AcquirerGateway acquirerGateway;

	@Mock
	private PaymentGateway paymentGateway;

	@Test @DisplayName("Deve processar notificação de pagamento aprovado com sucesso")
	void shouldProcessApprovedPaymentNotificationSuccessfully() {
		// Arrange
		var notificationInput = PaymentFixture.createValidAcquirerNotificationInput();
		var acquirerOutput = PaymentFixture.createValidAcquirerPaymentOutput();
		var existingPayment = PaymentFixture.createPendingPayment();

		when(acquirerGateway.getAcquirerPayments(notificationInput.dataId())).thenReturn(acquirerOutput);
		when(paymentGateway.findTopByOrderIdOrderByIdDesc(acquirerOutput.externalReference()))
				.thenReturn(Optional.of(existingPayment));

		// Act
		var result = ProcessPaymentNotificationUseCase.processPaymentNotification(notificationInput, acquirerGateway,
				paymentGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getStatus()).isEqualTo(PaymentStatus.APPROVED);
		assertThat(result.getTid()).isEqualTo(notificationInput.dataId());
		assertThat(result.getType()).isEqualTo(acquirerOutput.type());
	}

	@Test @DisplayName("Deve retornar pagamento existente quando já aprovado")
	void shouldReturnExistingPaymentWhenAlreadyApproved() {
		// Arrange
		var notificationInput = PaymentFixture.createValidAcquirerNotificationInput();
		var acquirerOutput = PaymentFixture.createValidAcquirerPaymentOutput();
		var approvedPayment = PaymentFixture.createApprovedPayment();

		when(acquirerGateway.getAcquirerPayments(notificationInput.dataId())).thenReturn(acquirerOutput);
		when(paymentGateway.findTopByOrderIdOrderByIdDesc(acquirerOutput.externalReference()))
				.thenReturn(Optional.of(approvedPayment));

		// Act
		var result = ProcessPaymentNotificationUseCase.processPaymentNotification(notificationInput, acquirerGateway,
				paymentGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getStatus()).isEqualTo(PaymentStatus.APPROVED);
		assertThat(result.getId()).isEqualTo(approvedPayment.getId());
	}

	@Test @DisplayName("Deve lançar exceção quando pagamento não for encontrado")
	void shouldThrowExceptionWhenPaymentNotFound() {
		// Arrange
		var notificationInput = PaymentFixture.createValidAcquirerNotificationInput();
		var acquirerOutput = PaymentFixture.createValidAcquirerPaymentOutput();

		when(acquirerGateway.getAcquirerPayments(notificationInput.dataId())).thenReturn(acquirerOutput);
		when(paymentGateway.findTopByOrderIdOrderByIdDesc(acquirerOutput.externalReference()))
				.thenReturn(Optional.empty());

		// Act & Assert
		assertThatThrownBy(() -> ProcessPaymentNotificationUseCase.processPaymentNotification(notificationInput,
				acquirerGateway, paymentGateway)).isInstanceOf(PaymentNotFoundException.class)
				.hasMessage("Pagamento não encontrado com id: 1");
	}

	@Test @DisplayName("Deve resetar ID do pagamento para segunda tentativa quando status não é PENDING")
	void shouldResetPaymentIdForSecondAttemptWhenStatusIsNotPending() {
		// Arrange
		var notificationInput = PaymentFixture.createValidAcquirerNotificationInput();
		var acquirerOutput = PaymentFixture.createValidAcquirerPaymentOutput();
		var rejectedPayment = PaymentFixture.createRejectedPayment();

		when(acquirerGateway.getAcquirerPayments(notificationInput.dataId())).thenReturn(acquirerOutput);
		when(paymentGateway.findTopByOrderIdOrderByIdDesc(acquirerOutput.externalReference()))
				.thenReturn(Optional.of(rejectedPayment));

		// Act
		var result = ProcessPaymentNotificationUseCase.processPaymentNotification(notificationInput, acquirerGateway,
				paymentGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isNull(); // ID resetado para nova tentativa
		assertThat(result.getStatus()).isEqualTo(PaymentStatus.APPROVED);
		assertThat(result.getTid()).isEqualTo(notificationInput.dataId());
	}

	@Test @DisplayName("Deve processar notificação de pagamento cancelado")
	void shouldProcessCancelledPaymentNotification() {
		// Arrange
		var notificationInput = PaymentFixture.createValidAcquirerNotificationInput();
		var acquirerOutput = PaymentFixture.createCancelledAcquirerPaymentOutput();
		var existingPayment = PaymentFixture.createPendingPayment();

		when(acquirerGateway.getAcquirerPayments(notificationInput.dataId())).thenReturn(acquirerOutput);
		when(paymentGateway.findTopByOrderIdOrderByIdDesc(acquirerOutput.externalReference()))
				.thenReturn(Optional.of(existingPayment));

		// Act
		var result = ProcessPaymentNotificationUseCase.processPaymentNotification(notificationInput, acquirerGateway,
				paymentGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getStatus()).isEqualTo(PaymentStatus.CANCELLED);
		assertThat(result.getTid()).isEqualTo(notificationInput.dataId());
		assertThat(result.getType()).isEqualTo(acquirerOutput.type());
	}
}
