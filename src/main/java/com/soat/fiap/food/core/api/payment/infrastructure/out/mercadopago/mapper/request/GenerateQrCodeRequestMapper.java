package com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.mapper.request;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.soat.fiap.food.core.api.payment.core.application.inputs.OrderCreatedInput;
import com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.entity.MercadoPagoGenerateQrCodeEntity;

/**
 * Mapper responsável por converter um {@link OrderCreatedInput} em um
 * {@link MercadoPagoGenerateQrCodeEntity}, representando o corpo da requisição
 * enviado ao adquirente para geração de um QR Code de pagamento.
 * <p>
 * Este mapeamento é utilizado na integração com serviços externos como o
 * Mercado Pago. Campos como {@code notification_url} e {@code expiration_date}
 * devem ser preenchidos posteriormente, se necessário, pois são ignorados
 * durante o mapeamento.
 */
@Mapper(componentModel = "spring", uses = GenerateQrCodeItemRequestMapper.class)
public interface GenerateQrCodeRequestMapper {

	/**
	 * Converte os dados de um {@link OrderCreatedInput} para um
	 * {@link MercadoPagoGenerateQrCodeEntity}, que será enviado ao adquirente para
	 * geração do QR Code de pagamento.
	 *
	 * @param input
	 *            Dados da criação de pedido, contendo ID, número do pedido, valor
	 *            total e itens.
	 * @return Requisição no formato esperado pelo adquirente.
	 */
	@Mapping(target = "external_reference", source = "orderId")
	@Mapping(target = "title", expression = "java(\"Pedido #\" + input.orderNumber())")
	@Mapping(target = "description", constant = "Pagamento via QR Code")
	@Mapping(target = "notification_url", ignore = true) @Mapping(target = "total_amount", source = "totalAmount")
	@Mapping(target = "items", source = "items") @Mapping(target = "expiration_date", ignore = true)
	MercadoPagoGenerateQrCodeEntity toRequest(OrderCreatedInput input);
}
