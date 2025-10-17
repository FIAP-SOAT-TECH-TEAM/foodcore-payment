package com.soat.fiap.food.core.api.payment.unit.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.payment.core.application.inputs.OrderCreatedInput;
import com.soat.fiap.food.core.api.payment.core.application.usecases.InitializePaymentUseCase;
import com.soat.fiap.food.core.api.payment.core.domain.model.Payment;
import com.soat.fiap.food.core.api.payment.core.domain.vo.PaymentStatus;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.gateways.AcquirerGateway;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.gateways.PaymentGateway;

@ExtendWith(MockitoExtension.class) @DisplayName("InitializePaymentUseCase - Testes Unitários")
class InitializePaymentUseCaseTest {

	@Mock
	private PaymentGateway paymentGateway;

	@Mock
	private AcquirerGateway acquirerGateway;

	@Test @DisplayName("Deve retornar pagamento existente quando já inicializado")
	void shouldReturnExistingPaymentWhenAlreadyInitialized() {
		// Arrange
		var orderCreatedInput = new OrderCreatedInput(1L, "ORD-001", "as23as3", new BigDecimal("50.00"),
				java.util.List.of());
		var existingPayment = new Payment("as23as8", 1L, new BigDecimal("50.00"));
		existingPayment.setId(1L);
		existingPayment.setStatus(PaymentStatus.PENDING);

		when(paymentGateway.findTopByOrderIdOrderByIdDesc(1L)).thenReturn(Optional.of(existingPayment));

		// Act
		var result = InitializePaymentUseCase.initializePayment(orderCreatedInput, paymentGateway, acquirerGateway);

		// Assert
		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals(PaymentStatus.PENDING, result.getStatus());
		assertEquals(new BigDecimal("50.00"), result.getAmount());
	}

	@Test @DisplayName("Deve criar novo pagamento quando não existe")
	void shouldCreateNewPaymentWhenNotExists() {
		// Arrange
		var orderCreatedInput = new OrderCreatedInput(1L, "ORD-002", "as23as3", new BigDecimal("75.50"),
				java.util.List.of());
		var qrCodeValue = "00020126580014BR.GOV.BCB.PIX0136123e4567-e12b-12d1-a456-426655440000";

		when(paymentGateway.findTopByOrderIdOrderByIdDesc(1L)).thenReturn(Optional.empty());
		when(acquirerGateway.generateQrCode(any(OrderCreatedInput.class), any(LocalDateTime.class)))
				.thenReturn(qrCodeValue);

		// Act
		var result = InitializePaymentUseCase.initializePayment(orderCreatedInput, paymentGateway, acquirerGateway);

		// Assert
		assertNotNull(result);
		assertEquals("as23as3", result.getUserId());
		assertEquals(1L, result.getOrderId());
		assertEquals(new BigDecimal("75.50"), result.getAmount());
		assertEquals(PaymentStatus.PENDING, result.getStatus());
		assertNotNull(result.getQrCode());
		assertEquals("00020126580014BR.GOV.BCB.PIX0136123e4567-e12b-12d1-a456-426655440000",
				result.getQrCode().value());
	}

	@Test @DisplayName("Deve criar pagamento com data de expiração correta")
	void shouldCreatePaymentWithCorrectExpirationDate() {
		// Arrange
		var orderCreatedInput = new OrderCreatedInput(2L, "ORD-003", "as238s3", new BigDecimal("100.00"),
				java.util.List.of());
		var qrCodeValue = "00020126580014BR.GOV.BCB.PIX0136987f6543-e21b-21d1-a654-426655440001";

		when(paymentGateway.findTopByOrderIdOrderByIdDesc(2L)).thenReturn(Optional.empty());
		when(acquirerGateway.generateQrCode(any(OrderCreatedInput.class), any(LocalDateTime.class)))
				.thenReturn(qrCodeValue);

		// Act
		var result = InitializePaymentUseCase.initializePayment(orderCreatedInput, paymentGateway, acquirerGateway);

		// Assert
		assertNotNull(result);
		assertNotNull(result.getExpiresIn());
		assertTrue(result.getExpiresIn().isAfter(LocalDateTime.now()));
		assertTrue(result.getExpiresIn().isBefore(LocalDateTime.now().plusMinutes(31))); // 30 minutos + margem
	}
}
