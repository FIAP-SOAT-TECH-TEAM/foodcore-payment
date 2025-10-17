package com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.client;

import com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.entity.MercadoPagoGenerateQrCodeEntity;
import com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.entity.MercadoPagoPaymentEntity;
import com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.entity.MercadoPagoQrCodeEntity;
import com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.entity.order.MercadoPagoOrderEntity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Cliente de conexão com a API do mercado pago
 */
public interface MercadoPagoClient {

	@POST("/instore/orders/qr/seller/collectors/{user_id}/pos/{pos_id}/qrs")
	Call<MercadoPagoQrCodeEntity> generateQrCode(@Path("user_id") String userId, @Path("pos_id") String posId,
			@Body MercadoPagoGenerateQrCodeEntity request);

	@GET("/v1/payments/{payment_id}")
	Call<MercadoPagoPaymentEntity> getMercadoPagoPayments(@Path("payment_id") String paymentId);

	@GET("/merchant_orders/{order_id}")
	Call<MercadoPagoOrderEntity> getMercadoPagoOrder(@Path("order_id") Long orderId);

}
