package unit.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.soat.fiap.food.core.payment.core.domain.vo.QrCode;

@DisplayName("QrCode - Testes de Domínio")
class QrCodeTest {

	@Test
	@DisplayName("Deve criar QrCode válido")
	void shouldCreateValidQrCode() {
		String qrValue = "QR123456789";
		QrCode qrCode = new QrCode(qrValue);

		assertNotNull(qrCode);
		assertEquals(qrValue, qrCode.value());
	}

	@Test
	@DisplayName("Deve lançar exceção se valor for nulo")
	void shouldThrowExceptionIfValueIsNull() {
		NullPointerException exception = assertThrows(NullPointerException.class, () -> {
			new QrCode(null);
		});

		assertEquals("QrCode não pode ser nulo", exception.getMessage());
	}

	@Test
	@DisplayName("Deve lançar exceção se valor exceder 255 caracteres")
	void shouldThrowExceptionIfValueExceeds255Characters() {
		String longValue = "Q".repeat(256);

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new QrCode(longValue);
		});

		assertEquals("O conteúdo do QrCode não pode ultrapassar 255 caracteres", exception.getMessage());
	}

	@Test
	@DisplayName("Deve permitir valor exatamente com 255 caracteres")
	void shouldAllowValueWith255Characters() {
		String maxLengthValue = "Q".repeat(255);

		assertDoesNotThrow(() -> new QrCode(maxLengthValue));

		QrCode qrCode = new QrCode(maxLengthValue);
		assertEquals(maxLengthValue, qrCode.value());
	}
}
