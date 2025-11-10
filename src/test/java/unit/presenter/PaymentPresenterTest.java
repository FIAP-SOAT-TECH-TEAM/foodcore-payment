package unit.presenter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.soat.fiap.food.core.payment.core.domain.model.Payment;
import com.soat.fiap.food.core.payment.core.interfaceadapters.bff.presenter.web.api.PaymentPresenter;
import com.soat.fiap.food.core.payment.infrastructure.in.web.api.dto.response.PaymentResponse;
import com.soat.fiap.food.core.payment.infrastructure.in.web.api.dto.response.PaymentStatusResponse;
import com.soat.fiap.food.core.payment.infrastructure.in.web.api.dto.response.QrCodeResponse;

import unit.fixtures.PaymentFixture;

@DisplayName("PaymentPresenter - Testes de Apresentação (DTOs)")
class PaymentPresenterTest {

	@Test @DisplayName("Deve converter Payment em PaymentStatusResponse corretamente")
	void shouldConvertPaymentToPaymentStatusResponse() {
		Payment payment = PaymentFixture.createApprovedPayment();

		PaymentStatusResponse response = PaymentPresenter.toPaymentStatusResponse(payment);

		assertNotNull(response);
		assertEquals(payment.getOrderId(), response.getOrderId());
		assertEquals(payment.getStatus(), response.getStatus());
	}

	@Test @DisplayName("Deve converter Payment em QrCodeResponse corretamente")
	void shouldConvertPaymentToQrCodeResponse() {
		Payment payment = PaymentFixture.createPendingPayment();

		QrCodeResponse response = PaymentPresenter.toQrCodeResponse(payment);

		assertNotNull(response);
		assertEquals(String.valueOf(payment.getOrderId()), response.getOrderId());
		assertEquals(payment.getQrCode(), response.getQrCode());
		assertEquals(payment.getExpiresIn(), response.getExpiresIn());
	}

	@Test @DisplayName("Deve converter Payment completo em PaymentResponse corretamente")
	void shouldConvertPaymentToPaymentResponse() {
		Payment payment = PaymentFixture.createApprovedPayment();

		PaymentResponse response = PaymentPresenter.toPaymentResponse(payment);

		assertNotNull(response);
		assertEquals(payment.getId(), response.getId());
		assertEquals(payment.getOrderId(), response.getOrderId());
		assertEquals(payment.getUserId(), response.getUserId());
		assertEquals(payment.getType(), response.getType());
		assertEquals(payment.getType().name(), response.getTypeName());
		assertEquals(payment.getStatus(), response.getStatus());
		assertEquals(payment.getStatus().getDescription(), response.getStatusDescription());
		assertEquals(payment.getTid(), response.getTid());
		assertEquals(payment.getAmount(), response.getAmount());
		assertEquals(payment.getQrCode().value(), response.getQrCode());
		assertEquals(payment.getExpiresIn(), response.getExpiresIn());
		assertEquals(payment.getPaidAt(), response.getPaidAt());
		assertEquals(payment.getObservations(), response.getObservations());
		assertEquals(payment.getAuditInfo().getCreatedAt(), response.getCreatedAt());
		assertEquals(payment.getAuditInfo().getUpdatedAt(), response.getUpdatedAt());
	}

	@Test @DisplayName("Deve retornar null ao converter Payment nulo")
	void shouldReturnNullWhenPaymentIsNull() {
		assertNull(PaymentPresenter.toPaymentResponse(null));
	}
}
