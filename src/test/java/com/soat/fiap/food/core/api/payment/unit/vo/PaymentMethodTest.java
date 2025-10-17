package com.soat.fiap.food.core.api.payment.unit.vo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.soat.fiap.food.core.api.payment.core.domain.vo.PaymentMethod;

@DisplayName("PaymentMethod - Testes Unitários")
class PaymentMethodTest {

	@Test @DisplayName("Deve retornar PaymentMethod para valor válido")
	void shouldReturnPaymentMethodForValidValue() {
		// Act & Assert
		assertEquals(PaymentMethod.PIX, PaymentMethod.fromValue("PIX"));
		assertEquals(PaymentMethod.PIX, PaymentMethod.fromValue("pix"));
		assertEquals(PaymentMethod.PIX, PaymentMethod.fromValue(" PIX "));

		assertEquals(PaymentMethod.CREDIT_CARD, PaymentMethod.fromValue("CREDIT_CARD"));
		assertEquals(PaymentMethod.CREDIT_CARD, PaymentMethod.fromValue("credit-card"));
		assertEquals(PaymentMethod.CREDIT_CARD, PaymentMethod.fromValue("credit card"));

		assertEquals(PaymentMethod.DEBIT_CARD, PaymentMethod.fromValue("DEBIT_CARD"));
		assertEquals(PaymentMethod.DEBIT_CARD, PaymentMethod.fromValue("debit-card"));

		assertEquals(PaymentMethod.ACCOUNT_MONEY, PaymentMethod.fromValue("ACCOUNT_MONEY"));
		assertEquals(PaymentMethod.ACCOUNT_MONEY, PaymentMethod.fromValue("account money"));
	}

	@Test @DisplayName("Deve lançar exceção para valor inválido")
	void shouldThrowExceptionForInvalidValue() {
		// Act & Assert
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> PaymentMethod.fromValue("INVALID_METHOD"));

		assertTrue(exception.getMessage().contains("PaymentMethod desconhecido"));
		assertTrue(exception.getMessage().contains("INVALID_METHOD"));
	}

	@Test @DisplayName("Deve retornar descrição correta para cada método")
	void shouldReturnCorrectDescriptionForEachMethod() {
		// Act & Assert
		assertEquals("PIX", PaymentMethod.PIX.getDescription());
		assertEquals("Cartão de Crédito", PaymentMethod.CREDIT_CARD.getDescription());
		assertEquals("Cartão de Débito", PaymentMethod.DEBIT_CARD.getDescription());
		assertEquals("Dinheiro", PaymentMethod.ACCOUNT_MONEY.getDescription());
	}

	@Test @DisplayName("Deve normalizar valores com espaços e hífen")
	void shouldNormalizeValuesWithSpacesAndHyphens() {
		// Act & Assert
		assertEquals(PaymentMethod.CREDIT_CARD, PaymentMethod.fromValue("  credit-card  "));
		assertEquals(PaymentMethod.DEBIT_CARD, PaymentMethod.fromValue("debit card"));
		assertEquals(PaymentMethod.ACCOUNT_MONEY, PaymentMethod.fromValue("ACCOUNT-MONEY"));
	}

	@Test @DisplayName("Deve ser case insensitive")
	void shouldBeCaseInsensitive() {
		// Act & Assert
		assertEquals(PaymentMethod.PIX, PaymentMethod.fromValue("pIx"));
		assertEquals(PaymentMethod.CREDIT_CARD, PaymentMethod.fromValue("Credit_Card"));
		assertEquals(PaymentMethod.DEBIT_CARD, PaymentMethod.fromValue("DEBIT_card"));
	}
}
