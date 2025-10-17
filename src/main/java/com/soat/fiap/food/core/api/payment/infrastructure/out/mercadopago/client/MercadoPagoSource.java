package com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.client;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

import com.soat.fiap.food.core.api.payment.core.application.inputs.OrderCreatedInput;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.dto.AcquirerPaymentDTO;
import com.soat.fiap.food.core.api.payment.infrastructure.common.source.AcquirerSource;
import com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.config.MercadoPagoProperties;
import com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.entity.MercadoPagoQrCodeEntity;
import com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.exceptions.MercadoPagoException;
import com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.mapper.request.GenerateQrCodeRequestMapper;
import com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.mapper.response.MercadoPagoPaymentDTOMapper;

import lombok.extern.slf4j.Slf4j;
import retrofit2.Response;

/**
 * Implementação concreta: DataSource para comunicação com o adquirente
 * (MercadoPago).
 */
@Component @Slf4j
public class MercadoPagoSource implements AcquirerSource {

	private static final DateTimeFormatter ISO_OFFSET_DATE_TIME_MILLIS_FIXED = new DateTimeFormatterBuilder()
			.appendPattern("yyyy-MM-dd'T'HH:mm:ss")
			.appendLiteral('.')
			.appendValue(ChronoField.MILLI_OF_SECOND, 3)
			.appendOffsetId()
			.toFormatter();
	private final MercadoPagoClient client;
	private final GenerateQrCodeRequestMapper generateQrCodeRequestMapper;
	private final MercadoPagoPaymentDTOMapper mercadoPagoPaymentDTOMapper;
	private final MercadoPagoProperties properties;

	public MercadoPagoSource(MercadoPagoClient client, GenerateQrCodeRequestMapper generateQrCodeRequestMapper,
			MercadoPagoPaymentDTOMapper mercadoPagoPaymentDTOMapper, MercadoPagoProperties mercadoPagoProperties) {
		this.client = client;
		this.generateQrCodeRequestMapper = generateQrCodeRequestMapper;
		this.mercadoPagoPaymentDTOMapper = mercadoPagoPaymentDTOMapper;
		this.properties = mercadoPagoProperties;
	}

	@Override
	public String generateQrCode(OrderCreatedInput input, LocalDateTime expireIn) {
		try {
			var request = generateQrCodeRequestMapper.toRequest(input);

			request.setNotification_url(properties.getNotificationUrl());

			var laPazExpirationDate = expireIn.atZone(ZoneId.of("America/Sao_Paulo"))
					.withZoneSameInstant(ZoneId.of("America/La_Paz"))
					.truncatedTo(ChronoUnit.MILLIS)
					.format(ISO_OFFSET_DATE_TIME_MILLIS_FIXED);
			request.setExpiration_date(laPazExpirationDate);

			Response<MercadoPagoQrCodeEntity> response = client
					.generateQrCode(properties.getUserId(), properties.getPosId(), request)
					.execute();
			if (response.isSuccessful() && response.body() != null) {
				return response.body().getQr_data();
			} else {
				log.warn("Erro ao contatar API do mercado pago para gerar QrCode");
				String errorBody = response.errorBody() != null ? response.errorBody().string() : "Erro desconhecido";
				throw new MercadoPagoException(
						"Erro na API Mercado Pago: " + errorBody + " | Status code: " + response.code(), null,
						response.code());
			}
		} catch (MercadoPagoException e) {
			throw e;
		} catch (Exception e) {
			log.error("Erro inesperado ao contatar API do mercado pago para gerar QrCode");
			throw new MercadoPagoException("Erro inesperado ao chamar API Mercado Pago", e, 500);
		}
	}

	@Override
	public AcquirerPaymentDTO getAcquirerPayments(String id) {
		try {
			var response = client.getMercadoPagoPayments(id).execute();

			if (response.isSuccessful() && response.body() != null) {

				var mercadoPagoPaymentEntity = response.body();

				return mercadoPagoPaymentDTOMapper.toDto(mercadoPagoPaymentEntity);
			} else {
				log.warn("Erro ao contatar API do mercado pago para obter dados do pagamento");

				String errorBody = response.errorBody() != null ? response.errorBody().string() : "Erro desconhecido";

				throw new MercadoPagoException(
						"Erro na API Mercado Pago: " + errorBody + " | Status code: " + response.code(), null,
						response.code());
			}
		} catch (MercadoPagoException e) {
			throw e;
		} catch (Exception e) {
			log.error("Erro inesperado ao contatar API do mercado pago obter dados do pagamento");
			throw new MercadoPagoException("Erro inesperado ao chamar API Mercado Pago", e, 500);
		}

	}

	@Override
	public Object getAcquirerOrder(Long orderId) {
		try {
			var response = client.getMercadoPagoOrder(orderId).execute();

			if (response.isSuccessful() && response.body() != null) {
				return response.body();
			} else {
				log.warn("Erro ao contatar API do mercado pago para obter dados do pedido");

				String errorBody = response.errorBody() != null ? response.errorBody().string() : "Erro desconhecido";

				throw new MercadoPagoException(
						"Erro na API Mercado Pago: " + errorBody + " | Status code: " + response.code(), null,
						response.code());
			}
		} catch (MercadoPagoException e) {
			throw e;
		} catch (Exception e) {
			log.error("Erro inesperado ao contatar API do mercado pago para obter dados do pedido");
			throw new MercadoPagoException("Erro inesperado ao chamar API Mercado Pago", e, 500);
		}

	}
}
