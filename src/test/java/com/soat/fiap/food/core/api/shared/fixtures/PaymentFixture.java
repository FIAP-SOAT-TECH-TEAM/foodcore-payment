package com.soat.fiap.food.core.api.shared.fixtures;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.soat.fiap.food.core.api.payment.core.application.inputs.AcquirerNotificationInput;
import com.soat.fiap.food.core.api.payment.core.application.inputs.OrderCreatedInput;
import com.soat.fiap.food.core.api.payment.core.domain.model.Payment;
import com.soat.fiap.food.core.api.payment.core.domain.vo.PaymentMethod;
import com.soat.fiap.food.core.api.payment.core.domain.vo.PaymentStatus;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.dto.AcquirerPaymentDTO;

/**
 * Fixture para criação de objetos do módulo Payment para testes unitários
 */
public class PaymentFixture {

	public static Payment createValidPayment() {
		return new Payment("as23as3", // userId
				1L, // orderId
				new BigDecimal("50.00") // amount
		);
	}

	public static Payment createPendingPayment() {
		var payment = new Payment("as23as3", 1L, new BigDecimal("25.90"));
		payment.setId(1L);
		payment.setStatus(PaymentStatus.PENDING);
		payment.setQrCode(
				"00020126580014br.gov.bcb.pix0136123e4567-e12b-12d1-a456-42661417400052040000530398654041.005802BR5925JOSE DA SILVA SAURO6009SAO PAULO62070503***6304");
		return payment;
	}

	public static Payment createApprovedPayment() {
		var payment = new Payment("as23as3", 1L, new BigDecimal("35.50"));
		payment.setId(2L);
		payment.setStatus(PaymentStatus.APPROVED);
		payment.setType(PaymentMethod.PIX);
		payment.setTid("12345678");
		payment.setQrCode(
				"00020126580014br.gov.bcb.pix0136123e4567-e12b-12d1-a456-42661417400052040000530398654041.005802BR5925JOSE DA SILVA SAURO6009SAO PAULO62070503***6304");
		return payment;
	}

	public static Payment createCancelledPayment() {
		var payment = new Payment("as23as3", 2L, new BigDecimal("18.90"));
		payment.setId(3L);
		payment.setStatus(PaymentStatus.CANCELLED);
		payment.setType(PaymentMethod.PIX);
		payment.setTid("87654321");
		return payment;
	}

	public static Payment createRejectedPayment() {
		var payment = new Payment("as23as3", 3L, new BigDecimal("42.00"));
		payment.setId(4L);
		payment.setStatus(PaymentStatus.REJECTED);
		payment.setType(PaymentMethod.PIX);
		payment.setTid("11223344");
		return payment;
	}

	public static Payment createExpiredPayment() {
		var payment = new Payment("as23as3", 4L, new BigDecimal("29.90"));
		payment.setId(5L);
		payment.setStatus(PaymentStatus.PENDING);
		payment.setExpiresIn(LocalDateTime.now().minusMinutes(30)); // Expirado há 30 minutos
		return payment;
	}

	public static Payment createPaymentWithCustomAmount(BigDecimal amount) {
		return new Payment("as23as3", 1L, amount);
	}

	public static Payment createPaymentForUser(String userId) {
		return new Payment(userId, 1L, new BigDecimal("25.00"));
	}

	public static Payment createPaymentForOrder(Long orderId) {
		return new Payment("as23as3", orderId, new BigDecimal("30.00"));
	}

	// Input DTOs para testes
	public static OrderCreatedInput createValidOrderCreatedInput() {
		return new OrderCreatedInput(1L, // orderId
				"ORD-001", // orderNumber
				"as23as3", // userId
				new BigDecimal("50.00"), // totalAmount
				List.of() // items
		);
	}

	public static OrderCreatedInput createOrderCreatedInputWithCustomAmount(BigDecimal amount) {
		return new OrderCreatedInput(1L, "ORD-002", "as23as3", amount, List.of());
	}

	public static AcquirerNotificationInput createValidAcquirerNotificationInput() {
		return new AcquirerNotificationInput(1L, // notificationId
				false, // isLiveMode
				"payment", // type
				java.time.ZonedDateTime.now(), // dateCreated
				1L, // userId
				"v1", // apiVersion
				"payment_notification", // action
				"12345678" // dataId
		);
	}

	public static AcquirerPaymentDTO createValidAcquirerPaymentOutput() {
		return new AcquirerPaymentDTO("12345678", // tid
				PaymentStatus.APPROVED, 1L, // externalReference (orderId)
				PaymentMethod.PIX);
	}

	public static AcquirerPaymentDTO createRejectedAcquirerPaymentOutput() {
		return new AcquirerPaymentDTO("87654321", PaymentStatus.REJECTED, 1L, PaymentMethod.PIX);
	}

	public static AcquirerPaymentDTO createCancelledAcquirerPaymentOutput() {
		return new AcquirerPaymentDTO("11223344", PaymentStatus.CANCELLED, 1L, PaymentMethod.PIX);
	}
}
