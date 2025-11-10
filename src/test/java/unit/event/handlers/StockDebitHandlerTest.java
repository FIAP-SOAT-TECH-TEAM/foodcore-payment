package unit.event.handlers;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.payment.core.interfaceadapters.bff.controller.web.api.InitializePaymentController;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.PaymentDTO;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.events.StockDebitEventDto;
import com.soat.fiap.food.core.payment.core.interfaceadapters.dto.mappers.PaymentDTOMapper;
import com.soat.fiap.food.core.payment.infrastructure.common.source.AcquirerSource;
import com.soat.fiap.food.core.payment.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.payment.infrastructure.in.event.listener.azsvcbus.catalog.handlers.StockDebitHandler;

import unit.fixtures.EventFixture;
import unit.fixtures.PaymentFixture;

@ExtendWith(MockitoExtension.class) @DisplayName("StockDebitHandler - Testes Unitários")
class StockDebitHandlerTest {

	@Mock
	private PaymentDataSource paymentDataSource;

	@Mock
	private AcquirerSource acquirerSource;

	private StockDebitHandler handler;

	@BeforeEach
	void setUp() {
		handler = new StockDebitHandler(paymentDataSource, acquirerSource);
	}

	@Test @DisplayName("Deve processar evento de débito de estoque com sucesso")
	void shouldHandleStockDebitEventSuccessfully() {
		// Arrange
		StockDebitEventDto event = EventFixture.createStockDebitEvent(1L, "ORD-001", "user-123",
				BigDecimal.valueOf(100));
		var validPayment = PaymentDTOMapper.toDTO(PaymentFixture.createPendingPayment());
		when(acquirerSource.generateQrCode(any(), any())).thenReturn("QR123456");
		when(paymentDataSource.save(any(PaymentDTO.class))).thenReturn(validPayment);

		// Act & Assert
		assertThatNoException().isThrownBy(() -> handler.handle(event));
	}

	@Test @DisplayName("Deve processar evento de débito de estoque com múltiplos itens")
	void shouldHandleStockDebitEventWithMultipleItems() {
		// Arrange
		StockDebitEventDto event = EventFixture.createStockDebitEventWithMultipleItems(2L, "ORD-002", "user-456",
				BigDecimal.valueOf(150));
		var validPayment = PaymentDTOMapper.toDTO(PaymentFixture.createPendingPayment());
		when(acquirerSource.generateQrCode(any(), any())).thenReturn("QR123456");
		when(paymentDataSource.save(any(PaymentDTO.class))).thenReturn(validPayment);

		// Act & Assert
		assertThatNoException().isThrownBy(() -> handler.handle(event));
	}

	@Test @DisplayName("Deve chamar InitializePaymentController.initializePayment ao processar evento")
	void shouldCallInitializePaymentController() {
		// Arrange
		StockDebitEventDto event = EventFixture.createStockDebitEvent(3L, "ORD-003", "user-789",
				BigDecimal.valueOf(200));

		try (MockedStatic<InitializePaymentController> mockedStatic = mockStatic(InitializePaymentController.class)) {
			// Act
			handler.handle(event);

			// Assert
			mockedStatic.verify(
					() -> InitializePaymentController.initializePayment(event, paymentDataSource, acquirerSource),
					times(1));
		}
	}

	@Test @DisplayName("Deve processar eventos com valores customizados")
	void shouldHandleStockDebitEventWithCustomAmount() {
		// Arrange
		StockDebitEventDto event = EventFixture.createStockDebitEvent(4L, "ORD-004", "user-999",
				BigDecimal.valueOf(999.99));
		var validPayment = PaymentDTOMapper.toDTO(PaymentFixture.createPendingPayment());
		when(acquirerSource.generateQrCode(any(), any())).thenReturn("QR123456");
		when(paymentDataSource.save(any(PaymentDTO.class))).thenReturn(validPayment);

		// Act & Assert
		assertThatNoException().isThrownBy(() -> handler.handle(event));
	}

	@Test @DisplayName("Deve processar evento mesmo com lista de itens vazia")
	void shouldHandleStockDebitEventWithEmptyItems() {
		// Arrange
		StockDebitEventDto event = EventFixture.createStockDebitEvent(5L, "ORD-005", "user-000", BigDecimal.valueOf(0));
		event.setItems(List.of());

		var validPayment = PaymentDTOMapper.toDTO(PaymentFixture.createPendingPayment());
		when(acquirerSource.generateQrCode(any(), any())).thenReturn("QR123456");
		when(paymentDataSource.save(any(PaymentDTO.class))).thenReturn(validPayment);

		// Act & Assert
		assertThatNoException().isThrownBy(() -> handler.handle(event));
	}
}
