package com.soat.fiap.food.core.payment.core.application.usecases;

import java.util.Objects;

import com.soat.fiap.food.core.payment.core.application.inputs.AcquirerNotificationInput;
import com.soat.fiap.food.core.payment.core.domain.exceptions.PaymentAlreadyProcessedException;
import com.soat.fiap.food.core.payment.core.domain.exceptions.PaymentNotFoundException;
import com.soat.fiap.food.core.payment.core.domain.model.Payment;
import com.soat.fiap.food.core.payment.core.domain.vo.PaymentStatus;
import com.soat.fiap.food.core.payment.core.interfaceadapters.gateways.AcquirerGateway;
import com.soat.fiap.food.core.payment.core.interfaceadapters.gateways.PaymentGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Processar notificação de pagamento recebida do adquirente.
 */
@Slf4j
public class ProcessPaymentNotificationUseCase {

	/**
	 * Processa uma notificação de pagamento vinda do adquirente
	 *
	 * @param acquirerNotificationInput
	 *            Dados da notificação recebida do adquirente
	 * @param acquirerGateway
	 *            Gateway para comunicação com o adquirente
	 * @param paymentGateway
	 *            Gateway para persistência e consulta de pagamentos
	 * @return Objeto {@link Payment} atualizado com os dados da notificação
	 */
	public static Payment processPaymentNotification(AcquirerNotificationInput acquirerNotificationInput,
			AcquirerGateway acquirerGateway, PaymentGateway paymentGateway) throws PaymentAlreadyProcessedException {

		var acquirerPaymentDto = acquirerGateway.getAcquirerPayments(acquirerNotificationInput.dataId());
		var payment = paymentGateway.findTopByOrderIdOrderByIdDesc(acquirerPaymentDto.externalReference());

		if (payment.isEmpty()) {
			log.warn("Pagamento não foi encontrado a partir da external_reference! {}",
					acquirerPaymentDto.externalReference());
			throw new PaymentNotFoundException("Pagamento", acquirerPaymentDto.externalReference());
		} else if (Objects.equals(payment.get().getTid(), acquirerPaymentDto.tid())) {
			log.warn("Pagamento já processado. Referência Externa: {}!", acquirerPaymentDto.externalReference());
			throw new PaymentAlreadyProcessedException("Pagamento já processado");
		}
		// Indica que se trata de uma segunda tentativa de pagamento
		else if (payment.get().getStatus() != PaymentStatus.PENDING) {
			payment.get().setId(null);
		}

		payment.get().setStatus(acquirerPaymentDto.status());
		payment.get().setType(acquirerPaymentDto.type());
		payment.get().setTid(acquirerNotificationInput.dataId());

		return payment.get();
	}
}
