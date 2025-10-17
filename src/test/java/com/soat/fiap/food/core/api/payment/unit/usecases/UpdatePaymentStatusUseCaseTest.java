package com.soat.fiap.food.core.api.payment.unit.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.payment.core.application.usecases.UpdatePaymentStatusUseCase;
import com.soat.fiap.food.core.api.payment.core.domain.model.Payment;
import com.soat.fiap.food.core.api.payment.core.domain.vo.PaymentStatus;

@ExtendWith(MockitoExtension.class) @DisplayName("UpdatePaymentStatusUseCase - Testes Unitários")
class UpdatePaymentStatusUseCaseTest {

	@Test @DisplayName("Deve atualizar status do pagamento para APPROVED")
	void shouldUpdatePaymentStatusToApproved() {
		// Arrange
		var payment = new Payment("as23a13", 1L, new BigDecimal("100.00"));
		payment.setStatus(PaymentStatus.PENDING);

		// Act
		var result = UpdatePaymentStatusUseCase.updatePaymentStatus(payment, PaymentStatus.APPROVED);

		// Assert
		assertNotNull(result);
		assertEquals(PaymentStatus.APPROVED, result.getStatus());
		assertEquals(payment.getId(), result.getId());
		assertEquals(payment.getOrderId(), result.getOrderId());
		assertEquals(payment.getAmount(), result.getAmount());
	}

	@Test @DisplayName("Deve atualizar status do pagamento para CANCELLED")
	void shouldUpdatePaymentStatusToCancelled() {
		// Arrange
		var payment = new Payment("as5t3as3", 2L, new BigDecimal("50.00"));
		payment.setStatus(PaymentStatus.PENDING);

		// Act
		var result = UpdatePaymentStatusUseCase.updatePaymentStatus(payment, PaymentStatus.CANCELLED);

		// Assert
		assertNotNull(result);
		assertEquals(PaymentStatus.CANCELLED, result.getStatus());
		assertEquals(payment.getUserId(), result.getUserId());
		assertEquals(payment.getOrderId(), result.getOrderId());
	}

	@Test @DisplayName("Deve atualizar status do pagamento para REJECTED")
	void shouldUpdatePaymentStatusToRejected() {
		// Arrange
		var payment = new Payment("1s23as3", 3L, new BigDecimal("75.00"));
		payment.setStatus(PaymentStatus.PENDING);

		// Act
		var result = UpdatePaymentStatusUseCase.updatePaymentStatus(payment, PaymentStatus.REJECTED);

		// Assert
		assertNotNull(result);
		assertEquals(PaymentStatus.REJECTED, result.getStatus());
		assertEquals(new BigDecimal("75.00"), result.getAmount());
	}

	@Test @DisplayName("Deve manter outras propriedades do pagamento inalteradas")
	void shouldKeepOtherPaymentPropertiesUnchanged() {
		// Arrange
		var payment = new Payment("as13as3", 4L, new BigDecimal("200.00"));
		payment.setId(100L);
		payment.setStatus(PaymentStatus.PENDING);
		var originalUserId = payment.getUserId();
		var originalOrderId = payment.getOrderId();
		var originalAmount = payment.getAmount();
		var originalId = payment.getId();

		// Act
		var result = UpdatePaymentStatusUseCase.updatePaymentStatus(payment, PaymentStatus.APPROVED);

		// Assert
		assertNotNull(result);
		assertEquals(PaymentStatus.APPROVED, result.getStatus());
		assertEquals(originalId, result.getId());
		assertEquals(originalUserId, result.getUserId());
		assertEquals(originalOrderId, result.getOrderId());
		assertEquals(originalAmount, result.getAmount());
	}
}
