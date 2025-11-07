package unit.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.payment.core.application.usecases.GetAllOrderPaymentsUseCase;
import com.soat.fiap.food.core.payment.core.interfaceadapters.gateways.PaymentGateway;
import unit.fixtures.PaymentFixture;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetAllOrderPaymentsUseCase - Testes Unitários")
class GetAllOrderPaymentsUseCaseTest {

	@Mock
	private PaymentGateway paymentGateway;

	@Test
	@DisplayName("Deve retornar lista de pagamentos quando encontrados")
	void shouldReturnPaymentListWhenFound() {
		// Arrange
		var orderId = 10L;
		var payment1 = PaymentFixture.createPaymentForOrder(orderId);
		var payment2 = PaymentFixture.createPaymentForOrder(orderId);
		var expectedPayments = List.of(payment1, payment2);

		when(paymentGateway.findByOrderId(orderId)).thenReturn(expectedPayments);

		// Act
		var result = GetAllOrderPaymentsUseCase.getAllOrderPayments(orderId, paymentGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result).hasSize(2);
		assertThat(result).containsExactlyInAnyOrder(payment1, payment2);
		verify(paymentGateway).findByOrderId(orderId);
	}

	@Test
	@DisplayName("Deve retornar lista vazia quando nenhum pagamento encontrado")
	void shouldReturnEmptyListWhenNoPaymentsFound() {
		// Arrange
		var orderId = 999L;

		when(paymentGateway.findByOrderId(orderId)).thenReturn(List.of());

		// Act
		var result = GetAllOrderPaymentsUseCase.getAllOrderPayments(orderId, paymentGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result).isEmpty();
		verify(paymentGateway).findByOrderId(orderId);
	}

	@Test
	@DisplayName("Deve chamar o gateway com o ID correto do pedido")
	void shouldCallGatewayWithCorrectOrderId() {
		// Arrange
		var orderId = 55L;
		when(paymentGateway.findByOrderId(orderId)).thenReturn(List.of());

		// Act
		GetAllOrderPaymentsUseCase.getAllOrderPayments(orderId, paymentGateway);

		// Assert
		verify(paymentGateway).findByOrderId(orderId);
	}
}
