package com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.deserializers;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.soat.fiap.food.core.api.payment.core.domain.vo.PaymentMethod;

/**
 * TypeAdapter customizado para PaymentMethod (enum) para uso com GSON.
 */
public class PaymentMethodTypeAdapter extends TypeAdapter<PaymentMethod> {
	@Override
	public void write(JsonWriter out, PaymentMethod value) throws IOException {
		if (value == null) {
			out.nullValue();
		} else {
			out.value(value.name());
		}
	}

	@Override
	public PaymentMethod read(JsonReader in) throws IOException {
		String value = in.nextString();
		return PaymentMethod.fromValue(value);
	}
}
