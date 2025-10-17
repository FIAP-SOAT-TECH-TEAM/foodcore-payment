package com.soat.fiap.food.core.api.payment.unit.mappers;

import static org.junit.jupiter.api.Assertions.*;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.soat.fiap.food.core.api.payment.core.application.inputs.AcquirerNotificationInput;
import com.soat.fiap.food.core.api.payment.core.application.inputs.mappers.AcquirerNotificationMapper;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.request.AcquirerNotificationRequest;

@DisplayName("AcquirerNotificationMapper - Testes Unitários")
class AcquirerNotificationMapperTest {

	@Test @DisplayName("Deve mapear AcquirerNotificationRequest para AcquirerNotificationInput com sucesso")
	void shouldMapAcquirerNotificationRequestToInput() {
		// Arrange
		AcquirerNotificationRequest request = new AcquirerNotificationRequest();
		request.setId(1L);
		request.setLiveMode(true);
		request.setType("payment");
		request.setDateCreated(ZonedDateTime.now());
		request.setUserId(123L);
		request.setApiVersion("v1");
		request.setAction("payment.created");
		AcquirerNotificationRequest.Data data = new AcquirerNotificationRequest.Data();
		data.setId("12345678");
		request.setData(data);

		// Act
		AcquirerNotificationInput result = AcquirerNotificationMapper.toInput(request);

		// Assert
		assertNotNull(result);
		assertEquals(1L, result.notificationId());
		assertTrue(result.isLiveMode());
		assertEquals("payment", result.type());
		assertEquals(request.getDateCreated(), result.dateCreated());
		assertEquals(123L, result.userId());
		assertEquals("v1", result.apiVersion());
		assertEquals("payment.created", result.action());
		assertEquals("12345678", result.dataId());
	}

	@Test @DisplayName("Deve mapear AcquirerNotificationRequest com valores nulos")
	void shouldMapAcquirerNotificationRequestWithNullValues() {
		// Arrange
		AcquirerNotificationRequest request = new AcquirerNotificationRequest();
		request.setId(2L);
		request.setLiveMode(false);
		request.setType(null);
		request.setDateCreated(null);
		request.setUserId(null);
		request.setApiVersion(null);
		request.setAction(null);
		request.setData(null);

		// Act
		AcquirerNotificationInput result = AcquirerNotificationMapper.toInput(request);

		// Assert
		assertNotNull(result);
		assertEquals(2L, result.notificationId());
		assertFalse(result.isLiveMode());
		assertNull(result.type());
		assertNull(result.dateCreated());
		assertNull(result.userId());
		assertNull(result.apiVersion());
		assertNull(result.action());
		assertNull(result.dataId());
	}

	@Test @DisplayName("Deve mapear AcquirerNotificationRequest com dados de teste")
	void shouldMapAcquirerNotificationRequestWithTestData() {
		// Arrange
		ZonedDateTime testDate = ZonedDateTime.now().minusHours(1);
		AcquirerNotificationRequest request = new AcquirerNotificationRequest();
		request.setId(999L);
		request.setLiveMode(false);
		request.setType("test");
		request.setDateCreated(testDate);
		request.setUserId(456L);
		request.setApiVersion("v2");
		request.setAction("test.notification");
		AcquirerNotificationRequest.Data testData = new AcquirerNotificationRequest.Data();
		testData.setId("TEST123");
		request.setData(testData);

		// Act
		AcquirerNotificationInput result = AcquirerNotificationMapper.toInput(request);

		// Assert
		assertNotNull(result);
		assertEquals(999L, result.notificationId());
		assertFalse(result.isLiveMode());
		assertEquals("test", result.type());
		assertEquals(testDate, result.dateCreated());
		assertEquals(456L, result.userId());
		assertEquals("v2", result.apiVersion());
		assertEquals("test.notification", result.action());
		assertEquals("TEST123", result.dataId());
	}
}
