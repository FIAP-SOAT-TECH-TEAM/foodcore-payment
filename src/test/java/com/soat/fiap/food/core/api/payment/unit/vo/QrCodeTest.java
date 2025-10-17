package com.soat.fiap.food.core.api.payment.unit.vo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.soat.fiap.food.core.api.payment.core.domain.vo.QrCode;

@DisplayName("QrCode - Testes de Value Object")
class QrCodeTest {

	@Test @DisplayName("Deve criar QrCode com valor válido")
	void shouldCreateQrCodeWithValidValue() {
		// Arrange
		String validQrCodeValue = "00020126580014br.gov.bcb.pix013636c2e6c2-4a9a-4b9e-8b1e-1234567890ab5204000053039865802BR5913MERCHANT NAME6009SAO PAULO62070503***6304A1B2";

		// Act
		var qrCode = new QrCode(validQrCodeValue);

		// Assert
		assertNotNull(qrCode);
		assertEquals(validQrCodeValue, qrCode.value());
	}

	@Test @DisplayName("Deve lançar exceção para QrCode nulo")
	void shouldThrowExceptionForNullQrCode() {
		// Act & Assert
		var exception = assertThrows(NullPointerException.class, () -> new QrCode(null));

		assertTrue(exception.getMessage().contains("QrCode"));
	}

	@Test @DisplayName("Deve lançar exceção para QrCode muito longo")
	void shouldThrowExceptionForTooLongQrCode() {
		// Arrange
		String longQrCode = "a".repeat(256); // Mais de 255 caracteres

		// Act & Assert
		var exception = assertThrows(IllegalArgumentException.class, () -> new QrCode(longQrCode));

		assertTrue(exception.getMessage().contains("255"));
	}

	@Test @DisplayName("Deve aceitar QrCode com 255 caracteres")
	void shouldAcceptQrCodeWith255Characters() {
		// Arrange
		String maxLengthQrCode = "a".repeat(255); // Exatamente 255 caracteres

		// Act & Assert
		assertDoesNotThrow(() -> new QrCode(maxLengthQrCode));

		var qrCode = new QrCode(maxLengthQrCode);
		assertEquals(maxLengthQrCode, qrCode.value());
	}

	@Test @DisplayName("Deve implementar equals corretamente")
	void shouldImplementEqualsCorrectly() {
		// Arrange
		var qrCodeValue = "valid-qr-code-value";
		var qrCode1 = new QrCode(qrCodeValue);
		var qrCode2 = new QrCode(qrCodeValue);
		var qrCode3 = new QrCode("different-value");

		// Act & Assert
		assertEquals(qrCode1, qrCode2);
		assertNotEquals(qrCode1, qrCode3);
		assertNotEquals(qrCode1, null);
		assertNotEquals(qrCode1, "string");
	}

	@Test @DisplayName("Deve implementar hashCode corretamente")
	void shouldImplementHashCodeCorrectly() {
		// Arrange
		var qrCodeValue = "valid-qr-code-value";
		var qrCode1 = new QrCode(qrCodeValue);
		var qrCode2 = new QrCode(qrCodeValue);

		// Act & Assert
		assertEquals(qrCode1.hashCode(), qrCode2.hashCode());
	}

	@Test @DisplayName("Deve implementar toString corretamente")
	void shouldImplementToStringCorrectly() {
		// Arrange
		var qrCodeValue = "valid-qr-code-value";
		var qrCode = new QrCode(qrCodeValue);

		// Act
		var toString = qrCode.toString();

		// Assert
		assertNotNull(toString);
		assertTrue(toString.contains(qrCodeValue));
	}
}
