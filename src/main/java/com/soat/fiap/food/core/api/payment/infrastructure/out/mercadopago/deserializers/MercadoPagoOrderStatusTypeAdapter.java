package com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.deserializers;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.entity.order.MercadoPagoOrderStatus;

/**
 * TypeAdapter customizado para MercadoPagoOrderStatus (enum) para uso com GSON.
 */
public class MercadoPagoOrderStatusTypeAdapter extends TypeAdapter<MercadoPagoOrderStatus> {
	@Override
	public void write(JsonWriter out, MercadoPagoOrderStatus value) throws IOException {
		if (value == null) {
			out.nullValue();
		} else {
			out.value(value.name());
		}
	}

	@Override
	public MercadoPagoOrderStatus read(JsonReader in) throws IOException {
		String value = in.nextString();
		return MercadoPagoOrderStatus.fromValue(value);
	}
}
