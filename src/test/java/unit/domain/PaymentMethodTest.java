package unit.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.soat.fiap.food.core.payment.core.domain.vo.PaymentMethod;

@DisplayName("PaymentMethod - Testes de Domínio")
class PaymentMethodTest {

	@Test
	@DisplayName("Deve retornar descrição correta para cada método de pagamento")
	void shouldReturnCorrectDescription() {
		assertEquals("Cartão de Crédito", PaymentMethod.CREDIT_CARD.getDescription());
		assertEquals("Cartão de Débito", PaymentMethod.DEBIT_CARD.getDescription());
		assertEquals("PIX", PaymentMethod.PIX.getDescription());
		assertEquals("Dinheiro", PaymentMethod.ACCOUNT_MONEY.getDescription());
	}

	@Test
	@DisplayName("Deve converter string válida para PaymentMethod correspondente")
	void shouldConvertValidStringToPaymentMethod() {
		assertEquals(PaymentMethod.CREDIT_CARD, PaymentMethod.fromValue("credit_card"));
		assertEquals(PaymentMethod.DEBIT_CARD, PaymentMethod.fromValue("DEBIT_CARD"));
		assertEquals(PaymentMethod.PIX, PaymentMethod.fromValue("pix"));
		assertEquals(PaymentMethod.ACCOUNT_MONEY, PaymentMethod.fromValue("account-money"));
	}

	@Test
	@DisplayName("Deve converter string com espaços ou hífens para PaymentMethod correspondente")
	void shouldConvertStringWithSpacesOrHyphens() {
		assertEquals(PaymentMethod.CREDIT_CARD, PaymentMethod.fromValue("credit card"));
		assertEquals(PaymentMethod.ACCOUNT_MONEY, PaymentMethod.fromValue("account money"));
	}

	@Test
	@DisplayName("Deve lançar exceção se o método for desconhecido")
	void shouldThrowExceptionForUnknownMethod() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			PaymentMethod.fromValue("boleto");
		});

		assertEquals("PaymentMethod desconhecido: boleto", exception.getMessage());
	}
}
