package com.soat.fiap.food.core.api.payment.core.application.inputs;

import java.time.ZonedDateTime;

/**
 * Representa um DTO de entrada da aplicação (Application Layer), contendo
 * apenas os dados necessários para processar uma notificação recebida do
 * adquirente.
 *
 * @param notificationId
 *            ID da notificação.
 * @param isLiveMode
 *            Indica se foi enviado em modo live.
 * @param type
 *            Tipo da notificação.
 * @param dateCreated
 *            Data de criação da notificação.
 * @param userId
 *            ID do usuário remetente.
 * @param apiVersion
 *            Versão da API.
 * @param action
 *            Ação executada (ex: payment.created).
 * @param dataId
 *            ID da entidade relacionada à notificação (ex: ID do pagamento).
 */
public record AcquirerNotificationInput(Long notificationId, boolean isLiveMode, String type, ZonedDateTime dateCreated,
		Long userId, String apiVersion, String action, String dataId) {
}
