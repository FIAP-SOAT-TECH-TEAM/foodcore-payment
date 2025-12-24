package unit.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.soat.fiap.food.core.payment.core.domain.vo.PaymentStatus;

@DisplayName("PaymentStatus - Testes de Domínio")
class PaymentStatusTest {

	@Test @DisplayName("Deve retornar descrição correta para cada status de pagamento")
	void shouldReturnCorrectDescription() {
		assertEquals("Pendente", PaymentStatus.PENDING.getDescription());
		assertEquals("Aprovado", PaymentStatus.APPROVED.getDescription());
		assertEquals("Rejeitado", PaymentStatus.REJECTED.getDescription());
		assertEquals("Cancelado", PaymentStatus.CANCELLED.getDescription());
	}

	@Test @DisplayName("Deve converter string válida para PaymentStatus correspondente")
	void shouldConvertValidStringToPaymentStatus() {
		assertEquals(PaymentStatus.PENDING, PaymentStatus.fromValue("pending"));
		assertEquals(PaymentStatus.APPROVED, PaymentStatus.fromValue("APPROVED"));
		assertEquals(PaymentStatus.REJECTED, PaymentStatus.fromValue("rejected"));
		assertEquals(PaymentStatus.CANCELLED, PaymentStatus.fromValue("CANCELLED"));
	}

	@Test @DisplayName("Deve lançar exceção se o status for desconhecido")
	void shouldThrowExceptionForUnknownStatus() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			PaymentStatus.fromValue("EXPIRED");
		});

		assertEquals("Status desconhecido: EXPIRED", exception.getMessage());
	}
}
