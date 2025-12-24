package com.soat.fiap.food.core.payment.infrastructure.in.web.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.soat.fiap.food.core.payment.core.interfaceadapters.controller.*;
import com.soat.fiap.food.core.payment.infrastructure.common.source.AcquirerSource;
import com.soat.fiap.food.core.payment.infrastructure.common.source.EventPublisherSource;
import com.soat.fiap.food.core.payment.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.payment.infrastructure.in.web.api.dto.request.AcquirerNotificationRequest;
import com.soat.fiap.food.core.payment.infrastructure.in.web.api.dto.request.AcquirerTopicNotificationRequest;
import com.soat.fiap.food.core.payment.infrastructure.in.web.api.dto.response.PaymentResponse;
import com.soat.fiap.food.core.payment.infrastructure.in.web.api.dto.response.PaymentStatusResponse;
import com.soat.fiap.food.core.payment.infrastructure.in.web.api.dto.response.QrCodeResponse;
import com.soat.fiap.food.core.shared.infrastructure.common.source.AccessManagerSource;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * Controlador REST para pagamentos
 */
@RestController @RequestMapping("/") @Slf4j
public class PaymentController {

	private final PaymentDataSource paymentDataSource;
	private final AcquirerSource acquirerSource;
	private final AccessManagerSource accessManagerSource;
	private final EventPublisherSource eventPublisherSource;

	public PaymentController(PaymentDataSource paymentDataSource, AcquirerSource acquirerSource,
			AccessManagerSource accessManagerSource, EventPublisherSource eventPublisherSource) {
		this.paymentDataSource = paymentDataSource;
		this.acquirerSource = acquirerSource;
		this.accessManagerSource = accessManagerSource;
		this.eventPublisherSource = eventPublisherSource;
	}

	// ========== ADQUIRENTE ==========

	@Hidden
	@Operation(operationId = "webhookWithTopic", summary = "Webhook com parâmetros 'topic' e 'id'", description = "Recebe notificações simplificadas do adquirente com parâmetros 'topic' e 'id' na URL", parameters = {
			@Parameter(name = "topic", description = "Tipo do tópico da notificação (ex: 'merchant_order')", required = true, in = ParameterIn.QUERY),
			@Parameter(name = "id", description = "ID do recurso associado à notificação (ex: ID da merchant_order)", required = true, in = ParameterIn.QUERY)})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Notificação processada com sucesso"),
			@ApiResponse(responseCode = "400", description = "Notificação malformada")})
	@PostMapping(value = "/webhook", params = {"topic", "id"})
	@Tag(name = "Mercado Pago", description = "Endpoints de integração com o adquirente") @Transactional
	public ResponseEntity<Void> acquirerTopicWebhook(@RequestParam String topic, @RequestParam String id,
			@Valid @RequestBody AcquirerTopicNotificationRequest notification) {
		log.info("Recebida notificação do adquirente (topic): topic={}, resource={}", topic,
				notification.getResource());

		return ResponseEntity.ok().build();
	}

	@Operation(operationId = "webhookWithoutTopic", summary = "Webhook completo do adquirente", description = "Recebe notificações de eventos de pagamento do adquirente com corpo JSON completo")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Notificação processada com sucesso"),
			@ApiResponse(responseCode = "400", description = "Notificação malformada")})
	@PostMapping(value = "/webhook", params = "!topic")
	@Tag(name = "Mercado Pago", description = "Endpoints de integração com o adquirente") @Transactional
	public ResponseEntity<Void> acquirerWebhook(@Valid @RequestBody AcquirerNotificationRequest notification) {
		log.info("Recebida notificação do adquirente (completa): ação={}, id interno={}, id externo={}",
				notification.getAction(), notification.getId(),
				notification.getData() != null ? notification.getData().getId() : "sem id externo");

		ProcessPaymentNotificationController.processPaymentNotification(notification, paymentDataSource, acquirerSource,
				eventPublisherSource);

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Buscar pedido no adquirente por ID da merchant_order")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Pedido retornado com sucesso", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content)})
	@GetMapping("/merchant_orders/{merchantOrderId}")
	@Tag(name = "Mercado Pago", description = "Endpoints de integração com o adquirente")
	@Transactional(readOnly = true)
	public ResponseEntity<Object> getAcquirerOrder(@PathVariable Long merchantOrderId) {
		log.info("Recebida requisição para obter dados do pedido no adquirente. merchantOrderId={}", merchantOrderId);
		var response = GetAcquirerOrderController.getAcquirerOrder(merchantOrderId, acquirerSource);
		return ResponseEntity.ok(response);
	}

	// ========== PAGAMENTOS ==========

	@Operation(summary = "Buscar status do pagamento por ID do pedido")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Status do pagamento retornado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentStatusResponse.class))),
			@ApiResponse(responseCode = "404", description = "Pagamento não encontrado", content = @Content)})
	@GetMapping("/{orderId}/status")
	@Tag(name = "Pagamentos", description = "Operações para gerenciamento de pagamentos")
	@Transactional(readOnly = true)
	public ResponseEntity<PaymentStatusResponse> getOrderPaymentStatus(@PathVariable Long orderId) {
		log.info("Recebida requisição para obter status do pagamento para orderId {}", orderId);
		var response = GetOrderPaymentStatusController.getOrderPaymentStatus(orderId, paymentDataSource,
				accessManagerSource);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Buscar pagamento mais recente por ID do pedido")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Pagamento retornado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentResponse.class))),
			@ApiResponse(responseCode = "404", description = "Pagamento não encontrado", content = @Content)})
	@GetMapping("/{orderId}/latest") @Transactional(readOnly = true)
	public ResponseEntity<PaymentResponse> getLatestOrderPayment(@PathVariable Long orderId) {
		log.info("Recebida requisição para obter o pagamento mais recente para orderId {}", orderId);

		var response = GetLatestOrderPaymentByOrderIdController.getLatestOrderPaymentByOrderId(orderId,
				paymentDataSource);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Buscar QrCode de pagamento por ID do pedido")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Qr Code de pagamento retornado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = QrCodeResponse.class))),
			@ApiResponse(responseCode = "404", description = "Pagamento não encontrado", content = @Content)})
	@GetMapping("/{orderId}/qrCode")
	@Tag(name = "Pagamentos", description = "Operações para gerenciamento de pagamentos")
	@Transactional(readOnly = true)
	public ResponseEntity<QrCodeResponse> getOrderPaymentQrCode(@PathVariable Long orderId) {
		log.info("Recebida requisição para obter qr de pagamento para orderId {}", orderId);
		var response = GetOrderPaymentQrCodeController.getOrderPaymentQrCode(orderId, paymentDataSource,
				accessManagerSource);
		return ResponseEntity.ok(response);
	}
}
