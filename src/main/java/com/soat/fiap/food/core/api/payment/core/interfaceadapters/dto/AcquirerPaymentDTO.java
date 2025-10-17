package com.soat.fiap.food.core.api.payment.core.interfaceadapters.dto;

import com.soat.fiap.food.core.api.payment.core.domain.vo.PaymentMethod;
import com.soat.fiap.food.core.api.payment.core.domain.vo.PaymentStatus;

/**
 * DTO utilizado para representar dados da entidade Payment retornados pelo
 * adquirente. Serve como objeto de transferência entre o domínio e o mundo
 * externo (DataSource).
 *
 * @param tid
 *            Identificador da transação no adquirente.
 * @param status
 *            Status atual do pagamento.
 * @param externalReference
 *            Referência externa associada ao pedido local.
 * @param type
 *            Tipo do método de pagamento utilizado.
 */
public record AcquirerPaymentDTO(String tid, PaymentStatus status, Long externalReference, PaymentMethod type) {
}
