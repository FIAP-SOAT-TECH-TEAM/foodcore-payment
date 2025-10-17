package com.soat.fiap.food.core.api.payment.unit.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.soat.fiap.food.core.api.payment.core.domain.exceptions.PaymentException;
import com.soat.fiap.food.core.api.payment.core.domain.model.Payment;
import com.soat.fiap.food.core.api.payment.core.domain.vo.PaymentMethod;
import com.soat.fiap.food.core.api.payment.core.domain.vo.PaymentStatus;
import com.soat.fiap.food.core.api.shared.fixtures.PaymentFixture;

@DisplayName("Payment - Testes de Domínio")
class PaymentTest {

	@Test @DisplayName("Deve criar pagamento válido")
	void shouldCreateValidPayment() {
		Payment payment = PaymentFixture.createValidPayment();

		assertNotNull(payment);
		assertEquals("as23as3", payment.getUserId());
		assertEquals(1L, payment.getOrderId());
		assertEquals(new BigDecimal("50.00"), payment.getAmount());
		assertEquals(PaymentStatus.PENDING, payment.getStatus());
		assertNotNull(payment.getExpiresIn());
		assertNotNull(payment.getAuditInfo());
		assertEquals("Pagamento via Mercado Pago", payment.getObservations());
	}

	@Test @DisplayName("Deve criar pagamento pendente")
	void shouldCreatePendingPayment() {
		Payment payment = PaymentFixture.createPendingPayment();

		assertEquals(PaymentStatus.PENDING, payment.getStatus());
		assertNotNull(payment.getQrCode());
		assertEquals(1L, payment.getId());
	}

	@Test @DisplayName("Deve criar pagamento aprovado")
	void shouldCreateApprovedPayment() {
		Payment payment = PaymentFixture.createApprovedPayment();

		assertEquals(PaymentStatus.APPROVED, payment.getStatus());
		assertEquals(PaymentMethod.PIX, payment.getType());
		assertEquals("12345678", payment.getTid());
		assertNotNull(payment.getQrCode());
		assertEquals(2L, payment.getId());
	}

	@Test @DisplayName("Deve criar pagamento cancelado")
	void shouldCreateCancelledPayment() {
		Payment payment = PaymentFixture.createCancelledPayment();

		assertEquals(PaymentStatus.CANCELLED, payment.getStatus());
		assertEquals(PaymentMethod.PIX, payment.getType());
		assertEquals("87654321", payment.getTid());
		assertEquals(3L, payment.getId());
	}

	@Test @DisplayName("Deve criar pagamento rejeitado")
	void shouldCreateRejectedPayment() {
		Payment payment = PaymentFixture.createRejectedPayment();

		assertEquals(PaymentStatus.REJECTED, payment.getStatus());
		assertEquals(PaymentMethod.PIX, payment.getType());
		assertEquals("11223344", payment.getTid());
		assertEquals(4L, payment.getId());
	}

	@Test @DisplayName("Deve criar pagamento expirado")
	void shouldCreateExpiredPayment() {
		Payment payment = PaymentFixture.createExpiredPayment();

		assertTrue(payment.getExpiresIn().isBefore(payment.getAuditInfo().getCreatedAt().plusMinutes(1)));
		assertEquals(PaymentStatus.PENDING, payment.getStatus());
		assertEquals(5L, payment.getId());
	}

	@Test @DisplayName("Deve lançar exceção se userId estiver vazio")
	void shouldThrowExceptionIfUserIdIsEmpty() {
		PaymentException exception = assertThrows(PaymentException.class, () -> {
			PaymentFixture.createPaymentForUser("");
		});
		assertEquals("O id do usuário não pode estar vazio", exception.getMessage());
	}

	@Test @DisplayName("Deve lançar exceção se amount for nulo")
	void shouldThrowExceptionIfAmountIsNull() {
		NullPointerException exception = assertThrows(NullPointerException.class, () -> {
			new Payment("as23as3", 1L, null);
		});
		assertEquals("O valor total não pode ser nulo", exception.getMessage());
	}

	@Test @DisplayName("Deve atualizar status e definir paidAt ao aprovar pagamento")
	void shouldUpdateStatusAndSetPaidAtWhenApproved() {
		Payment payment = PaymentFixture.createPendingPayment();
		assertNull(payment.getPaidAt());

		payment.setStatus(PaymentStatus.APPROVED);

		assertEquals(PaymentStatus.APPROVED, payment.getStatus());
		assertNotNull(payment.getPaidAt());
		assertNotNull(payment.getAuditInfo().getUpdatedAt());
	}

	@Test @DisplayName("Deve definir QR Code do pagamento")
	void shouldSetQrCode() {
		Payment payment = PaymentFixture.createValidPayment();
		String qrCode = "QR123456";
		payment.setQrCode(qrCode);

		assertNotNull(payment.getQrCode());
		assertEquals(qrCode, payment.getQrCode().value());
	}

	@Test @DisplayName("Deve lançar exceção se TID exceder 255 caracteres")
	void shouldThrowExceptionForLongTid() {
		Payment payment = PaymentFixture.createValidPayment();
		String longTid = "T".repeat(256);

		PaymentException exception = assertThrows(PaymentException.class, () -> {
			payment.setTid(longTid);
		});

		assertEquals("O TID não pode ter mais de 255 caracteres", exception.getMessage());
	}

	@Test @DisplayName("Deve aceitar TID válido")
	void shouldAcceptValidTid() {
		Payment payment = PaymentFixture.createValidPayment();
		String tid = "TX123456";

		assertDoesNotThrow(() -> payment.setTid(tid));
		assertEquals(tid, payment.getTid());
	}

	@Test @DisplayName("Deve retornar nome do método de pagamento")
	void shouldReturnPaymentTypeName() {
		Payment payment = PaymentFixture.createApprovedPayment();

		assertEquals("PIX", payment.getTypeName());
	}
}
