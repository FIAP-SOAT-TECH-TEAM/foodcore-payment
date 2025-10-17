package com.soat.fiap.food.core.api.payment.infrastructure.in.schedulers;

import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.soat.fiap.food.core.api.payment.core.interfaceadapters.bff.controller.web.api.ProcessExpiredPaymentsController;
import com.soat.fiap.food.core.api.payment.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.EventPublisherSource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Componente responsável por agendar e executar periodicamente o processamento
 * de pagamentos não aprovados expirados.
 *
 * <p>
 * Este agendador executa a cada 31 minutos, identificando e processando os
 * pagamentos que passaram do tempo limite permitido para serem concluídos.
 * </p>
 */
@Component @RequiredArgsConstructor @Slf4j @Transactional
public class PaymentExpirationScheduler {

	private final PaymentDataSource paymentDataSource;
	private final EventPublisherSource eventPublisherSource;

	@Scheduled(fixedRate = 31, timeUnit = TimeUnit.MINUTES)
	public void processExpiredPayments() {
		log.info("Iniciando processamento de pagamentos não aprovados expirados.");
		ProcessExpiredPaymentsController.processExpiredPayments(paymentDataSource, eventPublisherSource);
	}
}
