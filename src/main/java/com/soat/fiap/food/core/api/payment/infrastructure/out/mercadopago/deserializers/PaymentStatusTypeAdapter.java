package com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.deserializers;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.soat.fiap.food.core.api.payment.core.domain.vo.PaymentStatus;

/**
 * TypeAdapter customizado para PaymentStatus para uso com GSON.
 */
public class PaymentStatusTypeAdapter extends TypeAdapter<PaymentStatus> {
	@Override
	public void write(JsonWriter out, PaymentStatus value) throws IOException {
		if (value == null) {
			out.nullValue();
		} else {
			out.value(value.name());
		}
	}

	@Override
	public PaymentStatus read(JsonReader in) throws IOException {
		String value = in.nextString();
		return PaymentStatus.fromValue(value);
	}
}
