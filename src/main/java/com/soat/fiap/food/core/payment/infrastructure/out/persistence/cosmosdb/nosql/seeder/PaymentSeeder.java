package com.soat.fiap.food.core.payment.infrastructure.out.persistence.cosmosdb.nosql.seeder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.soat.fiap.food.core.payment.core.domain.vo.PaymentMethod;
import com.soat.fiap.food.core.payment.core.domain.vo.PaymentStatus;
import com.soat.fiap.food.core.payment.core.domain.vo.QrCode;
import com.soat.fiap.food.core.payment.infrastructure.out.persistence.cosmosdb.nosql.entity.PaymentEntity;
import com.soat.fiap.food.core.payment.infrastructure.out.persistence.cosmosdb.nosql.repository.CosmosDbPaymentRepository;
import com.soat.fiap.food.core.shared.core.domain.vo.AuditInfo;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

/**
 * Seeder de pagamentos para o Azure Cosmos DB.
 * <p>
 * Este seeder cria registros de pagamento fictícios para facilitar o
 * desenvolvimento e testes locais. Ele será executado apenas quando o profile
 * ativo for {@code local}.
 * <p>
 * Os pagamentos incluem diferentes métodos de pagamento (cartão de crédito,
 * débito e PIX) e diferentes status (APPROVED e PENDING).
 * <p>
 */
@Component @Profile("local") @RequiredArgsConstructor
public class PaymentSeeder {

	/** Repositório reativo para a entidade PaymentEntity no Cosmos DB */
	private final CosmosDbPaymentRepository repository;

	/**
	 * Método executado após a construção do bean, responsável por popular a coleção
	 * de pagamentos no Cosmos DB com registros de teste.
	 * <p>
	 * Cria quatro pagamentos com diferentes métodos e statuses, preenchendo os
	 * campos obrigatórios, como userId, orderId, amount, qrCode e auditInfo.
	 */
	@PostConstruct
	public void seed() {

		// Pagamento 1 - Cartão de Crédito
		PaymentEntity payment1 = new PaymentEntity();
		payment1.setUserId("asdas2332");
		payment1.setOrderId(1);
		payment1.setType(PaymentMethod.CREDIT_CARD);
		payment1.setExpiresIn(LocalDateTime.now().plusDays(30));
		payment1.setStatus(PaymentStatus.APPROVED);
		payment1.setPaidAt(LocalDateTime.now());
		payment1.setTid("112673020299");
		payment1.setAmount(new BigDecimal("32.80"));
		payment1.setQrCode(new QrCode(
				"00020101021243650016COM.MERCADOLIBRE02013063682409123-aaaa-bbbb-cccc-1234567890ab5204000053039865802BR5908Joao Test6009CURITIBA62070503***63AS04A13B"));
		payment1.setObservations("Pagamento aprovado via cartão");
		payment1.setAuditInfo(new AuditInfo());

		// Pagamento 2 - Débito
		PaymentEntity payment2 = new PaymentEntity();
		payment2.setUserId("asd34515232");
		payment2.setOrderId(2);
		payment2.setType(PaymentMethod.DEBIT_CARD);
		payment2.setExpiresIn(LocalDateTime.now());
		payment2.setStatus(PaymentStatus.APPROVED);
		payment2.setPaidAt(LocalDateTime.now());
		payment2.setTid("872379520298");
		payment2.setAmount(new BigDecimal("79.70"));
		payment2.setQrCode(new QrCode(
				"000201010asdasd43650016COM.MERCADOLIBRE02013063682409123-aaaa-bbbb-cccc-1234567890ab5204000053039865802BR5908Joao Test6009CURITIBA62070503***63AS04A13B"));
		payment2.setObservations("Pagamento via débito automático");
		payment2.setAuditInfo(new AuditInfo());

		// Pagamento 3 - PIX Pendente
		PaymentEntity payment3 = new PaymentEntity();
		payment3.setUserId("asd34515232");
		payment3.setOrderId(3);
		payment3.setType(null);
		payment3.setExpiresIn(LocalDateTime.now().plusHours(1));
		payment3.setStatus(PaymentStatus.PENDING);
		payment3.setPaidAt(null);
		payment3.setTid("622433528299");
		payment3.setAmount(new BigDecimal("19.90"));
		payment3.setQrCode(new QrCode(
				"00020101021243650016COM.MERCADOLIBRE02013063682409123-aaaa-bbbb-cccc-1234567890ab5204000053039865802BR5908Joao Test6009CURITIBA62070503***6304A13B"));
		payment3.setObservations("QR Code gerado para pagamento");
		payment3.setAuditInfo(new AuditInfo());

		// Pagamento 4 - PIX Pendente
		PaymentEntity payment4 = new PaymentEntity();
		payment4.setUserId("asd34515232");
		payment4.setOrderId(4);
		payment4.setType(null);
		payment4.setExpiresIn(LocalDateTime.now().plusHours(1));
		payment4.setStatus(PaymentStatus.PENDING);
		payment4.setPaidAt(null);
		payment4.setTid("2126735727299");
		payment4.setAmount(new BigDecimal("4.90"));
		payment4.setQrCode(new QrCode(
				"00020101021243650016COM.MERCADOLIBRE02013063682409cafe-9876-abcd-1234-1234567890cd5204000053039865802BR5912Maria Silva6009RIO BRANCO62070503***6304C7D2"));
		payment4.setObservations("QR Code gerado para pagamento");
		payment4.setAuditInfo(new AuditInfo());

		// Salva todos os pagamentos no Cosmos DB
		repository.saveAll(List.of(payment1, payment2, payment3, payment4));
	}
}
