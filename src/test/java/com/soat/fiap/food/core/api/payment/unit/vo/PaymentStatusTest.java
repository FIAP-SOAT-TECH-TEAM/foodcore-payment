package com.soat.fiap.food.core.api.payment.unit.vo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.soat.fiap.food.core.api.payment.core.domain.vo.PaymentStatus;

@DisplayName("PaymentStatus - Testes Unitários")
class PaymentStatusTest {

	@Test @DisplayName("Deve retornar PaymentStatus para valor válido")
	void shouldReturnPaymentStatusForValidValue() {
		// Act & Assert
		assertEquals(PaymentStatus.PENDING, PaymentStatus.fromValue("PENDING"));
		assertEquals(PaymentStatus.PENDING, PaymentStatus.fromValue("pending"));

		assertEquals(PaymentStatus.APPROVED, PaymentStatus.fromValue("APPROVED"));
		assertEquals(PaymentStatus.APPROVED, PaymentStatus.fromValue("approved"));

		assertEquals(PaymentStatus.REJECTED, PaymentStatus.fromValue("REJECTED"));
		assertEquals(PaymentStatus.REJECTED, PaymentStatus.fromValue("rejected"));

		assertEquals(PaymentStatus.CANCELLED, PaymentStatus.fromValue("CANCELLED"));
		assertEquals(PaymentStatus.CANCELLED, PaymentStatus.fromValue("cancelled"));
	}

	@Test @DisplayName("Deve lançar exceção para valor inválido")
	void shouldThrowExceptionForInvalidValue() {
		// Act & Assert
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> PaymentStatus.fromValue("INVALID_STATUS"));

		assertTrue(exception.getMessage().contains("Status desconhecido"));
		assertTrue(exception.getMessage().contains("INVALID_STATUS"));
	}

	@Test @DisplayName("Deve retornar descrição correta para cada status")
	void shouldReturnCorrectDescriptionForEachStatus() {
		// Act & Assert
		assertEquals("Pendente", PaymentStatus.PENDING.getDescription());
		assertEquals("Aprovado", PaymentStatus.APPROVED.getDescription());
		assertEquals("Rejeitado", PaymentStatus.REJECTED.getDescription());
		assertEquals("Cancelado", PaymentStatus.CANCELLED.getDescription());
	}

	@Test @DisplayName("Deve ser case insensitive")
	void shouldBeCaseInsensitive() {
		// Act & Assert
		assertEquals(PaymentStatus.PENDING, PaymentStatus.fromValue("PeNdInG"));
		assertEquals(PaymentStatus.APPROVED, PaymentStatus.fromValue("ApProVeD"));
		assertEquals(PaymentStatus.REJECTED, PaymentStatus.fromValue("REJECTED"));
		assertEquals(PaymentStatus.CANCELLED, PaymentStatus.fromValue("cancelled"));
	}

	@Test @DisplayName("Deve validar todos os valores do enum")
	void shouldValidateAllEnumValues() {
		// Act & Assert
		assertNotNull(PaymentStatus.PENDING);
		assertNotNull(PaymentStatus.APPROVED);
		assertNotNull(PaymentStatus.REJECTED);
		assertNotNull(PaymentStatus.CANCELLED);

		assertEquals(4, PaymentStatus.values().length);
	}
}
